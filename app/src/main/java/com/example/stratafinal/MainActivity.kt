package com.example.stratafinal


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stratafinal.myviewmodel.GoogleSignInViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}

@Composable
fun App() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val googleSignInViewModel = GoogleSignInViewModel()


    NavHost(navController = navController, startDestination = "GreetingScreen") {
        composable("GreetingScreen") {
            GreetingScreen(navController)
        }

        composable(route = "LoginScreen") {
            LoginScreen {
                googleSignInViewModel.handleGoogleSignIn(context, navController)
            }
        }

        composable(route = "ProfileScreen") {
            ProfileScreen(googleSignInViewModel)
        }

        composable("SurveyScreen") {
            SurveyScreen(navController)
        }





        composable("DashboardScreen") {
            DashboardScreen(navController = navController)
        }


        composable(
            route = "DashboardScreen/{averageEmission}",
            arguments = listOf(navArgument("averageEmission") {
                type = NavType.FloatType
                defaultValue = 0f
            })
        ) { backStackEntry ->
            val averageEmission = backStackEntry.arguments?.getFloat("averageEmission")?.toDouble() ?: 0.0
            DashboardScreen(navController = navController, averageEmission = averageEmission)
        }
        composable(
            route = "SolutionScreen/{averageEmission}",
            arguments = listOf(navArgument("averageEmission") {
                type = NavType.FloatType
                defaultValue = 0f
            })
        ) { backStackEntry ->
            val averageEmission = backStackEntry.arguments?.getFloat("averageEmission")?.toDouble() ?: 0.0
            SolutionScreen(navController = navController, averageEmission = averageEmission)
        }


    }
}


