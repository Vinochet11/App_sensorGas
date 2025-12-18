package com.example.aplicaciongas1.paginas

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicaciongas1.R
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
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

private fun formatearFecha(timestamp:Long):String{
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formato.format(Date(timestamp))
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
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val ahora = Date()
    return formato.format(ahora)
}


@Composable
fun tarjeta() {

    val dbRef = remember {
        Firebase.database.getReference("sensor_data")
    }

    var sensorValue by remember { mutableStateOf<Int?>(null) }
    var lastUpdate by remember { mutableStateOf<Long?>(null) }


    LaunchedEffect(Unit) {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sensorValue = snapshot.child("value").getValue(Int::class.java)
                lastUpdate = snapshot.child("timestamp").getValue(Long::class.java)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PaginaPrincipal", "Error leyendo sensor_data: ${error.message}")
            }
        })
    }

    val textoNivel = if (sensorValue == null) {
        "Nivel actual: cargando..."
    } else {
        "Nivel actual: $sensorValue"
    }


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
                text = textoNivel,
                color = TextoSecundario,
                fontSize = 14.sp
            )
            if (lastUpdate != null) {
                Text(
                    text = "Última actualización: ${formatearFecha(lastUpdate!!)}",
                    color = TextoSecundario,
                    fontSize = 12.sp
                )

            }
        }
    }
}