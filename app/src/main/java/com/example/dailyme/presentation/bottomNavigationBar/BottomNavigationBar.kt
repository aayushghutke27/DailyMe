package com.example.dailyme.presentation.bottomNavigationBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailyme.presentation.bottomNavigationBar.BottomTabItem

@Composable
fun BottomNavigationBar(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()

    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomTabItem(
                    icon = Icons.Outlined.Info,
                    selectedIcon = Icons.Outlined.Info,
                    label = "Progress",
                    isSelected = selectedTab == BottomTab.PROGRESS,
                    onClick = { onTabSelected(BottomTab.PROGRESS) }
                )

                BottomTabItem(
                    icon = Icons.Outlined.Info,
                    selectedIcon = Icons.Outlined.Info,
                    label = "Today",
                    isSelected = selectedTab == BottomTab.TODAY,
                    onClick = { onTabSelected(BottomTab.TODAY) }
                )

                BottomTabItem(
                    icon = Icons.Outlined.Info,
                    selectedIcon = Icons.Outlined.Info,
                    label = "Profile",
                    isSelected = selectedTab == BottomTab.PROFILE,
                    onClick = { onTabSelected(BottomTab.PROFILE) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNavigationBar(
        selectedTab = BottomTab.PROGRESS,
        onTabSelected = {})

}

