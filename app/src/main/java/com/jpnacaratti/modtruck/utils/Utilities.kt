package com.jpnacaratti.modtruck.utils

class Utilities {

    companion object {
        fun getEmptySampleArray(size: Int): FloatArray {
            val sampleArray = FloatArray(size)
            for (i in 0 until size) {
                sampleArray[i] = 0f
            }
            return sampleArray
        }
    }

}