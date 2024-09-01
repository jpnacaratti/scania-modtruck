package com.jpnacaratti.modtruck.utils

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.nacaratti.modtruck.R

class GoogleFontProvider {
    companion object {
        @Volatile
        private var provider: GoogleFont.Provider? = null

        fun initialize() {
            if (provider == null) {
                synchronized(this) {
                    if (provider == null) {
                        provider = GoogleFont.Provider(
                            providerAuthority = "com.google.android.gms.fonts",
                            providerPackage = "com.google.android.gms",
                            certificates = R.array.com_google_android_gms_fonts_certs
                        )
                    }
                }
            }
        }

        fun poppins(weight: FontWeight, style: FontStyle = FontStyle.Normal): FontFamily =
            if (provider == null)
                throw IllegalStateException("GoogleFontProvider is not initialized. Call initialize() first.")
            else FontFamily(
                Font(
                    googleFont = GoogleFont("Poppins"),
                    fontProvider = provider!!,
                    weight = weight,
                    style = style
                )
            )
    }
}