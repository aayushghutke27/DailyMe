   package com.example.dailyme.presentation.screen.mainScreen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyme.presentation.bottomNavigationBar.BottomNavigationBar
import com.example.dailyme.presentation.bottomNavigationBar.BottomTab
import com.example.dailyme.presentation.screen.progress.ProgressScreen
import com.example.dailyme.presentation.screen.profile.ProfileScreen
import com.example.dailyme.presentation.screen.todayScreen.TodayScreen
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dailyme.presentation.screen.MealsDetailedScreen.MealsDetailedScreen
import com.example.dailyme.presentation.screen.SelectMethodScreen.SelectMethodScreen
import com.example.dailyme.presentation.screen.cameraScreen.CameraScreen
import com.example.dailyme.presentation.screen.todayScreen.TodayViewModel

   @Composable
fun MainScreen(
) {

    val todayViewModel: TodayViewModel = hiltViewModel()

    var navState by remember {
        mutableStateOf(MainNavigationState())
    }

    val context =  LocalContext.current

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) {granted ->

            if(granted){
                navState = navState.copy(
                    showCamera = true
                )
            }

        }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Color.White,
            bottomBar = {
                if (!navState.showCamera && !navState.showMealDetail) {
                    Box(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        BottomNavigationBar(
                            selectedTab = navState.selectedTab,
                            onTabSelected = { navState = navState.copy(selectedTab = it) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (!navState.showCamera && !navState.showMealDetail) {
                    when (navState.selectedTab) {
                        BottomTab.PROGRESS -> ProgressScreen()
                        BottomTab.TODAY -> TodayScreen(
                            onAddMeal = { mealType ->
                                navState = navState.copy(
                                    selectedMealType = mealType,
                                    showSelectedMethod = true
                                )
                            },
                            onViewMeal = { mealType ->

                                val existing = todayViewModel
                                    .getMealAnalysis(mealType)
                                navState = navState.copy(
                                    selectedMealType = mealType,
                                    isViewingExistingMeal = true,
                                    existingAnalysis = existing,
                                    showMealDetail = true
                                )
                            }
//                            savedMeals = navState.savedMeals
                        )
                        BottomTab.PROFILE -> ProfileScreen()
                    }
                }
            }
        }

        if (navState.showSelectedMethod) {
            SelectMethodScreen(
                onCameraClick = {
                  val permission = Manifest.permission.CAMERA
                  val granted = ContextCompat.checkSelfPermission(
                      context, permission
                  ) == PackageManager.PERMISSION_GRANTED

                    if(granted){
                        navState = navState.copy(
                            showCamera = true
                        )

                    }else{
                        cameraPermissionLauncher.launch(permission)
                    }
                },
                onImportClick = {
                },
                onTextClick = {
                },
                onDismissClick = {
                    navState = navState.copy(
                        showSelectedMethod = false
                    )
                }
            )
        }

        if (navState.showCamera) {
            CameraScreen(
                mealType = navState.selectedMealType,
                onPhotoTaken = { photoPath ->
                    navState = navState.copy(
                        showSelectedMethod = false,
                        showCamera = false,
                        capturedPhotoPath = photoPath,
                        showMealDetail = true
                    )
                },
                onBack = {
                    navState = navState.copy(
                        showCamera = false
                    )
                }
            )
        }

        if (navState.showMealDetail) {
            MealsDetailedScreen(
                mealType = navState.selectedMealType,
                photoPath = navState.capturedPhotoPath,
                isViewingExisting = navState.isViewingExistingMeal,
                existingAnalysis = navState.existingAnalysis,
                onSaved = {savedAnalysis ->
                    navState = navState.copy(
                        showMealDetail = false,
                        capturedPhotoPath = null,
                        selectedTab = BottomTab.TODAY
//                        savedMeals = navState.savedMeals
//                            .filter {
//                                it.mealType != savedAnalysis.mealType
//                            }
//                            .plus(
//                                savedAnalysis
//                            )
                    )
                },
                onBack = {
                    navState = navState.copy(
                        showMealDetail = false,
                        capturedPhotoPath = null,
                        isViewingExistingMeal = false,
                        existingAnalysis = null

                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
