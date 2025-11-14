package com.example.aplicaciongas1

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicaciongas1.paginas.PaginaConfiguracion
import com.example.aplicaciongas1.paginas.PaginaHorario
import com.example.aplicaciongas1.paginas.PaginaLogs
import com.example.aplicaciongas1.paginas.PaginaPrincipal
import com.example.aplicaciongas1.paginas.PaginaTest
import com.example.aplicaciongas1.ui.theme.AplicacionGas1Theme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            AplicacionGas1Theme {
                    AuthApp()



                }
            }
        }
    }


@Composable
fun AuthApp(){
    val navController = rememberNavController()
    NavHost(navController,startDestination = "login"){
        composable ("login") { LoginPantalla(navController) }
        composable ("register") { RegistroPantalla(navController) }
        composable ("home") { HomePantalla(navController)}
    }
}
