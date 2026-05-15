package com.example.dailyme.presentation.screen.MealsDetailedScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File

@Composable
fun MealsPhotoCard(
    photoPath: String? = null,
    ingredients: List<String> = emptyList(),
    suggestion: String = ""
) {

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

            Text(
                text = "Meals",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(224.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.LightGray.copy(0.42f))
            ) {

                if (!photoPath.isNullOrEmpty()) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(File(photoPath))
                            .crossfade(true)
                            .build(),
                        contentDescription = "Meals Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(224.dp)
                            .clip(RoundedCornerShape(24.dp))
                    )

                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (ingredients.isNotEmpty()) {

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = ingredients.joinToString(" ") {
                        "• $it"

                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )


            } else {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Analyzing ingredients....",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

            }


            Spacer(modifier = Modifier.height(16.dp))

            if (suggestion.isNotEmpty()) {
                Text(
                    text = suggestion,
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = "Getting personalized suggestions...",
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
fun MealsPhotoCardPreview() {
    MealsPhotoCard(
        ingredients = listOf("Eggs", "Chicken", "Bread", "Broccoli"),
        suggestion = "If you want to increase a Nutritional Level next time..."
    )
}