package com.example.dailyme.presentation.screen.MealsDetailedScreen.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach

@Composable
fun MacronutrientCard(
    proteinLevel: String = "",
    carbsLevel: String = "",
    fatLevel: String = "",
    fiberLevel: String = ""
) {

    val macros = listOf(

        MacronutrientItemData(
            proteinLevel.ifEmpty { "...."},
            "Protein"
        ),
        MacronutrientItemData(
            carbsLevel.ifEmpty { "...." },
            "Carbs"
        ),
        MacronutrientItemData(
            fatLevel.ifEmpty { "...." },
            "Fat"
        ),
        MacronutrientItemData(
            fiberLevel.ifEmpty { "...." },
            "Fiber"
        )
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Text(
                    text = "Macronutrient Breakdown",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    macros.fastForEach { macro ->

                        MacronutrientItem(macronutrientItemData = macro)

                    }

                }


            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFF3F5F7)
@Composable
fun MacronutrientCardPreview() {
    MacronutrientCard()
}