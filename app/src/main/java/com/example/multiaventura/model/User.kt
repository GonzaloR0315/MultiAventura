package com.example.multiaventura.model

// Defino la clase User para representar los datos de un usuario
data class User(
    val id: String?, // Identificador único del usuario
    val userId: String, // ID del usuario
    val displayName: String, // Nombre para mostrar del usuario
    val telefono: String, // Número de teléfono del usuario
    val comentarios: String, // Comentarios adicionales sobre el usuario
){
    // Función para convertir un objeto User en un mapa de pares clave/valor
    fun toMap(): MutableMap<String, Any> {
        // Creo un mapa mutable y lo inicializo con los datos del usuario
        return mutableMapOf(
            "user_id" to this.userId, // ID del usuario
            "display_name" to this.displayName, // Nombre para mostrar del usuario
            "telefono" to this.telefono, // Número de teléfono del usuario
            "comentarios" to this.comentarios // Comentarios adicionales sobre el usuario
        )
    }
}
