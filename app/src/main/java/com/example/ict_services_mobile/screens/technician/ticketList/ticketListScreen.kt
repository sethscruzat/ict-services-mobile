package com.example.ict_services_mobile.screens.technician.ticketList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ict_services_mobile.screens.technician.profile.TechBottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicketListScreen(modifier: Modifier = Modifier, navController: NavHostController, taskIDList: List<Pair<Int,String>>, techID: Int){
    Scaffold(
        bottomBar =  { TechBottomNavigation(navController = navController, techID) }
    ){
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
            Spacer(modifier.weight(0.1f))

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(2f),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                contentPadding = PaddingValues(start = 18.dp, end = 18.dp)
            ){
                items(taskIDList) { item ->
                    GenerateTechTaskList(navController = navController,
                        equipmentID = item.second, ticketID = item.first, techID = techID)
                    HorizontalDivider(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(
                                start = 0.dp,
                                top = 12.dp,
                                end = 0.dp,
                                bottom = 2.dp
                            ),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}


@Composable
fun GenerateTechTaskList(modifier: Modifier = Modifier, navController: NavHostController,
                         equipmentID: String, ticketID: Int, techID:Int) {
    Row(modifier = modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(modifier = modifier
            .weight(1f)
            .padding(horizontal = 9.dp),text = equipmentID, fontSize = 18.sp)
        IconButton(onClick = {
            navController.navigate("techTicketInfo/{techID}/{ticketID}"
                .replace(oldValue = "{techID}", newValue = techID.toString())
                .replace(oldValue = "{ticketID}", newValue = ticketID.toString())) {
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Icon(Icons.AutoMirrored.Outlined.ArrowForwardIos, contentDescription = "Open", modifier = modifier.size(16.dp))
        }
    }

}