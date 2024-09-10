package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.theme.LightBlue
import com.jpnacaratti.modtruck.ui.theme.LightDarkBlue
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.theme.White
import com.jpnacaratti.modtruck.ui.viewmodels.UserViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins
import com.nacaratti.modtruck.R

@Composable
fun ProfilePersonalInfo(userViewModel: UserViewModel, modifier: Modifier = Modifier) {
    val keyStyle = SpanStyle(
        color = White,
        fontFamily = poppins(FontWeight.Normal),
        fontSize = 15.sp
    )

    val valueStyle = SpanStyle(
        color = LightGray,
        fontFamily = poppins(FontWeight.Light),
        fontSize = 14.sp
    )

    Column(
        modifier = modifier
    ) {
        Text(
            text = "Informações pessoais",
            fontFamily = poppins(FontWeight.Normal),
            fontSize = 17.sp,
            color = White,
            modifier = Modifier.padding(bottom = 3.dp)
        )
        Box(
            modifier = Modifier
                .padding(top = 5.dp)
                .clip(shape = RoundedCornerShape(size = 20.dp))
                .height(height = 200.dp)
                .fillMaxWidth()
                .background(color = LightDarkBlue)
        ) {
            Row(
                modifier = Modifier
                    .padding(all = 19.dp)
                    .fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(width = 260.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = keyStyle
                            ) {
                                append("Nome: ")
                            }
                            withStyle(
                                style = valueStyle
                            ) {
                                append(userViewModel.name.value)
                            }
                        },
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false
                    )

                    Column {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = keyStyle
                                ) {
                                    append("Número CNH: ")
                                }
                                withStyle(
                                    style = valueStyle
                                ) {
                                    append(userViewModel.driveLicenseNumber.value)
                                }
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = keyStyle
                                ) {
                                    append("Validade: ")
                                }
                                withStyle(
                                    style = valueStyle
                                ) {
                                    append(userViewModel.getFormattedDriveLicenseExpiration())
                                }
                            }
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = keyStyle
                                ) {
                                    append("1ª habilitação: ")
                                }
                                withStyle(
                                    style = valueStyle
                                ) {
                                    append(userViewModel.getFormattedFirstDriveLicenseDate())
                                }
                            }
                        )
                    }

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = keyStyle
                            ) {
                                append("CPF: ")
                            }
                            withStyle(
                                style = valueStyle
                            ) {
                                append(userViewModel.userIdentification.value)
                            }
                        }
                    )
                }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = { /* TODO: Implement */ }
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_pencil),
                            contentDescription = "Pencil icon",
                            tint = White,
                            modifier = Modifier.size(size = 28.dp)
                        )
                    }


                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Tipo",
                            color = White,
                            fontFamily = poppins(weight = FontWeight.Normal),
                            fontSize = 15.sp
                        )

                        Text(
                            text = userViewModel.driveLicenseType.value,
                            fontFamily = poppins(weight = FontWeight.Medium),
                            fontSize = 32.sp,
                            color = LightBlue
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfilePersonalInfoPreview() {
    GoogleFontProvider.initialize()

    val userViewModel = UserViewModel()

    ModTruckTheme {
        ProfilePersonalInfo(userViewModel = userViewModel)
    }
}