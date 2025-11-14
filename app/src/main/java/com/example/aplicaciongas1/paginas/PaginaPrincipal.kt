package com.example.aplicaciongas1.paginas

import android.R.attr.icon
import android.widget.Button
import android.widget.Toast
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.database
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect   // 👈 agregado
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset.Companion.Unspecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicaciongas1.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val FondoMorado = Color(0xFF5C1A99)
private val FondoNegro= Color(0xFF000000)
private val TarjetaOscura = Color(0xFF1E1E1E)
private val TextoPrincipal = Color(0xFFFFFFFF)
private val TextoSecundario = Color(0xFFBDBDBD)
private val Divisor = Color.White.copy(alpha = 0.15f)
private val BotonEncendido = Color(0xFF2E7D32)
private val BotonApagado = Color(0xFFE32B07)

@Composable
fun PaginaPrincipal(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoMorado)
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        tarjeta()
        Spacer(modifier=Modifier.weight(1f))
        SimpleSlider()
        Spacer(modifier=Modifier.weight(1f))
        BotonPrender { }
        Spacer(modifier=Modifier.weight(1f))
    }
}

@Composable
fun SimpleSlider() {
    val range = 0f..100f
    var selection by remember { mutableFloatStateOf(50f) }

    val dbRef = remember {
        Firebase.database.getReference("configuracion/sensibilidadSensorGas")
    }

    LaunchedEffect(Unit) {
        dbRef.get().addOnSuccessListener { snapshot ->
            val value = snapshot.getValue(Int::class.java)
            if (value != null) {
                selection = value.toFloat()
            }
        }
    }

    Text(
        text = "Sensibilidad del sensor de gas: ${selection.toInt()}%",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = TextoSecundario
    )

    Slider(
        value = selection,
        valueRange = range,
        onValueChange = {
            selection = it
            dbRef.setValue(selection.toInt())
        },
        colors = SliderDefaults.colors(
            thumbColor = TextoPrincipal,
            activeTrackColor = TextoPrincipal,
            inactiveTrackColor = TextoPrincipal.copy(alpha = 0.3f)
        )
    )
}

@Composable
fun BotonPrender(onCick: () -> Unit) {
    val context = LocalContext.current
    var encendido by remember { mutableStateOf(false) }

    val database = Firebase.database
    val refEstado = database.getReference("estado/sensorEncendido")
    val refAcciones = database.getReference("acciones")

    LaunchedEffect(Unit) {
        refEstado.get().addOnSuccessListener { snapshot ->
            val value = snapshot.getValue(Boolean::class.java)
            if (value != null) {
                encendido = value
            }
        }
    }

    Button(
        onClick = {
            encendido = !encendido
            val msg = if (encendido) "Sensor encendido" else "Sensor apagado"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            refEstado.setValue(encendido)
            val fechaHora = obtenerFechaHoraActual()
            val logAccion = mapOf(
                "tipo" to "TOGGLE_SENSOR",
                "nuevoEstado" to encendido,
                "fechaHora" to fechaHora
            )
            refAcciones.push().setValue(logAccion)

            onCick()
        },
        modifier = Modifier.padding(top = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (encendido) BotonEncendido else BotonApagado
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(18.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic),
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = Color.White
        )
    }
}

private fun obtenerFechaHoraActual():String{
    val formato = SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss", Locale.getDefault())
    val ahora = Date()
    return formato.format(ahora)
}

@Composable
fun tarjeta() {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = TarjetaOscura),
        shape = CardDefaults.shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Sensor de Gas",
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                fontSize = 18.sp
            )
            Text(
                text = "Nivel Actual: 50 ppm",
                color = TextoSecundario,
                fontSize = 14.sp
            )
        }
    }
}
