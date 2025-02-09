package com.example.stratafinal


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch



class EmissionViewModel : ViewModel() {
    private val pineconeService = PineconeService()
    private val _lastFetchedEmission = MutableLiveData<Double?>()
    val lastFetchedEmission: LiveData<Double?> get() = _lastFetchedEmission

    val averageEmission = mutableStateOf(0.0)
    private val emissions = mutableListOf<Double>()

    private fun normalizeInput(category: String, option: String): Pair<String, String> {
        val normalizedCategory = when (category.lowercase()) {
            "housing type" -> "housing type"
            "daily_transport" -> "daily_transport"
            "ride_hailing" -> "ride_hailing"
            "air_travel" -> "air_travel"
            "diet" -> "diet"
            "recycling" -> "recycling"
            "cooling" -> "cooling"
            else -> category
        }

        val normalizedOption = when (option.lowercase()) {
            "ocassionally" -> "Occasionally"
            "pescatrian" -> "Pescatarian"
            "omnivire" -> "Omnivore"
            "apartment/flat" -> "Apartment"
            "independent house/bungalow" -> "Independent house"
            "shared accommodation" -> "Shared accommodation"
            "no" -> "No"
            else -> option
        }

        return Pair(normalizedCategory, normalizedOption)
    }

    fun calculateEmission(category: String, optionText: String) {
        viewModelScope.launch {
            try {
                Log.d("EmissionCalc", "Fetching emission for category: $category, option: $optionText")

                val (normalizedCategory, normalizedOption) = normalizeInput(category, optionText)

                // Query Pinecone with normalized inputs
                val emission = pineconeService.getEmissionValue(normalizedCategory, normalizedOption)

                Log.d("EmissionCalc", "Fetched emission value: $emission for category: $category, option: $optionText")


                if (emission >= 0) {
                    emissions.add(emission)
                    averageEmission.value = emissions.average()
                    Log.d("EmissionCalc", "Updated average emission: ${averageEmission.value}")

                } else {
                    Log.w("EmissionCalc", "$normalizedCategory - $normalizedOption returned 0 emission.")
                }
            } catch (e: Exception) {
                Log.e("EmissionCalc", "Error calculating emission: ${e.message}")
            }
        }
    }fun resetCalculations() {
        emissions.clear()
        averageEmission.value = 0.0
        Log.i("EmissionCalc", "All calculations have been reset.")

        }
}





