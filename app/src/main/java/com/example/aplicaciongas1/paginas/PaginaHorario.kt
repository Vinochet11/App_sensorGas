package com.example.aplicaciongas1.paginas

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun PaginaHorario(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5C1A99)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SeleccionHorario()
    }
}

@Composable
fun SeleccionHorario() {
    val context = LocalContext.current

    var horaInicio by remember { mutableStateOf("08:00") }
    var horaFin by remember { mutableStateOf("18:00") }

    val refInicio = remember { Firebase.database.getReference("horarios/inicio") }
    val refFin = remember { Firebase.database.getReference("horarios/fin") }

    // Cargar valores guardados al entrar
    LaunchedEffect(Unit) {
        refInicio.get().addOnSuccessListener { snap ->
            val v = snap.getValue(String::class.java)
            if (!v.isNullOrBlank()) horaInicio = v
        }
        refFin.get().addOnSuccessListener { snap ->
            val v = snap.getValue(String::class.java)
            if (!v.isNullOrBlank()) horaFin = v
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C1A99))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Selecciona el horario de inicio y fin",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            val (h, m) = horaInicio.split(":").map { it.toIntOrNull() ?: 0 }
            val timePicker = TimePickerDialog(
                context,
                { _, hour: Int, minute: Int ->
                    horaInicio = String.format("%02d:%02d", hour, minute)
                    refInicio.setValue(horaInicio) // ✅ guardar en Firebase
                },
                h, m, true
            )
            timePicker.show()
        }) {
            Text("Hora de inicio: $horaInicio")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val (h, m) = horaFin.split(":").map { it.toIntOrNull() ?: 0 }
            val timePicker = TimePickerDialog(
                context,
                { _, hour: Int, minute: Int ->
                    horaFin = String.format("%02d:%02d", hour, minute)
                    refFin.setValue(horaFin) // ✅ guardar en Firebase
                },
                h, m, true
            )
            timePicker.show()
        }) {
            Text("Hora de fin: $horaFin")
        }
    }
}

