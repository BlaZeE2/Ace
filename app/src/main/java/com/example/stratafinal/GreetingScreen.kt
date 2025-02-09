package com.example.stratafinal



import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun GreetingScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Video Background
        AndroidView(
            factory = { context ->
                val videoView = VideoView(context)

                videoView.setVideoPath("android.resource://${context.packageName}/raw/forest")
                videoView.setOnPreparedListener {
                    it.isLooping = true // Loop the video
                    videoView.start() // Start the video
                    // Make sure the video fills the screen
                    videoView.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }

                videoView
            },
            modifier = Modifier.fillMaxSize()
        )

        // Greeting Text Overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Strata!",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate("LoginScreen") }) {
                Text("Get Started", fontSize = 17.sp)
            }
        }
    }
}