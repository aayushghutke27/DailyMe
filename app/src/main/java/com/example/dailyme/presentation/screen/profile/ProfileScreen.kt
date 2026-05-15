package com.example.dailyme.presentation.screen.profile

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailyme.presentation.screen.profile.component.ProfileMenuRow
import com.example.dailyme.presentation.screen.profile.component.UserAvatar
import com.example.dailyme.presentation.screen.profile.component.ProfileStatRow

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {

    val userName by viewModel.userName.collectAsStateWithLifecycle()
    val userEmail by viewModel.userEmail.collectAsStateWithLifecycle()
    val streak by viewModel.streak.collectAsStateWithLifecycle()
    val totalMeals by viewModel.totalMeals.collectAsStateWithLifecycle()
    val averageScore by viewModel.averageScore.collectAsStateWithLifecycle()
    val memberSince by viewModel.memberSince.collectAsStateWithLifecycle()

    ProfileScreenContent(
        userName = userName,
        userEmail = userEmail,
        streak = streak,
        totalMeals = totalMeals,
        averageScore = averageScore,
        memberSince = memberSince,
        onSignOut = { viewModel.signOut() }
    )
}

@Composable
fun ProfileScreenContent(
    userName: String,
    userEmail: String,
    streak: Int,
    totalMeals: Int,
    averageScore: Int,
    memberSince: String,
    onSignOut: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF067CDC),
                        Color(0xFF63ABE3)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .padding(start = 16.dp, top = 66.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            UserAvatar(
                userName = userName
            )

            Text(
                text = userName.ifEmpty { "username" },
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )


            Text(
                text = userEmail.ifEmpty { "userEmail" },
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.84f)
                .align(Alignment.BottomCenter)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.57f)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(
                    topStart = 42.dp,
                    topEnd = 42.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp)
                        .padding(
                            horizontal = 24.dp,

                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Progress You’ve Earned",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Your habits and consistency are shaping a healthier lifestyle.",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        // ── Stats Card ──
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF3F5F7)
                            ),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalArrangement = Arrangement
                                    .spacedBy(8.dp)
                            ) {

                                Text(
                                    text = "Your Wellness Journey",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement
                                        .spacedBy(0.dp)
                                ) {
                                    ProfileStatRow(
                                        emoji = "🔥",
                                        label = "Current Streak",
                                        value = "$streak days"
                                    )
                                    HorizontalDivider(
                                        color = Color(0xFFF0F0F0),
                                        thickness = 1.dp
                                    )
                                    ProfileStatRow(
                                        emoji = "🥗",
                                        label = "Total Meals Logged",
                                        value = "$totalMeals"
                                    )
                                    HorizontalDivider(
                                        color = Color(0xFFF0F0F0),
                                        thickness = 1.dp
                                    )
                                    ProfileStatRow(
                                        emoji = "⭐",
                                        label = "Average Score",
                                        value = "$averageScore"
                                    )
                                    HorizontalDivider(
                                        color = Color(0xFFF0F0F0),
                                        thickness = 1.dp
                                    )
                                    ProfileStatRow(
                                        emoji = "📅",
                                        label = "Member Since",
                                        value = memberSince.ifEmpty { "---" }
                                    )
                                }
                            }

                        }

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF3F5F7)
                            ),
                            elevation = CardDefaults.cardElevation(0.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalArrangement = Arrangement
                                    .spacedBy(8.dp)
                            ) {

                                Text(
                                    text = "Account & Preferences",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    ProfileMenuRow(
                                        label = "Notifications",
                                        onClick = {}
                                    )
                                    HorizontalDivider(
                                        color = Color(0xFFF0F0F0),
                                        thickness = 1.dp
                                    )
                                    ProfileMenuRow(
                                        label = "Privacy",
                                        onClick = {}
                                    )
                                    HorizontalDivider(
                                        color = Color(0xFFF0F0F0),
                                        thickness = 1.dp
                                    )
                                    ProfileMenuRow(
                                        label = "About",
                                        onClick = {}
                                    )
                                }
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(
                                    color = Color(0xFFFF4444).copy(
                                        alpha = 0.04f
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFFFF4444).copy(
                                        alpha = 0.3f
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .clickable { onSignOut() },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Sign Out",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFFF4444)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "TodayScreen")
@Composable
fun ProfileScreenPreview() {
    ProfileScreenContent(
        userName = "Ayush Ghutke",
        userEmail = "aayushghutke@gmail.com",
        streak = 6,
        totalMeals = 12,
        averageScore = 4,
        memberSince = "Oct 2024",
        onSignOut = {}
    )
}

