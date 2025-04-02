package com.example.oshop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay


@Composable
fun downCoins(walletSize: Dp = 200.dp, coinSize: Dp = 100.dp) {
    val animationDuration = 800
    val initialYPosition = (-500).dp
    val animatedY = remember { Animatable(initialYPosition, Dp.VectorConverter) }
    val animatedClip = remember { Animatable(1f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF66FFCC)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(walletSize * 1.75f) // Padidinam box, kad tilptų piniginė ir moneta
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                walletpicture(size = walletSize)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(x = 0.dp, y = walletSize / 10) // koreguojam monetos offset
            ) {
                coinFall(animatedY, animatedClip, coinSize = coinSize)
            }
        }
    }

    coinEffekt(animatedY, animatedClip, animationDuration,initialYPosition)
}
@Composable
fun coinFall(animatedY: Animatable<Dp, AnimationVector1D>, animatedClip: Animatable<Float, AnimationVector1D>, coinSize: Dp) {
    Image(
        painter = painterResource(id = R.drawable.euro_coin),
        contentDescription = "Krentanti moneta",
        modifier = Modifier
            .size(coinSize)
            .offset(y = animatedY.value + (coinSize * (1 - animatedClip.value)))
            .drawWithContent {
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height * animatedClip.value
                ) {
                    this@drawWithContent.drawContent()
                }
            }
    )
}
@Composable
fun coinEffekt(animatedY: Animatable<Dp, AnimationVector1D>, animatedClip: Animatable<Float, AnimationVector1D>, animationDuration: Int, initialYPosition: Dp) {
    LaunchedEffect(Unit) {
        while (true) {
            animatedY.snapTo(initialYPosition)
            animatedClip.snapTo(1f)

            animatedY.animateTo(
                targetValue = 0.dp,
                animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
            )
            animatedClip.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = animationDuration / 3, easing = LinearEasing)
            )
            delay(400)
        }
    }
}
@Composable
fun autoPicture(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.automobilis),
        contentDescription = "Automobilis",
        modifier = Modifier
            .size(200.dp)
            .background(Color.Blue)
    )
}
@Composable
fun walletpicture(size: Dp) {
    Image(
        painter = painterResource(id = R.drawable.wallet_photoroom),
        contentDescription = "Piniginė",
        modifier = Modifier
            .size(size)
    )
}