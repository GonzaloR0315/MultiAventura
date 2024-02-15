package com.example.multiaventura.Pantallas.home


import androidx.lifecycle.ViewModel
import com.example.multiaventura.data.ActividadesDataProvider
import com.example.multiaventura.model.Actividad
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


class HomeViewModel : ViewModel() {

    var actividad: String = ""
    var date: String = ""
    private val db = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow(
        ActividadesUiState(
            actividadesList = ActividadesDataProvider.getActividadData(),
            currentActividad = ActividadesDataProvider.getActividadData().getOrElse(0) {
                ActividadesDataProvider.defaultActividad
            }
        )
    )
    val uiState: StateFlow<ActividadesUiState> = _uiState

    fun updateCurrentActividad(selectedActividad: Actividad) {
        _uiState.update {
            it.copy(currentActividad = selectedActividad)
        }
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }


    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPage = false)
        }
    }

    fun crearReservaFirestore(nomActividad: String, fecha: String, numPersonas: Int, telefono: String) {
        // Acceder a la colección "reservas"
        val reservasRef = db.collection("reservas")

        // Crear un nuevo documento con un ID automático
        val reserva = hashMapOf(
            "actividad" to nomActividad,
            "fecha" to fecha,
            "numPersonas" to numPersonas,
            "telefono" to telefono,
            "timestamp" to FieldValue.serverTimestamp() // Usamos FieldValue.serverTimestamp() para registrar el momento exacto de la reserva
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

data class ActividadesUiState(
    val actividadesList: List<Actividad> = emptyList(),
    val currentActividad: Actividad = ActividadesDataProvider.defaultActividad,
    val isShowingListPage: Boolean = true
)