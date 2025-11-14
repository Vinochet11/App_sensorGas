package com.example.aplicaciongas1

import android.R.attr.label
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.aplicaciongas1.paginas.PaginaConfiguracion
import com.example.aplicaciongas1.paginas.PaginaHorario
import com.example.aplicaciongas1.paginas.PaginaLogs
import com.example.aplicaciongas1.paginas.PaginaPrincipal
import com.example.aplicaciongas1.paginas.PaginaTest

@Composable

fun HomePantalla(
    navController: NavController,
    modifier: Modifier = Modifier

) {


    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("test", Icons.Default.PlayArrow),
        NavItem("horario", Icons.Default.DateRange),
        NavItem("logs", Icons.Default.Info),
        NavItem("configuracion", Icons.Default.Build),

    )

    var selectedIndex by remember{
        mutableStateOf(0)

    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar={
            NavigationBar{
                navItemList.forEachIndexed {index, navItem ->
                   NavigationBarItem(
                       selected = selectedIndex ==index,
                       onClick = {
                           selectedIndex = index
                       },
                       icon = {
                           Icon(imageVector = navItem.icon, contentDescription = "Icon")
                       },
                       label={
                           Text(text=navItem.label)
                       })}}


            }
        ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding),selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier,selectedIndex:Int){
    when(selectedIndex){
        0->PaginaPrincipal()
        1->PaginaTest()
        2->PaginaHorario()
        3->PaginaLogs()
        4->PaginaConfiguracion()
    }
}