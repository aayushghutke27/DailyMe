package com.example.dailyme.data.repository

import android.graphics.BitmapFactory
import java.util.Locale
import com.example.dailyme.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton
import com.example.dailyme.domain.model.MealAnalysis
import org.json.JSONObject


@Singleton
class GeminiRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): GeminiRepository{

    private val apiKeys = listOf(
        BuildConfig.GEMINI_API_KEY_1,
        BuildConfig.GEMINI_API_KEY_2,
        BuildConfig.GEMINI_API_KEY_3
    )
    private var currentKeyIndex = AtomicInteger(0)

    private fun getModel(): GenerativeModel {
        val index = currentKeyIndex.getAndIncrement()
        val apiKey = apiKeys[index % apiKeys.size]
        
        return GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = apiKey
        )
    }

    private suspend fun generateWithRetry(
        prompt: String,
        bitmap: android.graphics.Bitmap? = null
    ): String {
        var lastException: Exception? = null
        
        repeat(apiKeys.size) {
            try {
                val model = getModel()
                val response = if (bitmap != null) {
                    model.generateContent(content {
                        image(bitmap)
                        text(prompt)
                    })
                } else {
                    model.generateContent(content {
                        text(prompt)
                    })
                }
                return response.text?.trim() ?: throw Exception("Empty response")
            } catch (e: Exception) {
                lastException = e
                if (e.message?.contains("QuotaExceededException", ignoreCase = true) != true &&
                    e.toString().contains("QuotaExceededException", ignoreCase = true) != true) {
                    throw e
                }
            }
        }
        throw Exception("API Quota exceeded for all keys. Please try again later.", lastException)
    }


    override suspend fun getDailyInsight(
        meals: List<MealAnalysis>,
        dailyScore: Int
    ): String{

        val loggedMeals = meals.filter{
            it.isLogged
        }

        if(loggedMeals.isEmpty()){
            return "Log your first meal"
        }

        val mealsText = loggedMeals.joinToString("\n"){
            "${it.mealType}: " +
            "${it.ingredients.joinToString(", ")}"+
            "(score: ${it.healthScore})"
        }

        val prompt = """
            You are a nutrition coach reviewing 
            someone's meals for today.
            
            Meals logged:
            $mealsText
            
            Daily score: $dailyScore/100
            
            Write ONE encouraging but honest sentence.
            Maximum 20 words.
            No JSON. Just the sentence.
            Be specific about what they ate.
            Be actionable about what to improve.
        """.trimIndent()

        val responseText = try {
            generateWithRetry(prompt)
        } catch (e: Exception) {
            return "Personalized insights and smarter recommendations are coming soon."
        }

        return responseText
    }


    override suspend fun analyzeMealPhoto(
        photoPath: String?,
        mealType: String
    ): MealAnalysis{

        if(photoPath == null){
            throw Exception("Photo path is null")
        }

        val file = File(photoPath)

        if(!file.exists()){
            throw Exception("Photo file not found")
        }

        val bitmap = BitmapFactory
            .decodeFile(file.absolutePath)
            ?: throw Exception("could not read photo")

        val prompt = """
            You are a nutrition expert analyzing 
            a food photo.
            
            Identify all food items visible 
            in this image.
            
            Return ONLY a valid JSON object.
            No markdown. No explanation. 
            No text before or after.
            Just the JSON object.
            
            {
              "ingredients": ["food1", "food2"],
              "healthScore": <integer 1 to 10>,
              "suggestion": "<one actionable sentence under 20 words>",
              "proteinLevel": "<exactly High, Medium, or Low>",
              "carbsLevel": "<exactly High, Medium, or Low>",
              "fatLevel": "<exactly High, Medium, or Low>",
              "fiberLevel": "<exactly High, Medium, or Low>",
              "mealStrength": "<exactly one of: Diverse, Balanced, Protein-Rich, Carb-Heavy>"
            }
        """.trimIndent()

        val rawText = generateWithRetry(prompt, bitmap)

        return parseResponse(
            raw = rawText,
            photoPath = photoPath,
            mealType = mealType
        )



    }


    private fun parseResponse(
        raw: String,
        photoPath: String,
        mealType: String
    ): MealAnalysis{

        val cleaned = raw
            .replace("```json", "")
            .replace("```", "")
            .trim()

        val jsonStart = cleaned.indexOf("{")
        val jsonEnd = cleaned.lastIndexOf("}") + 1


        if(jsonStart == -1 || jsonEnd == 0){
            throw Exception(
                "No valid Json in Gemini response")
        }

        val jsonString = cleaned
            .substring(jsonStart, jsonEnd)

        val json = JSONObject(jsonString)

        val ingredientsArray =
            json.getJSONArray(
                "ingredients"
            )

        val ingredients =
            (0 until ingredientsArray.length())
                .map{
                    ingredientsArray.getString(it)
                }


        val today = SimpleDateFormat(
            "dd-MM-yyyy",
            Locale.getDefault()

        ).format(Date())

        val time = SimpleDateFormat(
            "HH:mm",
            Locale.getDefault()
        ).format(Date())

        return MealAnalysis(
            mealType = mealType,
            photoPath = photoPath,
            date = today,
            loggedTime = time,
            timestamp = System.currentTimeMillis(),
            ingredients = ingredients,
            healthScore = json.getInt("healthScore"),
            suggestion = json.getString("suggestion"),
            proteinLevel = json.getString("proteinLevel"),
            carbsLevel = json.getString("carbsLevel"),
            fatLevel = json.getString("fatLevel"),
            fiberLevel = json.getString("fiberLevel"),
            mealStrength = json.getString("mealStrength")

        )
    }



}