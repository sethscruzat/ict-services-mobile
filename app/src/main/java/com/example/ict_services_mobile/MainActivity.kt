package com.example.ict_services_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    1) Migrate data to MongoDB Atlas
      1.1) Make sure to update OkHttpClient. We shouldn't be trusting all SSL certificates when it comes to actual production
      1.2) Migrating to Atlas basically means that instead of running the server on localhost, we're running the servers
           on MongoDB's servers instead para boogsh cloud-based
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
            IctservicesmobileTheme {
                NavigationGraph(navController = rememberNavController())
            }
        }
    }
}

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController, startDestination: String = NavRoutes.Login.screenroute){
    val loginViewModel = LoginViewModel()
    //Technician
    val viewModelProfile: ProfileViewModel = viewModel()
    val viewModelTicketList: TicketListViewModel = viewModel()
    val viewModelTechInfo: TicketInfoViewModel = viewModel()
    //Admin
    val viewModelTicketForm: TicketFormViewModel = viewModel()
    val viewModelCompletedTickets: CompletedTicketsViewModel = viewModel()
    val viewModelRateTickets: RateTicketsViewModel = viewModel()

    NavHost(modifier = modifier,navController = navController, startDestination = startDestination) {
        composable(NavRoutes.Login.screenroute) {
            LoginScreen(modifier, navController, loginViewModel)
        }
        // TECHNICIAN ROUTES
        composable("${NavRoutes.TechnicianProfile.screenroute}/{techID}") { navBackStackEntry ->
            val techID = navBackStackEntry.arguments?.getString("techID")
            if (techID != null) {
                LaunchedEffect(Unit) {
                    viewModelProfile.getTechnicianData(techID.toInt())
                }
                val userInfo by viewModelProfile.userInfo.collectAsState()
                ProfileScreen(navController = navController, userInfo = userInfo, techID = techID.toInt())
            }
        }
        composable("${NavRoutes.TechnicianTickets.screenroute}/{techID}") { navBackStackEntry ->
            val techID = navBackStackEntry.arguments?.getString("techID")
            if (techID != null) {
                LaunchedEffect(Unit) {
                    viewModelTicketList.getTicketList(techID.toInt())
                }
                val taskIDList by viewModelTicketList.taskIDList.collectAsState()
                TicketListScreen(navController = navController, taskIDList = taskIDList,  techID = techID.toInt())
            }
        }
        composable("${NavRoutes.TechnicianTicketInfo.screenroute}/{ticketID}") { navBackStackEntry ->
            val ticketID = navBackStackEntry.arguments?.getString("ticketID")
            if (ticketID != null) {
                LaunchedEffect(Unit) {
                    viewModelTechInfo.getTicketInfo(ticketID.toInt())
                }
                val ticketInfo by viewModelTechInfo.ticketInfo.collectAsState()
                TicketInfoScreen(navController = navController, ticketInfo = ticketInfo, viewModel = viewModelTechInfo)
            }
        }

        // ADMIN ROUTES
        composable("${NavRoutes.AdminTicketsForm.screenroute}/{adminID}") { navBackStackEntry ->
            val adminID = navBackStackEntry.arguments?.getString("adminID")
            if (adminID != null) {
                LaunchedEffect(Unit) {
                    viewModelTicketForm.getAdminData(adminID.toInt())
                    viewModelTicketForm.getTechnicianList()
                }
                val techList by viewModelTicketForm.techList.collectAsState()
                TicketFormScreen(navController = navController, viewModel = viewModelTicketForm, adminID = adminID.toInt(), techList = techList)
            }
        }
        composable("${NavRoutes.AdminTicketsList.screenroute}/{adminID}") { navBackStackEntry ->
            val adminID = navBackStackEntry.arguments?.getString("adminID")
            if (adminID != null) {
                LaunchedEffect(Unit) {
                    viewModelCompletedTickets.getCompletedAssignedTasks(adminID.toInt())
                }
                val ticketList by viewModelCompletedTickets.ticketList.collectAsState()
                CompletedTicketsScreen(navController = navController, adminID = adminID.toInt(), ticketList = ticketList)
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
