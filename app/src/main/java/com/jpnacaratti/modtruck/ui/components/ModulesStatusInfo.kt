package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.models.ModuleStats
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun ModulesStatusInfo(
    modules: List<ModuleStats>
) {
    modules.forEachIndexed { index, info ->
        ModuleDetail(
            icon = info.icon,
            title = info.title,
            description = info.showDescription,
            delayAnimationStart = index * 150L + 400L,
            modifier = Modifier.padding(top = if (index == 0) 0.dp else 15.dp)
        ) {
            Column {

                Text(
                    text = info.information,
                    color = LightGray,
                    fontFamily = poppins(FontWeight.Light),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                Image(
                    imageVector = ImageVector.vectorResource(id = info.statusIcon),
                    colorFilter = ColorFilter.tint(color = info.statusIconColor),
                    contentDescription = "Status icon",
                    modifier = Modifier
                        .padding(top = 33.dp, bottom = 21.dp)
                        .size(size = 56.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )

                if (info.statusLevel.isNotEmpty() || info.statusDescription.isNotEmpty()) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = White,
                                    fontFamily = poppins(FontWeight.Medium),
                                    fontSize = 14.sp
                                )
                            ) {
                                append(info.statusLevel)
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = LightGray,
                                    fontFamily = poppins(FontWeight.Light),
                                    fontSize = 14.sp
                                )
                            ) {
                                append(info.statusDescription)
                            }
                        },
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = if (info.attributes.isNotEmpty()) 20.dp else 0.dp)
                    )
                }

                if (info.attributes.isNotEmpty()) {
                    info.attributes.forEach {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = White,
                                        fontFamily = poppins(FontWeight.Light),
                                        fontSize = 14.sp
                                    )
                                ) {
                                    append(it.attribute)
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = LightGray,
                                        fontFamily = poppins(FontWeight.Light),
                                        fontSize = 14.sp
                                    )
                                ) {
                                    append(it.value)
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
    }
}