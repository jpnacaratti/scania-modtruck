package com.jpnacaratti.modtruck.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.animations.rememberBaseMoveAnimation
import com.jpnacaratti.modtruck.ui.theme.Charcoal
import com.jpnacaratti.modtruck.ui.theme.Graphite
import com.jpnacaratti.modtruck.ui.theme.Gray
import com.jpnacaratti.modtruck.ui.theme.MidnightGray
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun ModuleDetail(
    icon: Int,
    title: String,
    description: String,
    delayAnimationStart: Long,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 0F else 90F, label = ""
    )

    val interactionSource = remember { MutableInteractionSource() }
    val indication = rememberRipple(bounded = true, color = Color.LightGray, radius = 500.dp)

    var containerPosition by remember { mutableStateOf(0f) }

    val animationState = rememberBaseMoveAnimation(
        duration = 750,
        delayStart = delayAnimationStart,
        initialY = containerPosition.plus(500.dp.value)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 95.dp)
            .padding(horizontal = 34.dp)
            .offset(y = animationState.compOffsetY.dp)
            .onGloballyPositioned { coordinates ->
                containerPosition = coordinates.positionInParent().y
            }
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearEasing
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = indication
            ) {
                expanded = !expanded
            },
        colors = CardDefaults.cardColors(containerColor = Charcoal),
        shape = RoundedCornerShape(size = 35.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(all = 21.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(size = 20.dp))
                        .size(size = 53.dp)
                        .background(color = Graphite),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = "Module icon",
                        modifier = Modifier.size(size = 33.dp)
                    )
                }
                Column(modifier = Modifier.padding(start = 10.dp, top = 2.dp)) {
                    Text(
                        text = title,
                        color = White,
                        fontFamily = poppins(weight = FontWeight.Medium),
                        fontSize = 16.sp
                    )
                    Text(
                        text = description,
                        fontFamily = poppins(weight = FontWeight.Light),
                        fontSize = 14.sp,
                        color = Gray
                    )
                }
            }
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 12.dp))
                    .size(33.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .background(color = Color.Transparent)
                    .border(
                        width = 1.dp,
                        color = MidnightGray,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .rotate(degrees = rotationState),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Arrow down or right icon",
                    colorFilter = ColorFilter.tint(color = White),
                    modifier = Modifier.size(size = 18.dp)
                )
            }
        }

        if (!expanded) return@Card

        Box(
            modifier = Modifier.padding(
                start = 21.dp,
                end = 21.dp,
                bottom = 35.dp
            )
        ) {
            content()
        }
    }
}