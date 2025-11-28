package com.example.aplicaciongas1.paginas

import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PaginaHorario(modifier: Modifier=Modifier){
    Column(
        modifier=modifier.fillMaxSize()
            .background(Color(0xFF5C1A99)),
        verticalArrangement  = Arrangement.Center,
        horizontalAlignment =Alignment.CenterHorizontally
    ){
        seleccionHorario()
    }
}

@Composable
fun seleccionHorario(){
    val context=LocalContext.current
    var horaInicio by remember { mutableStateOf("08:00") }
    var horaFin by remember { mutableStateOf("18:00") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5C1A99))
            .padding(24.dp),
        horizontalAlignment= Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text("Selecciona el horario de inicio y fin", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier= Modifier.height(24.dp))

        Button(onClick = {
            val timePicker= TimePickerDialog(context,{
                _,hour:Int,minute:Int->
                horaInicio=String.format("%02d:%02d",hour,minute)
            },
             8,0,true
            )
            timePicker.show()
        }){
            Text("Seleccionar Hora de inicio: $horaInicio")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val timePicker = TimePickerDialog(
                context,
                { _, hour: Int, minute: Int ->
                    horaFin = String.format("%02d:%02d", hour, minute)
                },
                18, 0, true
            )
            timePicker.show()
        }) {
            Text("Seleccionar hora de fin: $horaFin")
        }
    }
}



