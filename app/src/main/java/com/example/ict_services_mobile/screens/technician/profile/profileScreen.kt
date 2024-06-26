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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ict_services_mobile.screens.loading.AuthViewModel
import com.example.ict_services_mobile.TechnicianNavItem
import com.example.ict_services_mobile.api.model.TicketModel
import com.example.ict_services_mobile.api.model.UserDataModel

/*
    Note: userInfo.remarks has the remarks array, just a for each on it.
          ticketList contains the list of completed tickets
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navController: NavHostController, techID: Int, userInfo: UserDataModel, ticketList: List<TicketModel>, authViewModel: AuthViewModel) {
    val name = "${userInfo.firstName} ${userInfo.lastName}"
    Scaffold(
        bottomBar =  { TechBottomNavigation(navController = navController, techID) }
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
                    authViewModel.logout()
                    authViewModel.setLoggedIn(false)
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
fun TechBottomNavigation(navController: NavController, techID: Int) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(
        TechnicianNavItem.Profile,
        TechnicianNavItem.TechTicketList,
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
                    navController.navigate("${item.screenroute}/{techID}".replace(oldValue = "{techID}", newValue = techID.toString())) {
                        navController.graph.startDestinationRoute?.let { screenroute ->
                            popUpTo(screenroute) {
                                saveState = true
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
