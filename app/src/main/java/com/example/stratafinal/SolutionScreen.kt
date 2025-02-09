package com.example.stratafinal



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SolutionScreen(navController: NavController, averageEmission: Double) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Changed to Center for vertical centering
        ) {
            // Header Section
            Text(
                text = "Personalized Solutions",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            // Emission Result Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp)), // Adding shadow for depth
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant, // Use surfaceVariant for a softer card
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center // Centering within the card
                ) {
                    Text(
                        text = "Your Average Emission",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = String.format("%.2f", averageEmission) + " tons CO2/year",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Improved visualization (replace with your actual graphics or chart library if needed)
                    Box(
                        modifier = Modifier
                            .size(80.dp),
                        contentAlignment = Alignment.Center
                    ){
                        // Add a painter

                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Personalized Advice Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center // Centering within the card
                ) {
                    Text(
                        text = "Recommendations",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val advice = when {
                        averageEmission <= 1.5 -> "Excellent! Your carbon footprint is very low. Continue to practice sustainable habits and explore further reductions where possible."
                        averageEmission <= 3.0 -> "Good job! Your carbon footprint is below the Indian average. Keep making sustainable choices and focus on areas where you can improve."
                        averageEmission <= 4.0 -> "Your carbon footprint is above the Indian average. Focus on reducing emissions in areas like transportation, diet, and energy consumption."
                        else -> "Your carbon footprint is significantly above the Indian average. Major lifestyle changes are recommended in areas like air travel, transportation, diet, and home energy use."
                    }

                    Text(
                        text = advice,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center, // Centering the text
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Here are some general tips:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    Text(text = " - Reduce food waste by planning meals and composting organic waste", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                    Text(text = " - Use public transport, cycle, or walk when possible", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                    Text(text = " - Recycle and reduce waste", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                    Text(text = " - Conserve energy at home", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                }
            }

            // Re-take Survey Button
            Button(
                onClick = { navController.navigate("SurveyScreen") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Retake Survey", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}