package com.example.multiaventura.model

import androidx.annotation.DrawableRes // Importo la anotación para recursos de imágenes
import androidx.annotation.StringRes // Importo la anotación para recursos de cadenas de texto

// Defino la clase Actividad para representar los datos de una actividad
data class Actividad(
    val id: Int, // Identificador único de la actividad
    @StringRes val titleResourceId: Int, // ID del recurso de cadena para el título de la actividad
    val playerCount: Int, // Número de jugadores para la actividad
    @DrawableRes val imageResourceId: Int, // ID del recurso de imagen para la actividad
    @DrawableRes val actividadImageBanner: Int, // ID del recurso de imagen para el banner de la actividad
    @StringRes val actividadDetails: Int, // ID del recurso de cadena para los detalles de la actividad
    val lat: Double, // Latitud de la ubicación de la actividad
    val lng: Double // Longitud de la ubicación de la actividad
)
