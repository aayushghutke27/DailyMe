package com.example.dailyme.presentation.screen.progress.component

import android.R.attr.label
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PeriodSelector(
    selected: AnalyticsPeriod,
    onSelect: (AnalyticsPeriod) -> Unit
) {

    val period = listOf(
        "Week" to AnalyticsPeriod.WEEK,
        "6 Months" to AnalyticsPeriod.SIX_MONTHS,
        "Year" to AnalyticsPeriod.YEAR
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(0.322f),
                RoundedCornerShape(12.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        period.forEach { (label, period) ->

            val isSelected = selected == period

            Box(
                modifier = Modifier
                    .background(
                        color = if (isSelected){
                            Color.White
                        }
                        else{
                            Color.Transparent
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 6.dp
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected)
                        FontWeight.SemiBold
                    else
                        FontWeight.Normal,
                    color = if (isSelected)
                        Color(0xFF067CDC)
                    else
                        Color.White.copy(alpha = 0.8f)
                )

            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = (0xFF067CDC))
@Composable
fun PeriodSelectorPreview() {
    PeriodSelector(
        selected = AnalyticsPeriod.WEEK,
        onSelect = {}
    )
}