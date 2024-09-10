package com.jpnacaratti.modtruck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpnacaratti.modtruck.ui.theme.LightBlue
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.nacaratti.modtruck.R

@Composable
fun ProfileHeader(modifier: Modifier = Modifier) {
    Box(modifier = modifier.height(height = 219.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_header),
            contentDescription = "Header image with four colors",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .offset(y = (-10).dp)
                .size(size = 80.dp)
                .clip(shape = CircleShape)
                .background(color = LightBlue)
                .align(alignment = Alignment.BottomCenter),
            contentAlignment = Alignment.BottomCenter
        ) {
            // TODO: Get users profile image
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.logo_scania),
                contentDescription = "Profile image",
                modifier = Modifier.width(76.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ProfileHeaderPreview() {
    ModTruckTheme {
        ProfileHeader()
    }
}