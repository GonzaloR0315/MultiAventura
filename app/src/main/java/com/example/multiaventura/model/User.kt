package com.example.multiaventura.model


//Creo esta clase para saber que datos voy a introducir a la tabla de usuarios
data class User(
    val id: String?,
    val userId: String,
    val displayName: String,
    val telefono: String,
    val comentarios: String,
){
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "display_name" to this.displayName,
            "telefono" to this.telefono,
            "comentarios" to this.comentarios
        )
    }
}
