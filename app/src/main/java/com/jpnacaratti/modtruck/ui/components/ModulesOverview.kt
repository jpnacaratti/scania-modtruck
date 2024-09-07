package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.animations.rememberBaseAlphaAnimation
import com.jpnacaratti.modtruck.ui.theme.Black
import com.jpnacaratti.modtruck.ui.theme.Gray
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun ModulesOverview(
    animationDurationAlpha: Int,
    initialDelayAlpha: Long,
    connectedModulesCount: Int,
    modulesStatusIcon: Int,
    modulesStatusDescription: String,
    modifier: Modifier = Modifier
) {

    val amountAnimationState = rememberBaseAlphaAnimation(
        duration = animationDurationAlpha,
        delayStart = initialDelayAlpha + animationDurationAlpha
    )
    val statusAnimationState =
        rememberBaseAlphaAnimation(
            duration = animationDurationAlpha,
            delayStart = initialDelayAlpha + (animationDurationAlpha * 2)
        )

    Column(
        modifier = modifier
            .padding(top = 35.dp, start = 40.dp)
            .alpha(alpha = amountAnimationState.alpha)
    ) {
        Text(
            text = "$connectedModulesCount",
            fontFamily = poppins(FontWeight.SemiBold),
            fontSize = 54.sp,
            color = Black
        )
        Text(
            text = "Módulos",
            fontFamily = poppins(FontWeight.Medium),
            fontSize = 17.sp,
            color = Black,
            modifier = Modifier.offset(y = (-12).dp)
        )
        Text(
            text = "Acoplados",
            fontFamily = poppins(FontWeight.Normal),
            fontSize = 16.sp,
            color = Gray,
            modifier = Modifier.offset(y = (-14).dp)
        )
    }

    Column(
        modifier = modifier
            .width(width = 180.dp)
            .padding(start = 40.dp, top = 15.dp)
            .alpha(alpha = statusAnimationState.alpha)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = modulesStatusIcon),
            contentDescription = "All modules status icon"
        )
        Text(
            text = modulesStatusDescription,
            fontFamily = poppins(FontWeight.Normal),
            color = Gray,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            modifier = Modifier.padding(top = 5.dp)
        )
    }
}