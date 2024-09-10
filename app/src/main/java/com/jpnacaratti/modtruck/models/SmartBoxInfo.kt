package com.jpnacaratti.modtruck.models

import java.io.Serializable

data class SmartBoxInfo(
    val model: String,
    val serialNumber: String,
    val numPorts: Int,
    val usedPorts: Int
): Serializable
