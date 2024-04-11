package com.example.ict_services_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ict_services_mobile.navigation.NavRoutes
import com.example.ict_services_mobile.screens.admin.completedTickets.CompletedTicketsScreen
import com.example.ict_services_mobile.screens.admin.completedTickets.CompletedTicketsViewModel
import com.example.ict_services_mobile.screens.admin.rateTechnician.RateTicketScreen
import com.example.ict_services_mobile.screens.admin.rateTechnician.RateTicketsViewModel
import com.example.ict_services_mobile.screens.admin.ticketForm.TicketFormScreen
import com.example.ict_services_mobile.screens.admin.ticketForm.TicketFormViewModel
import com.example.ict_services_mobile.screens.login.LoginScreen
import com.example.ict_services_mobile.screens.login.LoginViewModel
import com.example.ict_services_mobile.screens.technician.profile.ProfileScreen
import com.example.ict_services_mobile.screens.technician.profile.ProfileViewModel
import com.example.ict_services_mobile.screens.technician.ticketInfo.TicketInfoScreen
import com.example.ict_services_mobile.screens.technician.ticketInfo.TicketInfoViewModel
import com.example.ict_services_mobile.screens.technician.ticketList.TicketListScreen
import com.example.ict_services_mobile.screens.technician.ticketList.TicketListViewModel
import com.example.ict_services_mobile.ui.theme.IctservicesmobileTheme


/* TODO:
    1) Migrate to using HTTPS
      1.1) Make sure to update OkHttpClient. We shouldn't be trusting all SSL certificates when it comes to actual production
    2) Implement saving user state when killing app
      2.1) This includes identifying whether or not the user is currently logged in
      2.2) Make sure to check if state is saved when orientation changes
    3) Clean up error messages on backend and implement proper error handling for screens that have forms
      3.1) ticketFormScreen
      3.2) rateTechnicianScreen
      3.3) This mostly entails handling cases where user enters the wrong data type
    4) DESIGN
    5) Implement Notification, check list to see which screens need it
    6) Scanning qr codes brings up equipment info
*/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authViewModel = AuthViewModel(application)
            IctservicesmobileTheme {
                val navController = rememberNavController()
                val isLoggedIn = authViewModel.isLoggedIn()
                val userID = authViewModel.getUserID()
                val userRole = authViewModel.getUserRole()
                NavigationGraph(navController = navController, authViewModel = authViewModel)
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
                }
            }
        }
    }
}

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController, startDestination: String = NavRoutes.Login.screenroute, authViewModel: AuthViewModel){
    val loginViewModel = LoginViewModel()
    //Technician
    val viewModelProfile: ProfileViewModel = viewModel()
    val dataLoadedTechProfile = remember { mutableStateOf(false) }
    val viewModelTicketList: TicketListViewModel = viewModel()
    val dataLoadedTechList = remember { mutableStateOf(false) }
    val viewModelTechInfo: TicketInfoViewModel = viewModel()
    //Admin
    val viewModelTicketForm: TicketFormViewModel = viewModel()
    val dataLoadedAdminTicket = remember { mutableStateOf(false) }
    val viewModelCompletedTickets: CompletedTicketsViewModel = viewModel()
    val dataLoadedCompTicket = remember { mutableStateOf(false) }
    val viewModelRateTickets: RateTicketsViewModel = viewModel()

    NavHost(modifier = modifier,navController = navController, startDestination = startDestination) {
        composable(NavRoutes.Login.screenroute) {
            LoginScreen(modifier, navController, loginViewModel, authViewModel)
        }
        // TECHNICIAN ROUTES
        composable("${NavRoutes.TechnicianProfile.screenroute}/{techID}") { navBackStackEntry ->
            val techID = navBackStackEntry.arguments?.getString("techID")
            if (techID != null) {
                LaunchedEffect(Unit) {
                    if(!dataLoadedTechProfile.value){
                        viewModelProfile.getTechnicianData(techID.toInt())
                        viewModelProfile.getCompletedTickets(techID.toInt())
                        dataLoadedTechProfile.value = true
                    }
                }
                val userInfo by viewModelProfile.userInfo.collectAsState()
                val completedTicketList by viewModelProfile.recentlyCompletedList.collectAsState()
                ProfileScreen(navController = navController, userInfo = userInfo,ticketList = completedTicketList ,techID = techID.toInt(), authViewModel = authViewModel)
            }
        }
        composable("${NavRoutes.TechnicianTickets.screenroute}/{techID}") { navBackStackEntry ->
            val techID = navBackStackEntry.arguments?.getString("techID")
            if (techID != null) {
                LaunchedEffect(Unit) {
                    if(!dataLoadedTechList.value){
                        viewModelTicketList.getTicketList(techID.toInt())
                        dataLoadedTechList.value = true
                    }
                }
                val taskIDList by viewModelTicketList.taskIDList.collectAsState()
                TicketListScreen(navController = navController, taskIDList = taskIDList,  techID = techID.toInt(), authViewModel = authViewModel)
            }
        }
        composable("${NavRoutes.TechnicianTicketInfo.screenroute}/{ticketID}") { navBackStackEntry ->
            val ticketID = navBackStackEntry.arguments?.getString("ticketID")
            if (ticketID != null) {
                LaunchedEffect(Unit) {
                    viewModelTechInfo.getTicketInfo(ticketID.toInt())
                }
                val ticketInfo by viewModelTechInfo.ticketInfo.collectAsState()
                TicketInfoScreen(navController = navController, ticketInfo = ticketInfo, viewModel = viewModelTechInfo, viewModel2 = viewModelTicketList)
            }
        }

        // ADMIN ROUTES
        composable("${NavRoutes.AdminTicketsForm.screenroute}/{adminID}") { navBackStackEntry ->
            val adminID = navBackStackEntry.arguments?.getString("adminID")
            if (adminID != null) {
                LaunchedEffect(Unit) {
                    if(!dataLoadedAdminTicket.value){
                        viewModelTicketForm.getTechnicianList()
                        dataLoadedAdminTicket.value = true
                    }
                }
                val techList by viewModelTicketForm.techList.collectAsState()
                TicketFormScreen(navController = navController, viewModel = viewModelTicketForm, adminID = adminID.toInt(), techList = techList,authViewModel = authViewModel)
            }
        }
        composable("${NavRoutes.AdminTicketsList.screenroute}/{adminID}") { navBackStackEntry ->
            val adminID = navBackStackEntry.arguments?.getString("adminID")
            if (adminID != null) {
                LaunchedEffect(Unit) {
                    if(!dataLoadedCompTicket.value){
                        viewModelCompletedTickets.getCompletedAssignedTasks(adminID.toInt())
                        dataLoadedCompTicket.value = true
                    }
                }
                val ticketList by viewModelCompletedTickets.ticketList.collectAsState()
                CompletedTicketsScreen(navController = navController, adminID = adminID.toInt(), ticketList = ticketList,authViewModel = authViewModel)
            }
        }
        composable("${NavRoutes.AdminRate.screenroute}/{ticketID}") { navBackStackEntry ->
            val ticketID = navBackStackEntry.arguments?.getString("ticketID")
            if (ticketID != null) {
                LaunchedEffect(Unit) {
                    viewModelRateTickets.getCompletedTicketInfo(ticketID.toInt())
                }
                val ticketInfo by viewModelRateTickets.ticketInfo.collectAsState()
                RateTicketScreen(navController = navController, viewModel = viewModelRateTickets, ticketInfo = ticketInfo)
            }
        }
    }
}
