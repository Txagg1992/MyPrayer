package com.curiousapps.myprayer.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.curiousapps.myprayer.R
import com.curiousapps.myprayer.ui.theme.MyPrayerTheme

@Composable
fun LaunchScreen() {
    Image(
        painter = painterResource(id = R.drawable.mosaic_cross),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
private fun LaunchScreenPreview() {
    MyPrayerTheme {
        LaunchScreen()
    }
}
