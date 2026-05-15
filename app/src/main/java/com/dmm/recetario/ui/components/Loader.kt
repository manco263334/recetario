package com.dmm.recetario.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dmm.recetario.R
import kotlinx.coroutines.launch

@Composable
fun CookingLoadingScreen (
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = isSystemInDarkTheme()
) {
    val backgroundGradient = if (isDarkMode) {
        listOf (
            Color(0xFF0F172A),
            Color(0xFF111827),
            Color(0xFF1E293B)
        )
    } else {
        listOf (
            Color(0xFFF8FAFC),
            Color(0xFFE2E8F0),
            Color(0xFFDBEAFE)
        )
    }

    val textColor = if (isDarkMode) {
        Color.White
    } else {
        Color(0xFF0F172A)
    }

    val subTextColor = if (isDarkMode) {
        Color(0xFFCBD5E1)
    } else {
        Color(0xFF64748B)
    }

    val cardColor = if (isDarkMode) {
        Color(0xFF1E293B)
    } else {
        Color.White
    }

    // Splash Animations
    val scale = remember { Animatable(0.7f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            scale.animateTo (
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1200,
                    easing = EaseOutBack
                )
            )
        }

        launch {
            alpha.animateTo (
                targetValue = 1f,
                animationSpec = tween(900)
            )
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "premium")

    val rotation by infiniteTransition.animateFloat (
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable (
            animation = tween (
                durationMillis = 2500,
                easing = EaseInOut
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    val glowScale by infiniteTransition.animateFloat (
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable (
            animation = tween(1800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background (
                Brush.verticalGradient(backgroundGradient)
            )
    ) {

        // Floating Particles
        FloatingParticles()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Glow
            Box (
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .graphicsLayer {
                            scaleX = glowScale
                            scaleY = glowScale
                        }
                        .background(
                            Color(0xFFFF914D).copy(alpha = 0.15f),
                            CircleShape
                        )
                        .blur(40.dp)
                )

                // Main Card
                Card(
                    modifier = Modifier
                        .size(230.dp)
                        .graphicsLayer {
                            rotationZ = rotation
                            scaleX = scale.value
                            scaleY = scale.value
                            this.alpha = alpha.value
                        },
                    shape = RoundedCornerShape(42.dp),
                    elevation = CardDefaults.cardElevation (
                        defaultElevation = 20.dp
                    ),
                    colors = CardDefaults.cardColors (
                        containerColor = cardColor
                    )
                ) {

                    Box (
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        // Shimmer
                        ShimmerEffect()

                        Image (
                            painter = painterResource(R.drawable.logo_app),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(42.dp))

            Text(
                text = "Cooking Magic",
                color = textColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Preparing delicious recipes for you...",
                color = subTextColor,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            LoadingBar()
        }
    }
}

@Composable
fun LoadingBar() {
    val infiniteTransition = rememberInfiniteTransition(label = "loadingBar")

    val progress by infiniteTransition.animateFloat (
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )

    Box (
        modifier = Modifier
            .width(240.dp)
            .height(10.dp)
            .clip(RoundedCornerShape(100))
            .background(Color.White.copy(alpha = 0.12f))
    ) {

        Box (
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress)
                .clip(RoundedCornerShape(100))
                .background (
                    Brush.horizontalGradient (
                        listOf(
                            Color(0xFFFF914D),
                            Color(0xFFFFD166)
                        )
                    )
                )
        )
    }
}

@Composable
fun FloatingParticles() {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")

    val icons = listOf (
        "🥕",
        "🍎",
        "🥬",
        "🍒",
        "🥄",
        "🌶️"
    )

    Box(modifier = Modifier.fillMaxSize()) {

        icons.forEachIndexed { index, icon ->

            val offsetY by infiniteTransition.animateFloat(
                initialValue = 900f,
                targetValue = -200f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 6000 + (index * 700),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                ),
                label = "particleY$index"
            )

            val offsetX by infiniteTransition.animateFloat (
                initialValue = (50..900).random().toFloat(),
                targetValue = (100..1000).random().toFloat(),
                animationSpec = infiniteRepeatable (
                    animation = tween(
                        durationMillis = 5000 + (index * 500),
                        easing = EaseInOut
                    ),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "particleX$index"
            )

            Text (
                text = icon,
                fontSize = (22 + index * 4).sp,
                modifier = Modifier
                    .offset (
                        x = offsetX.dp,
                        y = offsetY.dp
                    )
                    .alpha(0.7f)
            )
        }
    }
}

@Composable
fun ShimmerEffect() {
    val transition = rememberInfiniteTransition(label = "shimmer")

    val translateAnimation by transition.animateFloat (
        initialValue = -400f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable (
            animation = tween(
                durationMillis = 1400,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerAnim"
    )

    val brush = Brush.linearGradient (
        colors = listOf (
            Color.Transparent,
            Color.White.copy(alpha = 0.15f),
            Color.Transparent
        ),
        start = Offset(translateAnimation, translateAnimation),
        end = Offset (
            translateAnimation + 250f,
            translateAnimation + 250f
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    )
}

val EaseOutBack = CubicBezierEasing(
    0.34f,
    1.56f,
    0.64f,
    1f
)

@Preview(showBackground = true)
@Composable
private fun LoaderPreview() {
    CookingLoadingScreen()
}