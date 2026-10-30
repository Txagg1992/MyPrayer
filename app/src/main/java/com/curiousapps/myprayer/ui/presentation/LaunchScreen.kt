package com.curiousapps.myprayer.ui.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.ui.theme.AppDimens.SIX_FORTY
import com.curiousapps.myprayer.ui.theme.AppDimens.THREE_SIXTY
import com.curiousapps.myprayer.ui.theme.MyPrayerTheme

@Composable
fun LaunchScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF090C12),
                        Color(0xFF111827),
                        Color(0xFF171E2E)
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.mosaic_cross),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.12f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = Color(0xFF2A3448),
                tonalElevation = 0.dp,
                shadowElevation = 6.dp,
                border = BorderStroke(1.dp, Color(0xFFE6C978)),
                modifier = Modifier.widthIn(max = 420.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gold_ornate_cross),
                        contentDescription = null,
                        modifier = Modifier.size(88.dp),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFF4D6),
                        textAlign = TextAlign.Center,
                        letterSpacing = 1.8.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Prayer in quiet devotion",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFFE7EDF7),
                        textAlign = TextAlign.Center,
                        letterSpacing = 1.4.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = THREE_SIXTY, heightDp = SIX_FORTY)
@Composable
private fun LaunchScreenPreview() {
    MyPrayerTheme {
        LaunchScreen()
    }
}
