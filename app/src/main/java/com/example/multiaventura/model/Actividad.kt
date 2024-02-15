package com.example.multiaventura.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Actividad(

    val id: Int,
    @StringRes val titleResourceId: Int,
    val playerCount: Int,
    @DrawableRes val imageResourceId: Int,
    @DrawableRes val actividadImageBanner: Int,
    @StringRes val actividadDetails: Int,
    val lat: Double,
    val lng: Double
)
