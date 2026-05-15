package com.example.dailyme.presentation.screen.MealsDetailedScreen.Components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HealthyScoreCard(
    score: Int = 0,
    suggestion: String = "",
    mealStrength : String = "",
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = "Health Level",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

//                Text(
//                    text = "Diverse",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Light,
//                    color = Color.LightGray
//                )

                Text(
                    text = mealStrength.ifEmpty { "Analyzing..." },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(282.dp),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(286.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFF7D6FFF).copy(0.92f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )

                )

                Box(
                    modifier = Modifier
                        .size(224.dp)
                        .background(
                            Color.White.copy(0.42f),
                            shape = CircleShape
                        )
                      ,
                    contentAlignment = Alignment.Center

                ){

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(224.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFF7D6FFF).copy(alpha = 0.82f),
                                        Color(0xFF7E9FFB).copy(alpha = 0.82f),
                                        Color.Transparent
                                    )
                                ),
                                shape = CircleShape
                            )

                    )



                }


                Text(
                    text = "+$score",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )




            }

            Spacer(modifier = Modifier.height(24.dp))


            if(suggestion.isNotEmpty()){
                Text(
                    text = suggestion,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }else{
                Text(
                    text = "If you want to increase a Nutritional Level next time, add more fiber and source of fats.",
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }


        }


    }

}


@Preview(showBackground = true, backgroundColor = 0xFFF3F5F7)
@Composable
fun HealthyScoreCardPreview(){

    HealthyScoreCard(
        score = 2,
        mealStrength = "Diverse",
        suggestion = "If you want to increase a Nutritional Level next time, add more fiber and source of fats.",

        )

}
