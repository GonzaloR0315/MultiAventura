package com.example.multiaventura.data

import com.example.multiaventura.R
import com.example.multiaventura.model.Actividad
object ActividadesDataProvider {
    val defaultActividad = getActividadData()[0]

    fun getActividadData(): List<Actividad>{
        return listOf(
            Actividad(
                id = 1,
                titleResourceId = R.string.paintball,
                playerCount = 26,
                imageResourceId = R.drawable.paintball_icon,
                actividadImageBanner = R.drawable.paintball,
                actividadDetails = R.string.paintballDetails,
                lat = 43.24687,
                lng = -4.2419751
            ),
            Actividad(
                id = 2,
                titleResourceId = R.string.tirolina,
                playerCount = 10,
                imageResourceId = R.drawable.tirolina_icon,
                actividadImageBanner = R.drawable.tirolina,
                actividadDetails = R.string.tirolinaDetails,
                lat = 43.2121427,
                lng = -4.2880196
            ),
            Actividad(
                id = 3,
                titleResourceId = R.string.tiroConArco,
                playerCount = 20,
                imageResourceId = R.drawable.tiroconarco_icon,
                actividadImageBanner = R.drawable.tiroconarco,
                actividadDetails = R.string.tiroConArcoDetails,
                lat = 43.23871,
                lng = -4.28293
            ),
            Actividad(
                id = 4,
                titleResourceId = R.string.rafting,
                playerCount = 15,
                imageResourceId = R.drawable.rafting_icon,
                actividadImageBanner = R.drawable.rafting,
                actividadDetails = R.string.raftingDetails,
                lat = 43.31876,
                lng = -4.20481
            ),
            Actividad(
                id = 5,
                titleResourceId = R.string.karting,
                playerCount = 12,
                imageResourceId = R.drawable.karting_icon,
                actividadImageBanner = R.drawable.karting,
                actividadDetails = R.string.kartingDetails,
                lat = 43.23871,
                lng = -4.28293
            ),
            Actividad(
                id = 6,
                titleResourceId = R.string.restarurante,
                playerCount = 60,
                imageResourceId = R.drawable.restaurante_icon,
                actividadImageBanner = R.drawable.restaurante,
                actividadDetails = R.string.restaruranteDetails,
                lat = 43.23871,
                lng = -4.28293
            )
        )
    }
}