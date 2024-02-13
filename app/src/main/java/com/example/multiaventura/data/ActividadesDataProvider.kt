package com.example.multiaventura.data

import com.example.multiaventura.R
import com.example.multiaventura.model.Actividad
object ActividadesDataProvider {
    val Actividad = getActividadData()[0]

    fun getActividadData(): List<Actividad>{
        return listOf(
            Actividad(
                id = 1,
                titleResourceId = R.string.paintball,
                playerCount = 26,
                imageResourceId = R.drawable.paintball_icon,
                actividadImageBanner = R.drawable.paintball,
                actividadDetails = R.string.paintballDetails
            ),
            Actividad(
                id = 2,
                titleResourceId = R.string.tirolina,
                playerCount = 10,
                imageResourceId = R.drawable.tirolina_icon,
                actividadImageBanner = R.drawable.tirolina,
                actividadDetails = R.string.tirolinaDetails
            ),
            Actividad(
                id = 3,
                titleResourceId = R.string.tiroConArco,
                playerCount = 20,
                imageResourceId = R.drawable.tiroConArco_icon,
                actividadImageBanner = R.drawable.tiroConArco,
                actividadDetails = R.string.tiroConArcoDetails
            ),
            Actividad(
                id = 4,
                titleResourceId = R.string.rafting,
                playerCount = 15,
                imageResourceId = R.drawable.rafting_icon,
                actividadImageBanner = R.drawable.rafting,
                actividadDetails = R.string.raftingDetails
            ),
            Actividad(
                id = 5,
                titleResourceId = R.string.karting,
                playerCount = 12,
                imageResourceId = R.drawable.karting_icon,
                actividadImageBanner = R.drawable.karting,
                actividadDetails = R.string.kartingDetails
            ),
            Actividad(
                id = 6,
                titleResourceId = R.string.restarurante,
                playerCount = 60,
                imageResourceId = R.drawable.restaurante_icon,
                actividadImageBanner = R.drawable.restaurante,
                actividadDetails = R.string.restaruranteDetails
            )
        )
    }
}