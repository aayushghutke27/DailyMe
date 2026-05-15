package com.example.dailyme.presentation.bottomNavigationBar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

@Composable
fun RowScope.BottomTabItem(
    icon: ImageVector,
    selectedIcon: ImageVector,
    label: String,
    isSelected: Boolean,
    selectedColor: Color = Color(0xFF067CDC),
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) selectedColor.copy(alpha = 0.10f) else Color.Transparent,
        animationSpec = tween(300),
        label = "bgColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) selectedColor else selectedColor.copy(alpha = 0.42f),
        animationSpec = tween(300),
        label = "contentColor"
    )

    Row(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ) { onClick() }
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {

        Icon(
            imageVector = if (isSelected) selectedIcon else icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = label,
            fontSize = 14.sp,
            color = contentColor,
            fontWeight = if (isSelected){
                FontWeight.Bold
            }
            else {
                FontWeight.Normal
            }

        )
    }

}
