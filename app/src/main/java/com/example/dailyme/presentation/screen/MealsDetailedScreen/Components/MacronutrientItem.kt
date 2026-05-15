package com.example.dailyme.presentation.screen.MealsDetailedScreen.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MacronutrientItem(
    macronutrientItemData: MacronutrientItemData
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Text(
            text = macronutrientItemData.level,
            fontSize = 16.sp,
            color = Color.Black
        )

        Text(
            text = macronutrientItemData.label,
            fontSize = 12.sp,
            color = Color.Gray
        )

    }

}

@Preview(showBackground = true)
@Composable
fun MacronutrientItemPreview(){
    MacronutrientItem(
       MacronutrientItemData(" High", "Protein")
    )
}