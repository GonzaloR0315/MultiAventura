package com.example.multiaventura.data

import com.example.multiaventura.R // Importo la clase R para acceder a los recursos de la aplicación
import com.example.multiaventura.model.Actividad // Importo la clase Actividad para definir los datos de las actividades

// Objeto singleton para proporcionar datos de actividades
object ActividadesDataProvider {

    // Actividad predeterminada que se muestra al iniciar la aplicación
    val defaultActividad = getActividadData()[0]

    // Función para obtener una lista de datos de actividades
    fun getActividadData(): List<Actividad> {
        // Lista de actividades con sus respectivos detalles
        return listOf(
            Actividad(
                id = 1,
                titleResourceId = R.string.paintball, // Título de la actividad: Paintball
                playerCount = 26, // Número de jugadores: 26
                imageResourceId = R.drawable.paintball_icon, // ID del recurso de imagen para el ícono de Paintball
                actividadImageBanner = R.drawable.paintball, // ID del recurso de imagen para el banner de Paintball
                actividadDetails = R.string.paintballDetails, // Detalles de la actividad de Paintball
                lat = 43.24687, // Latitud de la ubicación de Paintball
                lng = -4.2419751 // Longitud de la ubicación de Paintball
            ),
            Actividad(
                id = 2,
                titleResourceId = R.string.tirolina, // Título de la actividad: Tirolina
                playerCount = 10, // Número de jugadores: 10
                imageResourceId = R.drawable.tirolina_icon, // ID del recurso de imagen para el ícono de Tirolina
                actividadImageBanner = R.drawable.tirolina, // ID del recurso de imagen para el banner de Tirolina
                actividadDetails = R.string.tirolinaDetails, // Detalles de la actividad de Tirolina
                lat = 43.2121427, // Latitud de la ubicación de Tirolina
                lng = -4.2880196 // Longitud de la ubicación de Tirolina
            ),
            Actividad(
                id = 3,
                titleResourceId = R.string.tiroConArco, // Título de la actividad: Tiro con Arco
                playerCount = 20, // Número de jugadores: 20
                imageResourceId = R.drawable.tiroconarco_icon, // ID del recurso de imagen para el ícono de Tiro con Arco
                actividadImageBanner = R.drawable.tiroconarco, // ID del recurso de imagen para el banner de Tiro con Arco
                actividadDetails = R.string.tiroConArcoDetails, // Detalles de la actividad de Tiro con Arco
                lat = 43.23871, // Latitud de la ubicación de Tiro con Arco
                lng = -4.28293 // Longitud de la ubicación de Tiro con Arco
            ),
            Actividad(
                id = 4,
                titleResourceId = R.string.rafting, // Título de la actividad: Rafting
                playerCount = 15, // Número de jugadores: 15
                imageResourceId = R.drawable.rafting_icon, // ID del recurso de imagen para el ícono de Rafting
                actividadImageBanner = R.drawable.rafting, // ID del recurso de imagen para el banner de Rafting
                actividadDetails = R.string.raftingDetails, // Detalles de la actividad de Rafting
                lat = 43.31876, // Latitud de la ubicación de Rafting
                lng = -4.20481 // Longitud de la ubicación de Rafting
            ),
            Actividad(
                id = 5,
                titleResourceId = R.string.karting, // Título de la actividad: Karting
                playerCount = 12, // Número de jugadores: 12
                imageResourceId = R.drawable.karting_icon, // ID del recurso de imagen para el ícono de Karting
                actividadImageBanner = R.drawable.karting, // ID del recurso de imagen para el banner de Karting
                actividadDetails = R.string.kartingDetails, // Detalles de la actividad de Karting
                lat = 43.23871, // Latitud de la ubicación de Karting
                lng = -4.28293 // Longitud de la ubicación de Karting
            ),
            Actividad(
                id = 6,
                titleResourceId = R.string.restarurante, // Título de la actividad: Restaurante
                playerCount = 60, // Número de jugadores: 60
                imageResourceId = R.drawable.restaurante_icon, // ID del recurso de imagen para el ícono de Restaurante
                actividadImageBanner = R.drawable.restaurante, // ID del recurso de imagen para el banner de Restaurante
                actividadDetails = R.string.restaruranteDetails, // Detalles de la actividad de Restaurante
                lat = 43.23871, // Latitud de la ubicación de Restaurante
                lng = -4.28293 // Longitud de la ubicación de Restaurante
            )
        )
    }
}
