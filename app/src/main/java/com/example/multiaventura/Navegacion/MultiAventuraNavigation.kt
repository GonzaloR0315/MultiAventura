package com.example.multiaventura.Navegacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.multiaventura.Pantallas.home.Home
import com.example.multiaventura.Pantallas.login.LoginScreen

@Composable
fun MultiAventuraNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MultiAventuraScreens.LoginScreen.name
    ){

        composable(MultiAventuraScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(MultiAventuraScreens.HomeScreen.name){
            Home(navController = navController)
        }
    }

}