package com.example.stratafinal

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch




@Composable
fun DashboardScreen(navController: NavController, averageEmission: Double = 0.0) {
    val viewModel: EmissionViewModel = viewModel()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerContent(onCloseDrawer = {
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
            }, navController = navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("HI USER", Modifier.padding(top = 20.dp) ,fontSize = 35.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.9f),

                ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Your Carbon Footprint",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = String.format("%.2f", averageEmission) + " tons CO₂/year",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Indian average: 3.1 tons/year",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .padding(16.dp) // Add padding for outer spacing
                    .clickable {
                        // In your "Go to Solutions" button click handler:
                        navController.navigate("SolutionScreen/${averageEmission}")

                    }
                    .fillMaxWidth(.75f), // This ensures the card wraps its content width
                elevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Go to Solutions", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
            }



        }
    }
}


@Composable
fun DrawerContent(onCloseDrawer: () -> Unit, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Profile Picture (Centrally Aligned)
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {IconButton(
            onClick = onCloseDrawer,
            modifier = Modifier.align(Alignment.TopEnd).padding(top = 20.dp) // Aligns to the top-right
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close Drawer")
        }
            Image(
                painter = painterResource(id = R.drawable.user), // Replace with your drawable
                contentDescription = "User Profile Picture",
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
        }

        // Other items (Left-Aligned)
        Text("Menu", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        DrawerItem(title = "Re-Calculate", onClick = {
            navController.navigate("SurveyScreen") {
                popUpTo("SurveyScreen") { inclusive = true }
            }
            onCloseDrawer()
        })

        DrawerItem(title = "Achievements", onClick = {
            navController.navigate("SurveyScreen") {
                popUpTo("SurveyScreen") { inclusive = true }
            }
            onCloseDrawer()
        })

        DrawerItem(title = "Settings", onClick = {
            navController.navigate("ProfileScreen") {
                popUpTo("ProfileScreen") { inclusive = true }
            }
            onCloseDrawer()
        })

        DrawerItem(title = "Logout", onClick = {
            navController.navigate("LoginScreen") {
                popUpTo("LoginScreen") { inclusive = true }
            }
            onCloseDrawer()
        })

    }
}




@Composable
fun DrawerItem(title: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = title, fontSize = 16.sp)
    }
}
