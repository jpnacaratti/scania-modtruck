package com.jpnacaratti.modtruck.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class UserViewModel : ViewModel() {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // TODO: Store this information on ShredPreferences
    private val _name = mutableStateOf("João Pedro Nacaratti")
    val name: State<String> = _name

    private val _driveLicenseNumber = mutableStateOf("08523736348")
    val driveLicenseNumber: State<String> = _driveLicenseNumber

    private val _driveLicenseExpiration = mutableStateOf(LocalDate.parse("11/10/2028", dateFormatter))
    val driveLicenseExpiration: State<LocalDate> = _driveLicenseExpiration

    private val _firstDriveLicense = mutableStateOf(LocalDate.parse("13/03/2023", dateFormatter))
    val firstDriveLicense: State<LocalDate> = _firstDriveLicense

    private val _userIdentification = mutableStateOf("503.016.152-85")
    val userIdentification: State<String> = _userIdentification

    private val _driveLicenseType = mutableStateOf("C")
    val driveLicenseType: State<String> = _driveLicenseType

    fun getDrivingYears(): Long {
        val today = LocalDate.now()
        return ChronoUnit.YEARS.between(_firstDriveLicense.value, today)
    }

    fun getFormattedFirstDriveLicenseDate(): String {
        return _firstDriveLicense.value.format(dateFormatter)
    }

    fun getFormattedDriveLicenseExpiration(): String {
        return _driveLicenseExpiration.value.format(dateFormatter)
    }
}