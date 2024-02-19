// Definición del paquete donde se encuentra la clase ViewModel
package com.example.multiaventura.Pantallas.login

// Importaciones necesarias para la clase ViewModel y Firebase
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.multiaventura.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

// Clase ViewModel para la pantalla de inicio de sesión
class LoginScreenViewModel: ViewModel() {

    // Instancia de FirebaseAuth para la autenticación de Firebase
    private val auth: FirebaseAuth = Firebase.auth

    // LiveData para indicar si se está cargando algún proceso
    private val _loading = MutableLiveData(false)

    // Función para iniciar sesión con correo electrónico y contraseña
    fun signInWithEmailAndPassword(email: String, password: String, home: ()-> Unit) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Login", "SignInEmailPass entra el usuario")
                        home() // Redirigir al usuario a la pantalla principal
                    } else {
                        Log.d("Login", "SignInEmailPass: ${task.result.toString()}")
                    }
                }
        }catch (ex: Exception){
            Log.d("Login", "SignInEmailPass: ${ex.message}")
        }
    }

    // Función para crear un usuario con correo electrónico y contraseña
    fun createUserWithEmailAndPass(email: String, password: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Si se crea el usuario correctamente, registrar y redirigir al usuario a la pantalla principal
                        val displayName =
                            task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName) // Crear el usuario en la base de datos
                        home() // Redirigir al usuario a la pantalla principal
                    } else {
                        Log.e("Login", "Failed to create user", task.exception)
                    }
                    _loading.value = false // Indicar que el proceso ha finalizado
                }
        }
    }

    // Función para crear un usuario en la base de datos Firebase con información adicional
    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        // Crear un objeto de tipo User con los datos del nuevo usuario
        val user = User(
            userId = userId.toString(),
            displayName = displayName.toString(),
            telefono = "",
            comentarios = "Reservar",
            id = null
        ).toMap()
        // Añadir el usuario a la colección "users" en Firestore
        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("Create", "Usuario creado ${it.id}")
            }.addOnFailureListener {
                Log.d("Create", "ERROR Usuario creado ${it}")
            }
    }
}
