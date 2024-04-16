package com.example.ict_services_mobile.screens.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun LandingScreen(modifier: Modifier = Modifier, navController: NavHostController, authViewModel: AuthViewModel) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(text = "FILLER LANDING SCREEN", modifier = modifier
            .padding(
                vertical = 6.dp,
                horizontal = 14.dp
            ),
            fontSize = 17.sp)
    }

    val isLoggedIn = authViewModel.isLoggedIn()
    val userID = authViewModel.getUserID()
    val userRole = authViewModel.getUserRole()

    if(isLoggedIn){
        if(userRole == "technician"){
            navController.navigate("techProfile/{techID}".replace(oldValue = "{techID}", newValue = userID.toString())) {
                navController.graph.startDestinationRoute?.let { screenroute ->
                    popUpTo(screenroute) {
                        saveState = false
                    }
                }
                launchSingleTop = true
                restoreState = false
            }
        }else if(userRole == "admin"){
            navController.navigate("adminTicketForm/{adminID}".replace(oldValue = "{adminID}", newValue = userID.toString())) {
                navController.graph.startDestinationRoute?.let { screenroute ->
                    popUpTo(screenroute) {
                        saveState = false
                    }
                }
                launchSingleTop = true
                restoreState = false
            }
        }
    }else{
        navController.navigate("login") {
            navController.graph.startDestinationRoute?.let { screenroute ->
                popUpTo(screenroute) {
                    saveState = false
                }
            }
            launchSingleTop = true
            restoreState = false
        }
    }
}