package com.example.aplicaciongas1.paginas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.database

private val FondoMorado = Color(0xFF5C1A99)
private val TarjetaOscura = Color(0xFF1E1E1E)
private val TextoPrincipal = Color(0xFFFFFFFF)
private val TextoSecundario = Color(0xFFBDBDBD)

@Composable
fun PaginaLogs(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoMorado)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HistorialSensorCard()
        LogsAccionesCard()
    }
}

@Composable
fun HistorialSensorCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TarjetaOscura),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Historial del sensor",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextoPrincipal
            )
            Text(
                text = "Sin datos del sensor",
                color = TextoSecundario,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun LogsAccionesCard() {
    var acciones by remember { mutableStateOf(listOf<String>()) }

    val dbRef = remember {
        Firebase.database.getReference("acciones")
    }


    LaunchedEffect(Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val lista = snapshot.children.mapNotNull { child ->
                val tipo = child.child("tipo").getValue(String::class.java) ?: return@mapNotNull null
                val estado = child.child("nuevoEstado").getValue(Boolean::class.java)
                val fechaHora = child.child("fechaHora").getValue(String::class.java) ?: ""

                val estadoTexto = when (estado) {
                    true -> "ENCENDIDO"
                    false -> "APAGADO"
                    null -> ""
                }

                "[$fechaHora] $tipo → $estadoTexto"
            }.reversed()  // último primero

            acciones = lista
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = TarjetaOscura),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Logs de funciones",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextoPrincipal
            )

            if (acciones.isEmpty()) {
                Text(
                    text = "Aún no hay acciones registradas.",
                    color = TextoSecundario,
                    fontSize = 14.sp
                )
            } else {
                acciones.take(20).forEach { log ->
                    Text(
                        text = "• $log",
                        color = TextoSecundario,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}