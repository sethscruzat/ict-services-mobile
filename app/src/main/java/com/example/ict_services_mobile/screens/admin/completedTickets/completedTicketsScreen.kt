package com.example.ict_services_mobile.screens.admin.completedTickets

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ict_services_mobile.api.model.TicketModel
import com.example.ict_services_mobile.screens.admin.ticketForm.AdminBottomNavigation
import com.example.ict_services_mobile.screens.technician.ticketList.GenerateTechTaskList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTicketsScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: CompletedTicketsViewModel, adminID: Int, ticketList: List<TicketModel>) {
    val ctx = LocalContext.current
    Scaffold(
        bottomBar =  { AdminBottomNavigation(navController = navController, adminID) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Button(
                    modifier = modifier
                        .padding(12.dp)
                        .align(Alignment.End),
                    onClick = {
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
                )
                {
                    Text("Logout")
                }
                Spacer(modifier = Modifier.weight(0.1f))
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(2f),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    contentPadding = PaddingValues(start = 18.dp, end = 18.dp)
                ){
                    items(ticketList) { item ->

                    }
                }
            }
        }
    }
}