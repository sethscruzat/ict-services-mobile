package com.example.ict_services_mobile.screens.admin.ticketForm

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ict_services_mobile.AdminNavItem
import com.example.ict_services_mobile.AuthViewModel
import com.example.ict_services_mobile.api.model.AssignedToModel
import com.example.ict_services_mobile.api.model.IssuedByModel
import com.example.ict_services_mobile.api.model.TicketModel
import com.example.ict_services_mobile.api.model.UserDataModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketFormScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: TicketFormViewModel, adminID: Int, techList: List<UserDataModel>, authViewModel: AuthViewModel) {
    val ctx = LocalContext.current
    val adminData by viewModel.adminInfo.collectAsState()
    Scaffold(
        bottomBar =  { AdminBottomNavigation(navController = navController, adminID) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
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
            var equipmentID by remember { mutableStateOf("") }
            var location by remember { mutableStateOf("") }
            var remarks by remember { mutableStateOf("") }
            var issuedToID by remember { mutableIntStateOf(0) }
            var issuedToName by remember { mutableStateOf("") }
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = equipmentID,
                onValueChange = { equipmentID = it },
                label = { Text(text = "Equipment ID") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 24.dp, end = 12.dp, bottom = 6.dp)
            )
            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text(text = "Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 24.dp, end = 12.dp, bottom = 6.dp)
            )
            TextField(
                value = remarks,
                onValueChange = { remarks = it },
                label = { Text(text = "Remarks") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 24.dp, end = 12.dp, bottom = 6.dp)
            )

            /* TODO: Update Database to include a field called "availability" and make this drop down only
                display available technicians */
            var isExpanded by remember { mutableStateOf(false) }
            Box(modifier = Modifier.fillMaxWidth()) {
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 24.dp, end = 12.dp, bottom = 6.dp)
                ) {
                    TextField(
                        value = issuedToName, onValueChange = {}, readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )
                    DropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        techList.forEach { item ->
                            val name = "${item.firstName} ${item.lastName}"
                            DropdownMenuItem(
                                text = { Text(text = name) },
                                onClick = {
                                    issuedToName = name
                                    issuedToID = item.techID
                                    isExpanded = false
                                })
                        }
                    }
                }
            }

            Button(
                modifier = modifier.padding(12.dp),
                onClick = {
                    viewModel.getAdminData(adminID)

                    val reqBody = TicketModel(
                        0,
                        equipmentID,
                        location,
                        remarks,
                        IssuedByModel(adminID, "${adminData.firstName} ${adminData.lastName}"),
                        AssignedToModel(issuedToID, issuedToName)
                    )
                    viewModel.assignTask(reqBody) {
                        if (it.code() == 200) {
                            equipmentID = ""
                            location = ""
                            remarks = ""
                            issuedToID = 0
                            issuedToName = ""
                            Toast.makeText(ctx, "Ticket successfully created", Toast.LENGTH_SHORT)
                                .show()
                            // TODO: NOTIFY TECHNICIAN
                            //      Make Remarks text field bigger
                        } else if (it.code() == 404) {
                            Toast.makeText(ctx, "404 User Not Found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
            {
                Text("Submit")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun AdminBottomNavigation(navController: NavController, adminID: Int) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(
        AdminNavItem.Scan,
        AdminNavItem.Ticket,
        AdminNavItem.TicketList,
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
                    navController.navigate("${item.screenroute}/{adminID}".replace(oldValue = "{adminID}", newValue = adminID.toString())) {
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
