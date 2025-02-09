package com.example.stratafinal

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException



class PineconeService {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val PINECONE_API_KEY = "pcsk_5RaDek_KxT6yRGPfNB9ip1jDxpStyjAveWPNkUmNaz4sJdcEFktPW3JK3akDYKuamuX8L6"
    private val PINECONE_API_URL = "https://rightpath-g4so3jl.svc.aped-4627-b74a.pinecone.io"

    suspend fun getEmissionValue(category: String, optionText: String): Double {
        return withContext(Dispatchers.IO) {
            val embedding = generateEmbedding(optionText)
            val response = queryPinecone(category, embedding)
            parseEmissionFromResponse(response)
        }
    }

    private val vocabulary = mutableMapOf<String, Int>()
    private val vectorSize = 384

    init {
        initializeVocabulary()
    }

    private fun initializeVocabulary() {
        val uniqueWords = setOf(
            "never", "once", "a", "year", "or", "less", "times", "Six", "more", "than", "10",
            "walking", "motorcycle", "public", "transport", "Vehicle(petrol)","Car(electric/hybrid)",
            "occasionally", "regularly", "vegan", "vegetarian", "pescatarian", "omnivore", "yes", "no",
            "apartment", "flat", "independent", "house", "bungalow", "shared", "accommodation", "none",
            "one", "Two", "to", "Three", "Ten","Twice", "Thrice", "between", "through","sometimes","travel",
            "by","rarely","frequently","rarely","very","I","once","in","a","while","yet","not","nope",
            "don't","single","often", "above"
        )
        uniqueWords.forEachIndexed { index, word ->
            vocabulary[word] = index
        }
    }

    private fun generateEmbedding(text: String): List<Double> {
        val vector = DoubleArray(vectorSize)
        text.lowercase().split(" ", "-", "/").forEach { word ->
            vocabulary[word]?.let { index ->
                if (index < vectorSize) {
                    vector[index] += 1.0
                }
            }
        }
        val magnitude = Math.sqrt(vector.sumOf { it * it })
        return if (magnitude > 0) vector.map { it / magnitude } else vector.toList()
    }

    private fun queryPinecone(category: String, embedding: List<Double>): String {
        val payload = mapOf(
            "vector" to embedding,
            "topK" to 1,
            "includeMetadata" to true,
            "filter" to mapOf("category" to category)
        )

        val requestBody = gson.toJson(payload).toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("$PINECONE_API_URL/query")
            .addHeader("Api-Key", PINECONE_API_KEY)
            .post(requestBody)
            .build()

        return client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            response.body?.string() ?: throw IOException("Empty response")
        }
    }

    private fun parseEmissionFromResponse(response: String): Double {
        try {
            val jsonElement = JsonParser.parseString(response)
            val jsonObject = jsonElement.asJsonObject
            val matchesArray = jsonObject.getAsJsonArray("matches")
            if (matchesArray != null && matchesArray.size() > 0) {
                val firstMatch = matchesArray[0].asJsonObject
                val metadata = firstMatch.getAsJsonObject("metadata")
                if (metadata.has("emission")) {
                    return metadata.get("emission").asDouble
                }
            }
            throw IllegalStateException("No valid emission data found in response")
        } catch (e: Exception) {
            Log.e("PineconeService", "Error parsing emission from response: ${e.message}")
            throw e
        }
    }

}



