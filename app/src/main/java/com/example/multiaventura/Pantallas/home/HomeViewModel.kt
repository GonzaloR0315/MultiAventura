// Definición del paquete donde se encuentra el ViewModel de la pantalla de inicio
package com.example.multiaventura.Pantallas.home

// Importaciones necesarias
import androidx.lifecycle.ViewModel
import com.example.multiaventura.data.ActividadesDataProvider
import com.example.multiaventura.model.Actividad
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// ViewModel para la pantalla de inicio
class HomeViewModel : ViewModel() {

    // Variables para almacenar información sobre la actividad y la fecha seleccionada
    var actividad: String = ""
    var date: String = ""

    // Instancia de Firestore para acceder a la base de datos
    private val db = FirebaseFirestore.getInstance()

    // Estado mutable de la interfaz de usuario
    private val _uiState = MutableStateFlow(
        ActividadesUiState(
            actividadesList = ActividadesDataProvider.getActividadData(),
            currentActividad = ActividadesDataProvider.getActividadData().getOrElse(0) {
                ActividadesDataProvider.defaultActividad
            }
        )
    )
    // Estado inmutable de la interfaz de usuario
    val uiState: StateFlow<ActividadesUiState> = _uiState

    // Función para actualizar la actividad actual seleccionada
    fun updateCurrentActividad(selectedActividad: Actividad) {
        _uiState.update {
            it.copy(currentActividad = selectedActividad)
        }
    }

    // Función para navegar a la página de lista
    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }

    // Función para navegar a la página de detalle
    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }

    // Función para crear una reserva en Firestore
    fun crearReservaFirestore(nomActividad: String, fecha: String, numPersonas: Int, telefono: String) {
        // Acceder a la colección "reservas"
        val reservasRef = db.collection("reservas")

        // Crear un nuevo documento con un ID automático
        val reserva = hashMapOf(
            "actividad" to nomActividad,
            "fecha" to fecha,
            "numPersonas" to numPersonas,
            "telefono" to telefono,
            "timestamp" to FieldValue.serverTimestamp() // Registrar el momento exacto de la reserva
        )

        // Agregar el documento a la colección "reservas"
        reservasRef.add(reserva)
            .addOnSuccessListener { documentReference ->
                // Éxito al agregar el documento
                println("Documento agregado con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                // Error al agregar el documento
                println("Error al agregar el documento: $e")
            }
    }
}

// Estado de la interfaz de usuario para la pantalla de inicio
data class ActividadesUiState(
    val actividadesList: List<Actividad> = emptyList(), // Lista de actividades
    val currentActividad: Actividad = ActividadesDataProvider.defaultActividad, // Actividad actual seleccionada
    val isShowingListPage: Boolean = true // Booleano para indicar si se muestra la página de lista o no
)
