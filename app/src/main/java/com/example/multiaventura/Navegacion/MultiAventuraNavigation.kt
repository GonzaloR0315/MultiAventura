package com.example.multiaventura.Navegacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.multiaventura.Pantallas.home.Home // Importa la pantalla principal (Home)
import com.example.multiaventura.Pantallas.login.LoginScreen // Importa la pantalla de inicio de sesión (LoginScreen)

@Composable
fun MultiAventuraNavigation(){
    // Crea un controlador de navegación
    val navController = rememberNavController()

    // Define el NavHost con las diferentes rutas de navegación
    NavHost(
        navController = navController,
        startDestination = MultiAventuraScreens.LoginScreen.name // Pantalla de inicio: LoginScreen
    ){
        // Define la ruta para la pantalla de inicio de sesión (LoginScreen)
        composable(MultiAventuraScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }

        // Define la ruta para la pantalla principal (HomeScreen)
        composable(MultiAventuraScreens.HomeScreen.name){
            Home(navController = navController)
        }
    }
}
