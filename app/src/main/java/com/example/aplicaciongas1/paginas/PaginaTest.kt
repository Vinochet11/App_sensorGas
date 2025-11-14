package com.example.aplicaciongas1.paginas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val FondoMorado = Color(0xFF5C1A99)
private val TextoPrincipal = Color(0xFFFFFFFF)
private val BotonAzul = Color(0xFF0D47A1)
private val BotonVerde = Color(0xFF2E7D32)
private val BotonRojo = Color(0xFFB71C1C)
private val BotonGris = Color(0xFF424242)

@Composable
fun PaginaTest(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoMorado)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Panel de Pruebas del Sistema",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(10.dp))

        TestTodo {}
        TestLedRojo {}
        TestLedAzul {}
        TestBuzzer {}
        TestVentilador {}
    }
}

@Composable
fun TestVentilador(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = BotonVerde),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(55.dp)
    ) {
        Text("Prueba del Ventilador", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TestBuzzer(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = BotonAzul),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(55.dp)
    ) {
        Text("Prueba del Buzzer", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TestLedAzul(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(55.dp)
    ) {
        Text("Prueba del Led Azul", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TestLedRojo(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = BotonRojo),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(55.dp)
    ) {
        Text("Prueba del Led Rojo", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun TestTodo(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(containerColor = BotonGris),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(55.dp)
    ) {
        Text("Funcionamiento del sistema completo", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}