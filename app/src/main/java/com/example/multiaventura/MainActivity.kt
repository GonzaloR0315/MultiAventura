// Paquete donde se encuentra la clase MainActivity
package com.example.multiaventura

// Importaciones necesarias para la clase MainActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.multiaventura.Navegacion.MultiAventuraNavigation
import com.example.multiaventura.ui.theme.MultiAventuraTheme

// Clase MainActivity que extiende de ComponentActivity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establecer el contenido de la actividad
        setContent {
            // Aplicar el tema MultiAventuraTheme a la actividad
            MultiAventuraTheme {
                // Contenedor visual Surface utilizando el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamar a la función MultiAventuraApp para mostrar el contenido de la actividad
                    MultiAventuraApp()
                }
            }
        }
    }
}

// Función composable MultiAventuraApp que define la interfaz de usuario de la actividad
@Composable
fun MultiAventuraApp() {
    // Contenedor visual Surface con modificadores para tamaño y relleno, utilizando el color de fondo del tema
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        // Columna que organiza los elementos de manera vertical y centrada
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Llamar a la función MultiAventuraNavigation para mostrar la navegación de la actividad
            MultiAventuraNavigation()
        }
    }
}
