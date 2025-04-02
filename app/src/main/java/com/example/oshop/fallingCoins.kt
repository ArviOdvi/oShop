package com.example.oshop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp


@Composable
fun fallingCoins(walletSize: Dp = 200.dp, coinSize: Dp = 100.dp) {
    val animationDuration = 600
    val initialYPosition = (-500).dp
    val coinPainters = listOf(
        painterResource(id = R.drawable.euro_coin),
        painterResource(id = R.drawable.dollar_coin),
        painterResource(id = R.drawable.piggy)
    )
    val animatedYList = coinPainters.map { remember { Animatable(initialYPosition, Dp.VectorConverter) } }
    val animatedClipList = coinPainters.map { remember { Animatable(1f) } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF66FFCC)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(walletSize * 1.75f)
        ) {
            Box(modifier = Modifier.align(Alignment.Center)) {
                walletPic(size = walletSize) // Pataisytas pavadinimas
            }
            coinPainters.forEachIndexed { index, painter ->
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(x = 0.dp, y = walletSize / 10)
                ) {
                    coinFall(animatedYList[index], animatedClipList[index], coinSize = coinSize, coinPainter = painter)
                }
            }
        }
    }
    coinEffect(animatedYList, animatedClipList, animationDuration, initialYPosition) // Pataisytas kvietimas
}

@Composable
fun coinFall(animatedY: Animatable<Dp, AnimationVector1D>, animatedClip: Animatable<Float, AnimationVector1D>, coinSize: Dp, coinPainter: Painter) {
    Image(
        painter = coinPainter, // Pataisytas paveikslėlio naudojimas
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
fun coinEffect(animatedYList: List<Animatable<Dp, AnimationVector1D>>, animatedClipList: List<Animatable<Float, AnimationVector1D>>, animationDuration: Int, initialYPosition: Dp) {
    LaunchedEffect(Unit) {
        while (true) {
            animatedYList.forEachIndexed { index, animatedY ->
                animatedY.snapTo(initialYPosition)
                animatedClipList[index].snapTo(1f)
                animatedY.animateTo(
                    targetValue = 0.dp,
                    animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing)
                )
                animatedClipList[index].animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = animationDuration / 4, easing = LinearEasing)
                )
            }
        }
    }
}

@Composable
fun autoPic(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.automobilis),
        contentDescription = "Automobilis",
        modifier = Modifier
            .size(200.dp)
            .background(Color.Blue)
    )
}

@Composable
fun walletPic(size: Dp) {
    Image(
        painter = painterResource(id = R.drawable.wallet_photoroom),
        contentDescription = "Piniginė",
        modifier = Modifier
            .size(size)
    )
}