package com.example.bubblingshapes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bubblingshapes.ui.theme.BubblingShapesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BubblingShapesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HeartSampleScreen()
                }
            }
        }
    }
}

@Composable
fun HeartSampleScreen() {
    val heartCount = remember { mutableStateOf(0) }
    val starCount = remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        repeat(heartCount.value) {
            Heart(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 36.dp),
                horizontalPadding = 24,
                bottomMargin = 110
            )
        }

        repeat(starCount.value) {
            Star(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 46.dp),
                horizontalPadding = 24,
                bottomMargin = 130
            )
        }

        Button(
            onClick = {
                heartCount.value+=3
                starCount.value+=3
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
                .wrapContentHeight()
                .wrapContentWidth()
            ,colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.Transparent
            )
        ) {
            Text(
                text = "Like",
                color = Color.White
            )
        }

    }
}