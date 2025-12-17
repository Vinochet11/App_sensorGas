package com.example.aplicaciongas1.paginas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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

@Composable
fun PaginaConfiguracion(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF5C1A99))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        total()
        Spacer(Modifier.width(12.dp))
        IntLedAzul()
        Spacer(Modifier.width(12.dp))
        IntLedRojo()
        Spacer(Modifier.width(12.dp))
        volBuzzer()
        Spacer(Modifier.width(12.dp))
        fuerzaVentilador()
    }
}

@Composable
fun total() {
    val range = 0f..100f
    var selection by remember { mutableFloatStateOf(50f) }


    val dbRef = remember { Firebase.database.getReference("configuracion/configuracionTotal") }


    LaunchedEffect(Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val value = snapshot.getValue(Int::class.java)
            if (value != null) {
                selection = value.toFloat()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Configuración Total ${selection.toInt()}%",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Slider(
            value = selection,
            valueRange = range,
            onValueChange = {
                selection = it
                dbRef.setValue(selection.toInt())
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun IntLedAzul() {
    val range = 0f..100f
    var selection by remember { mutableFloatStateOf(50f) }

    val dbRef = remember { Firebase.database.getReference("configuracion/intensidadLedAzul") }

    LaunchedEffect(Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val value = snapshot.getValue(Int::class.java)
            if (value != null) {
                selection = value.toFloat()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Intensidad del LED azul ${selection.toInt()}%",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Slider(
            value = selection,
            valueRange = range,
            onValueChange = {
                selection = it
                dbRef.setValue(selection.toInt())
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun IntLedRojo() {
    val range = 0f..100f
    var selection by remember { mutableFloatStateOf(50f) }

    val dbRef = remember { Firebase.database.getReference("configuracion/intensidadLedRojo") }

    LaunchedEffect(Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val value = snapshot.getValue(Int::class.java)
            if (value != null) {
                selection = value.toFloat()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Intensidad del LED rojo ${selection.toInt()}%",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Slider(
            value = selection,
            valueRange = range,
            onValueChange = {
                selection = it
                dbRef.setValue(selection.toInt())
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun volBuzzer() {
    val range = 0f..100f
    var selection by remember { mutableFloatStateOf(50f) }

    val dbRef = remember { Firebase.database.getReference("configuracion/volumenBuzzer") }

    LaunchedEffect(Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val value = snapshot.getValue(Int::class.java)
            if (value != null) {
                selection = value.toFloat()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Volumen del buzzer ${selection.toInt()}%",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Slider(
            value = selection,
            valueRange = range,
            onValueChange = {
                selection = it
                dbRef.setValue(selection.toInt())
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}

@Composable
fun fuerzaVentilador() {
    val range = 0f..100f
    var selection by remember { mutableFloatStateOf(50f) }

    val dbRef = remember { Firebase.database.getReference("configuracion/fuerzaVentilador") }

    LaunchedEffect(Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val value = snapshot.getValue(Int::class.java)
            if (value != null) {
                selection = value.toFloat()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Fuerza del ventilador ${selection.toInt()}%",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Slider(
            value = selection,
            valueRange = range,
            onValueChange = {
                selection = it
                dbRef.setValue(selection.toInt())
            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )
    }
}