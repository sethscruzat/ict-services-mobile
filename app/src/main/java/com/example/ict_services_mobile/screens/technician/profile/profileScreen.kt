package com.example.ict_services_mobile.screens.technician.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ict_services_mobile.BottomNavItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavHostController, profileViewModel: ProfileViewModel, email: String) {
    val viewModel: ProfileViewModel = viewModel()
    val dataLoaded = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if(!dataLoaded.value){
            viewModel.getTechnicianData(email)
            dataLoaded.value = true
        }
    }
    val name by viewModel.name.collectAsState()
    Scaffold(
        bottomBar =  { BottomNavigation(navController = navController, email) }
    ){
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
        )
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
            Column(modifier = modifier.weight(2f)){
                Text(text = "Name: $name", modifier = modifier
                    .padding(
                        vertical = 6.dp,
                        horizontal = 14.dp
                    ),
                    fontSize = 17.sp)
            }
        }
    }
}

@Composable
fun BottomNavigation(navController: NavController, email: String) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(
        BottomNavItem.Calculator,
        BottomNavItem.Generator,
    )
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 15.sp) },
                alwaysShowLabel = true,
                selected = currentRoute == item.screenroute,
                onClick = {
                    navController.navigate("${item.screenroute}/{email}".replace(oldValue = "{email}", newValue = email)) {
                        navController.graph.startDestinationRoute?.let { screenroute ->
                            popUpTo(screenroute) {
                                saveState = false
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
