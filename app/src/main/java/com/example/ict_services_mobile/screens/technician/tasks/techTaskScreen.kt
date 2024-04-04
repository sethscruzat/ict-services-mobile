package com.example.ict_services_mobile.screens.technician.tasks

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ict_services_mobile.screens.technician.profile.BottomNavigation
import com.example.ict_services_mobile.screens.technician.profile.ProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TechTaskScreen(modifier: Modifier = Modifier, navController: NavHostController, techTaskViewModel: TechTaskViewModel, email: String){
    val viewModel: TechTaskViewModel = viewModel()
    val dataLoaded = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if(!dataLoaded.value){
            viewModel.getTechTaskItems(email)
            dataLoaded.value = true
        }
    }
    val taskIDList by viewModel.taskIDList.collectAsState()
    Scaffold(
        bottomBar =  { BottomNavigation(navController = navController, email) }
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
                    val index = taskIDList.indexOf(item)
                    GenerateTechTaskList(navController = navController,
                        equipmentID = item, index = index, email = email)
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
                         equipmentID: String, index: Int, email: String) {
    Row(modifier = modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(modifier = modifier
            .weight(1f)
            .padding(horizontal = 9.dp),text = equipmentID, fontSize = 18.sp)
        IconButton(onClick = {
            navController.navigate("techCards/{email}/{index}"
                .replace(oldValue = "{index}", newValue = index.toString())
                .replace(oldValue = "{email}", newValue = email)) {
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Icon(Icons.AutoMirrored.Outlined.ArrowForwardIos, contentDescription = "Open", modifier = modifier.size(16.dp))
        }
    }

}