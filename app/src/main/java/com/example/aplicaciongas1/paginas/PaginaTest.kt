package com.example.aplicaciongas1.paginas

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase


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

        TestTodo()
        TestLedRojo()
        TestLedAzul()
        TestBuzzer()
        TestVentilador()
        BotonModo()
    }
}
@Composable
fun BotonModo(){
    val db= FirebaseDatabase.getInstance().getReference("test_control")
    val context= LocalContext.current
    Button(onClick = {
        db.child("mode").get().addOnSuccessListener{
            val current = it.getValue(String::class.java) ?: "auto"
            val newMode= if(current=="auto") "manual" else "auto"
            db.child("mode").setValue(newMode)

            Toast.makeText(
                context,"Modo cambiado a $newMode",
                Toast.LENGTH_SHORT
            ).show()
        }
    },
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
        modifier = Modifier.fillMaxWidth(0.8f).height(55.dp)
    ) {
        Text("cambiar modo Automatico/Manual", color = Color.White)
    }
}


fun toggleValue(path: String) {
    val ref = FirebaseDatabase.getInstance().getReference("test_control").child(path)
    ref.get().addOnSuccessListener {
        val current = it.getValue(Boolean::class.java) ?: false
        ref.setValue(!current)
    }
}


@Composable
fun TestVentilador() {
    val context = LocalContext.current

    Button(
        onClick = {
            toggleValue("ventilador")
            Toast.makeText(context, "Ventilador ON / OFF", Toast.LENGTH_SHORT).show()
        },
        colors = ButtonDefaults.buttonColors(containerColor = BotonVerde),
        modifier = Modifier.fillMaxWidth(0.8f).height(55.dp)
    ) {
        Text("Prueba del Ventilador", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}
@Composable
fun TestLedAzul() {
    Button(
        onClick = { toggleValue("led_azul") },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)),
        modifier = Modifier.fillMaxWidth(0.8f).height(55.dp)
    ) {
        Text("Prueba del Led Azul", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}
@Composable
fun TestBuzzer() {
    val context = LocalContext.current

    Button(
        onClick = {
            toggleValue("buzzer")
            Toast.makeText(context, "Buzzer ON / OFF", Toast.LENGTH_SHORT).show()
        },
        colors = ButtonDefaults.buttonColors(containerColor = BotonAzul),
        modifier = Modifier.fillMaxWidth(0.8f).height(55.dp)
    ) {
        Text("Prueba del Buzzer", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}
@Composable
fun TestLedRojo() {
    Button(
        onClick = {setValue("led_rojo",true)},
        colors = ButtonDefaults.buttonColors(containerColor = BotonRojo),
        modifier = Modifier.fillMaxWidth(0.8f).height(55.dp)

    ) {
        Text("encender Led Rojo",color=TextoPrincipal)
    }
}

@Composable
fun TestTodo() {
    val ref = FirebaseDatabase.getInstance().getReference("test_control")

    Button(
        onClick = {
            ref.get().addOnSuccessListener {
                val current = it.child("led_rojo").getValue(Boolean::class.java) ?: false
                val newState = !current

                ref.child("led_rojo").setValue(newState)
                ref.child("led_azul").setValue(newState)
                ref.child("buzzer").setValue(newState)
                ref.child("ventilador").setValue(newState)
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = BotonGris),
        modifier = Modifier.fillMaxWidth(0.8f).height(55.dp)
    ) {
        Text("Sistema completo ON / OFF", color = TextoPrincipal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

fun setValue(path: String, value: Boolean){
    FirebaseDatabase.getInstance()
        .getReference("test_control")
        .child(path)
        .setValue(value)
}