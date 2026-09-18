package com.example.businessapplication02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
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
import com.example.businessapplication02.ui.theme.Businessapplication02Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Businessapplication02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF073042) // Specific codelab dark teal
                ) {
                    BusinessCardScreen()
                }
            }
        }
    }
}

@Composable
fun BusinessCardScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Section
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
            )
            Text(
                text = "Gentil Pinto",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Light
            )
            Text(
                text = "Software Developer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3DDC84)
            )
        }

        // Contact Information Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color(0xFF526E7B))
            ContactRow(
                icon = Icons.Default.Phone,
                text = "+11 (123) 444 555 666",
                iconColor = Color(0xFF3DDC84)
            )
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color(0xFF526E7B))
            ContactRow(
                icon = Icons.Default.Share,
                text = "@AndroidDev",
                iconColor = Color(0xFF3DDC84)
            )
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color(0xFF526E7B))
            ContactRow(
                icon = Icons.Default.Email,
                text = "gentil.pinto@android.com",
                iconColor = Color(0xFF3DDC84)
            )
        }
    }
}

@Composable
fun ContactRow(icon: ImageVector, text: String, iconColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCardPreview() {
    Businessapplication02Theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF073042)
        ) {
            BusinessCardScreen()
        }
    }
}
