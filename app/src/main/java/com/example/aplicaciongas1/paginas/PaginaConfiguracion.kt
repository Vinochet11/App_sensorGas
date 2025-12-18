package com.example.aplicaciongas1.paginas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.database
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun obtenerFechaHoraActual(): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formato.format(Date())
}

fun logAccion(tipo: String, valor: Int) {
    val refAcciones = Firebase.database.getReference("acciones")
    val log = mapOf(
        "tipo" to tipo,
        "nuevoValor" to valor,
        "fechaHora" to obtenerFechaHoraActual()
    )
    refAcciones.push().setValue(log)
}
@Composable
fun PaginaConfiguracion(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5C1A99))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SliderConfig(
            titulo = "Configuración Total",
            path = "configuracion/configuracionTotal",
            logTipo = "CONFIG_TOTAL"
        )

        SliderConfig(
            titulo = "Intensidad del LED Azul",
            path = "configuracion/intensidadLedAzul",
            logTipo = "CONFIG_LED_AZUL"
        )

        SliderConfig(
            titulo = "Intensidad del LED Rojo",
            path = "configuracion/intensidadLedRojo",
            logTipo = "CONFIG_LED_ROJO"
        )

        SliderConfig(
            titulo = "Volumen del Buzzer",
            path = "configuracion/volumenBuzzer",
            logTipo = "CONFIG_BUZZER"
        )

        SliderConfig(
            titulo = "Fuerza del Ventilador",
            path = "configuracion/fuerzaVentilador",
            logTipo = "CONFIG_VENTILADOR"
        )
    }
}

@Composable
fun SliderConfig(
    titulo: String,
    path: String,
    logTipo: String
) {
    val range = 0f..100f
    var selection by remember { mutableFloatStateOf(50f) }
    val dbRef = remember { Firebase.database.getReference(path) }

    LaunchedEffect(Unit) {
        dbRef.get().addOnSuccessListener {
            it.getValue(Int::class.java)?.let { v ->
                selection = v.toFloat()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "$titulo ${selection.toInt()}%",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )

        Slider(
            value = selection,
            valueRange = range,
            onValueChange = { selection = it },
            onValueChangeFinished = {
                val valor = selection.toInt()
                dbRef.setValue(valor)
                logAccion(logTipo, valor)
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}