package com.example.dailyme.data.repository

import android.content.Context
import android.util.Log
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.dailyme.data.datastore.UserPreferenceDatastore
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Singleton
class AuthRepositoryImpl @Inject constructor(

    private val firebaseAuth: FirebaseAuth,
    private val datastore: UserPreferenceDatastore,
    @ApplicationContext private val context: Context

): AuthRepository{

    override val isLoggedIn: Flow<Boolean> = datastore.isLoggedIn

    override suspend fun signInWithGoogle(context: Context): Result<UserData> {

        return try {

            Log.d("AUTH", "Starting sign in")

            val credentialManager = CredentialManager.create(context = context)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(
                    "991958363555-7ip01kqkqbgrri7cburt9apcenj2laro.apps.googleusercontent.com"
                )
                .setAutoSelectEnabled(false)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            Log.d("AUTH", "Got credential: ${result.credential.type}")

            val credential = result.credential

            if(credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
            ){
                Log.d("AUTH", "Valid Google credential")

                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)

                val firebaseCredential = GoogleAuthProvider
                    .getCredential(
                        googleIdTokenCredential.idToken,
                        null
                    )

                Log.d("AUTH", "Signing into Firebase")

                val authResult = firebaseAuth
                    .signInWithCredential(firebaseCredential)
                    .await()

                Log.d("AUTH", "Firebase success: ${authResult.user?.email}")


                val user = authResult.user
                    ?: return Result.failure(Exception("User is null"))

                val userData = UserData(
                    name = user.displayName ?: "User",
                    email = user.email ?: "",
                    photoUrl = user.photoUrl?.toString() ?: ""
                )

                datastore.saveUser(
                    name = userData.name,
                    email = userData.email,
                    photoUrl = userData.photoUrl
                )

                Log.d("AUTH", "DataStore saved. Returning success")


                Result.success(userData)
            }else{

                Log.e("AUTH", "Wrong credential type: ${credential.type}")

                Result.failure(Exception("Invalid Credential Type"))
            }


        } catch (e: GetCredentialException) {
            Log.e("AUTH", "GetCredentialException: ${e.message}")

            Result.failure(e)

        } catch (e: Exception) {

            Log.e("AUTH", "Exception: ${e.message}")

            Result.failure(e)

        }


    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
        datastore.clearUser()
    }

}