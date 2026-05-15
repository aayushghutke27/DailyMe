package com.example.dailyme.presentation.screen.cameraScreen

import android.Manifest
import android.net.Uri
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.dailyme.presentation.navigation.Screen
import java.io.File

@Composable
fun CameraScreen(
    mealType: String = "",
    onPhotoTaken: (String) -> Unit = {},
    onBack: () -> Unit = {}
) {

    val context = LocalContext.current

    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var photoPath by remember { mutableStateOf("") }
    var permissionDenied by remember { mutableStateOf(false) }
    var cameraReady by remember { mutableStateOf(false) }


    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && photoPath.isNotEmpty()) {
            onPhotoTaken(photoPath)
        } else {
            onBack()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraReady = true
        } else {
            permissionDenied = true
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    LaunchedEffect(cameraReady) {
        if (cameraReady) {
            val (uri, path) = createImageFile(context)
            photoUri = uri
            photoPath = path
            cameraLauncher.launch(uri)
        }
    }

    if (permissionDenied) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(32.dp)
            ) {
                Text(
                    text = "Camera permission is required to log meals.",
                    textAlign = TextAlign.Center
                )

                Button(onClick = {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                    context.startActivity(intent)
                }) {
                    Text(
                        text = "Open Setting"
                    )
                }

                TextButton(
                    onClick = onBack
                ) {
                    Text(
                        text = "Cancel"
                    )
                }
            }
        }
    }
}

fun createImageFile(context: Context): Pair<Uri, String> {

    val mealsDir = File(
        context.filesDir, "meals"
    ).also {
        it.mkdirs()
    }

    val file = File(
        mealsDir,
        "meal_photo_${System.currentTimeMillis()}.jpg"
    )

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    return Pair(uri, file.absolutePath)

}

@Preview(showSystemUi = true)
@Composable
fun CameraScreenPreview() {
    CameraScreen()
}