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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.Charcoal
import com.jpnacaratti.modtruck.ui.theme.Graphite
import com.jpnacaratti.modtruck.ui.theme.Gray
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.MidnightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.ui.theme.Yellow
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun ModuleDetail(
    icon: Int,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 0F else 90F, label = ""
    )

    val interactionSource = remember { MutableInteractionSource() }
    val indication = rememberRipple(bounded = true, color = Color.LightGray, radius = 500.dp)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 95.dp)
            .padding(horizontal = 34.dp)
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

@Preview
@Composable
private fun ModuleDetailPreview() {
    GoogleFontProvider.initialize()

    ModTruckTheme {
        ModuleDetail(
            icon = R.drawable.engine_health_module,
            title = "Vida útil do motor",
            description = "11%"
        ) {
            Column {
                Text(
                    text = "Este módulo é responsável por monitorar o desgaste do motor através da medição de RPM e temperatura. Ele alerta caso detecte um desgaste excessivo, garantindo que o motor opere dentro dos padrões seguros.",
                    color = LightGray,
                    fontFamily = poppins(FontWeight.Light),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_warning),
                    colorFilter = ColorFilter.tint(color = Yellow),
                    contentDescription = "Warning icon",
                    modifier = Modifier
                        .padding(top = 33.dp, bottom = 21.dp)
                        .size(size = 56.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = White,
                                fontFamily = poppins(FontWeight.Medium),
                                fontSize = 14.sp
                            )
                        ) {
                            append("Atenção: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = LightGray,
                                fontFamily = poppins(FontWeight.Light),
                                fontSize = 14.sp
                            )
                        ) {
                            append("Motor com ALTO nível de desgaste, leve a uma assistência IMEDIATAMENTE!")
                        }
                    },
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = White,
                                fontFamily = poppins(FontWeight.Light),
                                fontSize = 14.sp
                            )
                        ) {
                            append("RPM do motor: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = LightGray,
                                fontFamily = poppins(FontWeight.Light),
                                fontSize = 14.sp
                            )
                        ) {
                            append("9870")
                        }
                    },
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}