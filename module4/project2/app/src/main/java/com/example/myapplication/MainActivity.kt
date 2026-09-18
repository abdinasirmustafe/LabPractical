package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

// Palette taken from the target screen
private val DarkTeal = Color(0xFF073042)
private val AndroidGreen = Color(0xFF3DDC84)
private val DividerGrey = Color(0xFF2E4A57)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkTeal
                ) {
                    BusinessCard()
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {

        // ---- Top block: logo, name, title ----
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(180.dp)
            )
            Text(
                text = "Gentil Pinto",
                fontSize = 46.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Software Developer",
                fontSize = 22.sp,
                color = AndroidGreen
            )
        }

        // ---- Bottom block: contact rows ----
        Column(modifier = Modifier.padding(bottom = 48.dp)) {
            ContactRow(Icons.Filled.Call, "+60 11 1234 5678")
            ContactRow(Icons.Filled.Share, "@gentil__pinto")
            ContactRow(Icons.Filled.Email, "gentil.pinto.dev@gmail.com")
            Divider(color = DividerGrey, thickness = 1.dp)
        }
    }
}

@Composable
fun ContactRow(icon: ImageVector, text: String) {
    Divider(color = DividerGrey, thickness = 1.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AndroidGreen
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCardPreview() {
    MyApplicationTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = DarkTeal) {
            BusinessCard()
        }
    }
}