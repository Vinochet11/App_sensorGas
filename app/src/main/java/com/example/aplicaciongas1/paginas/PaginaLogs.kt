package com.example.aplicaciongas1.paginas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val FondoMorado = Color(0xFF5C1A99)
private val TarjetaOscura = Color(0xFF1E1E1E)
private val TextoPrincipal = Color(0xFFFFFFFF)
private val TextoSecundario = Color(0xFFBDBDBD)

private fun formatearFechaLogs(timestamp: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formato.format(Date(timestamp))
}
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
    val refHistorial = remember { Firebase.database.getReference("sensor_history") }
    var historial by remember { mutableStateOf(listOf<String>()) }

    LaunchedEffect(Unit) {
        refHistorial.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = snapshot.children.mapNotNull { item ->
                    val value = item.child("value").getValue(Int::class.java)
                        ?: return@mapNotNull null
                    val ts = item.child("timestamp").getValue(Long::class.java) ?: 0L

                    val fecha = if (ts > 0) formatearFechaLogs(ts) else "sin fecha"
                    "[$fecha] Nivel: $value"
                }
                    .takeLast(20)
                    .reversed()

                historial = lista
            }

            override fun onCancelled(error: DatabaseError) {
                historial = listOf("Error leyendo historial: ${error.message}")
            }
        })
    }

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

            if (historial.isEmpty()) {
                Text(
                    text = "Aún no hay historial del sensor.",
                    color = TextoSecundario,
                    fontSize = 14.sp
                )
            } else {
                historial.forEach { row ->
                    Text(
                        text = "• $row",
                        color = TextoSecundario,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun LogsAccionesCard() {
    var acciones by remember { mutableStateOf(listOf<String>()) }
    val refAcciones = remember { Firebase.database.getReference("acciones") }

    LaunchedEffect(Unit) {
        refAcciones.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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
                }.reversed()

                acciones = lista
            }

            override fun onCancelled(error: DatabaseError) {
                acciones = listOf("Error leyendo acciones: ${error.message}")
            }
        })
    }

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
                acciones.take(30).forEach { log ->
                    Text(text = "• $log", color = TextoSecundario, fontSize = 14.sp)
                }
            }
        }
    }
}