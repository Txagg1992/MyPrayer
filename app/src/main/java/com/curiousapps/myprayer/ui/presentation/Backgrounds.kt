package com.curiousapps.myprayer.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.curiousapps.myprayer.R

@Composable
fun BackgroundWithCross(
    crossResource: Painter = painterResource(id = R.drawable.gold_ornate_cross)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = crossResource,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.matchParentSize()
        )
    }
}
@Composable
fun GradientBackground(
    primaryColor: Color,
    topColor: Color = Color(0xFFFFE26D),
    bottomColor: Color = Color(0xFF7717F0)
) {
    val gradient = Brush.verticalGradient(
        listOf(
            topColor,
            primaryColor,
            primaryColor,
            primaryColor,
            primaryColor,
            primaryColor,
            primaryColor,
            primaryColor,
            primaryColor,
            primaryColor,
            bottomColor,
            bottomColor
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    )
}

@Composable
@Preview
fun GradientBackgroundPreview() {
    GradientBackground(
        primaryColor = Color.LightGray
    )
}

@Composable
@Preview
fun BackgroundWithCrossPreview(){
    BackgroundWithCross(
        crossResource = painterResource(id = R.drawable.celtic_cross)
    )
}