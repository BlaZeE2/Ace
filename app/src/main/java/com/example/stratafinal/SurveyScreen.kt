package com.example.stratafinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.ColorScheme



@Composable
fun SurveyScreen(navController: NavController) {
    val viewModel: EmissionViewModel = viewModel()
    var currentQuestionIndex by remember { mutableStateOf(0) }

    // List of questions and their options
    val questions = listOf(
        Question(
            category = "air_travel",
            question = "How often do you travel by airplane?",
            options = listOf(
                "once a year",
                "twice/thrice",
                "Six to Ten times a year",
                "above ten"
            ),
            backgroundImageRes = R.drawable.whatsapp_image_2025_02_09_at_07_51_30_ea30f113
        ),
        Question(
            category = "daily_transport",
            question = "What is your primary mode of daily transportation?",
            options = listOf(
                "Walking",
                "MotorCycle",
                "Public transport (bus, train, metro)",
                "Vehicle(petrol)",
                "Car(electric/hybrid)"
            ),
            backgroundImageRes =
            R.drawable.whatsapp_image_2025_02_09_at_07_51_30_105e86e3

        ),
        Question(
            category = "ride_hailing",
            question = "How often do you use ride-hailing services (e.g., Uber, Lyft)?",
            options = listOf(
                "minimal",
                "Occasionally ",
                "Regularly "
            ),
            backgroundImageRes =
            R.drawable.whatsapp_image_2025_02_09_at_07_51_30_105e86e3

        ),
        Question(
            category = "diet",
            question = "What type of diet do you follow?",
            options = listOf(
                "Vegan",
                "Vegetarian",
                "Pescatarian",
                "omnivore"
            ),
            backgroundImageRes =
            R.drawable.whatsapp_image_2025_02_09_at_07_51_29_3ad7cfc9

        ),

        Question(
            category = "housing_type",
            question = "What type of house do you live in?",
            options = listOf(
                "Apartment",
                "bungalow",
                "Shared accommodation"
            ),
            backgroundImageRes =
            R.drawable.whatsapp_image_2025_02_09_at_07_51_31_8f65b099

        ),
        Question(
            category = "cooling",
            question = "How many rooms in your house have air conditioners?",
            options = listOf(
                "None",
                "one",
                "Two",
                "More than Three"
            ),
            backgroundImageRes =
            R.drawable.whatsapp_image_2025_02_09_at_07_51_30_d2ff535f

        )
    )

    val selectedOptions = remember { mutableStateListOf<String?>() }
    while (selectedOptions.size < questions.size) {
        selectedOptions.add(null)
    }

    // Current question
    val currentQuestion = questions[currentQuestionIndex]

    LaunchedEffect(Unit) {
        viewModel.resetCalculations()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        // Background Image
        Image(
            painter = painterResource(id = currentQuestion.backgroundImageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Semi-transparent overlay for the entire screen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f)) // Adjust alpha for desired darkness
            )

            // Question Card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = currentQuestion.question,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    currentQuestion.options.forEach { option ->
                        OptionButton(
                            text = option,
                            isSelected = selectedOptions[currentQuestionIndex] == option,
                            onSelect = {
                                selectedOptions[currentQuestionIndex] = option
                                viewModel.calculateEmission(currentQuestion.category, option)
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            // Navigation Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentQuestionIndex > 0) {
                    NavigationButton(
                        text = "Previous",
                        onClick = { currentQuestionIndex-- }
                    )
                } else {
                    Spacer(modifier = Modifier.width(100.dp))
                }

                NavigationButton(
                    text = if (currentQuestionIndex < questions.size - 1) "Next" else "Finish",
                    onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                        } else {
                            navController.navigate("DashboardScreen/${viewModel.averageEmission.value}")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun OptionButton(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Button(
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.8f),
            contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}

@Composable
fun NavigationButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(100.dp)
            .height(40.dp)
    ) {
        Text(text = text, fontSize = 14.sp)
    }
}




data class Question(
    val category: String,
    val question: String,
    val options: List<String>,
    val backgroundImageRes: Int
)
