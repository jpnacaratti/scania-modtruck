package com.jpnacaratti.modtruck.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpnacaratti.modtruck.ui.components.ProfileDrivenInfo
import com.jpnacaratti.modtruck.ui.components.ProfileHeader
import com.jpnacaratti.modtruck.ui.components.ProfilePersonalInfo
import com.jpnacaratti.modtruck.ui.components.ProfileSmartBoxInfo
import com.jpnacaratti.modtruck.ui.components.ProfileWeeklyOverview
import com.jpnacaratti.modtruck.ui.theme.LightGray
import com.jpnacaratti.modtruck.ui.theme.ModTruckTheme
import com.jpnacaratti.modtruck.ui.viewmodels.TruckViewModel
import com.jpnacaratti.modtruck.ui.viewmodels.UserViewModel
import com.jpnacaratti.modtruck.utils.GoogleFontProvider
import com.jpnacaratti.modtruck.utils.GoogleFontProvider.Companion.poppins

@Composable
fun ProfileScreen(
    truckViewModel: TruckViewModel,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxHeight()
            .verticalScroll(state = rememberScrollState())
            .padding(bottom = 100.dp)
    ) {
        ProfileHeader()

        ProfileDrivenInfo(
            drivenHours = truckViewModel.getDrivenHours(),
            drivenYears = userViewModel.getDrivingYears(),
            modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 10.dp)
        )

        ProfilePersonalInfo(
            userViewModel = userViewModel,
            modifier = Modifier.padding(
                start = 30.dp,
                end = 30.dp,
                top = 40.dp,
                bottom = 30.dp
            )
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
        )

        if (truckViewModel.hasConnectedBefore.value || truckViewModel.truckConnected.value) {
            ProfileSmartBoxInfo(
                connected = truckViewModel.truckConnected.value,
                smartBoxInfo = truckViewModel.smartBoxInfo.value!!,
                modifier = Modifier.padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = 20.dp
                )
            )

            ProfileWeeklyOverview(
                modifier = Modifier.padding(
                    top = 30.dp,
                    start = 30.dp,
                    end = 30.dp
                )
            )

        } else {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum caminhão conectado",
                    color = LightGray,
                    fontSize = 18.sp,
                    fontFamily = poppins(weight = FontWeight.Normal),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .width(width = 245.dp)
                )
            }
        }

    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    GoogleFontProvider.initialize()

    val truckViewModel = TruckViewModel()
    val userViewModel = UserViewModel()

    ModTruckTheme {
        ProfileScreen(truckViewModel = truckViewModel, userViewModel = userViewModel)
    }
}