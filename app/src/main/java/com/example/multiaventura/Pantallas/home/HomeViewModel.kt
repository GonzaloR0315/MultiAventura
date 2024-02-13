package com.example.multiaventura.Pantallas.home


import androidx.lifecycle.ViewModel
import com.example.multiaventura.data.ActividadesDataProvider
import com.example.multiaventura.model.Actividad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

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
}

data class ActividadesUiState(
    val actividadesList: List<Actividad> = emptyList(),
    val currentActividad: Actividad = ActividadesDataProvider.defaultActividad,
    val isShowingListPage: Boolean = true
)