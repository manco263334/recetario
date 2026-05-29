package com.dmm.recetario.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun WellnessCard (
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    description: String? = null,
    image: String? = null,
    imageDescription: String? = null,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    imagePosition: ImagePosition = ImagePosition.TOP,
    onClick: () -> Unit = {}
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState (
        targetValue = if (isPressed) 0.95f else 1f,
        label = "scaleAnim"
    )

    Card (
        modifier = modifier
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .animateContentSize(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation (
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        if (imagePosition == ImagePosition.TOP) {
            Column (
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (!image.isNullOrEmpty()) {
                    Box {
                        AsyncImage (
                            model = image,
                            contentDescription = imageDescription,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Box (
                            modifier = Modifier
                                .matchParentSize()
                                .background (
                                    Brush.verticalGradient (
                                        colors = listOf (
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
                Text (
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = titleColor
                )
                if (!description.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text (
                        text = description,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        color = titleColor
                    )
                }
            }
        } else {
            Row (
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!image.isNullOrEmpty()) {
                    Box {
                        AsyncImage (
                            model = image,
                            contentDescription = imageDescription,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Box (
                            modifier = Modifier
                                .matchParentSize()
                                .background (
                                    Brush.verticalGradient (
                                        colors = listOf (
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))
                }
                Column {
                    Text (
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start
                    )
                    if (!description.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text (
                            text = description,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Justify
                        )
                    }
                }
            }
        }
    }
}

enum class ImagePosition {
    TOP, SIDE
}