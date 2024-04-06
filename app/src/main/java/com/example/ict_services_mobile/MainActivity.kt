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
import com.example.ict_services_mobile.navigation.navRoutes
import com.example.ict_services_mobile.screens.admin.completedTickets.CompletedTicketsScreen
import com.example.ict_services_mobile.screens.admin.completedTickets.CompletedTicketsViewModel
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
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController, startDestination: String = navRoutes.Login.screenroute){
    val loginViewModel = LoginViewModel()
    //Technician
    val viewModelProfile: ProfileViewModel = viewModel()
    val viewModelTicketList: TicketListViewModel = viewModel()
    val viewModelTechInfo: TicketInfoViewModel = viewModel()
    //Admin
    val viewModelTicketForm: TicketFormViewModel = viewModel()
    val viewModelCompletedTickets: CompletedTicketsViewModel = viewModel()

    NavHost(modifier = modifier,navController = navController, startDestination = startDestination) {
        composable(navRoutes.Login.screenroute) {
            LoginScreen(modifier, navController, loginViewModel)
        }
        // TECHNICIAN ROUTES
        composable("${navRoutes.TechnicianProfile.screenroute}/{techID}") { navBackStackEntry ->
            val techID = navBackStackEntry.arguments?.getString("techID")
            if (techID != null) {
                LaunchedEffect(Unit) {
                    viewModelProfile.getTechnicianData(techID.toInt())
                }
                val userInfo by viewModelProfile.userInfo.collectAsState()
                ProfileScreen(navController = navController, userInfo = userInfo, techID = techID.toInt())
            }
        }
        composable("${navRoutes.TechnicianTickets.screenroute}/{techID}") { navBackStackEntry ->
            val techID = navBackStackEntry.arguments?.getString("techID")
            if (techID != null) {
                LaunchedEffect(Unit) {
                    viewModelTicketList.getTechTaskItems(techID.toInt())
                }
                val taskIDList by viewModelTicketList.taskIDList.collectAsState()
                TicketListScreen(navController = navController, taskIDList = taskIDList,  techID = techID.toInt())
            }
        }
        composable("${navRoutes.TechnicianTicketInfo.screenroute}/{techID}/{ticketID}") { navBackStackEntry ->
            val techID = navBackStackEntry.arguments?.getString("techID")
            val ticketID = navBackStackEntry.arguments?.getString("ticketID")
            if (techID != null && ticketID != null) {
                LaunchedEffect(Unit) {
                    viewModelTechInfo.getTechTaskList(techID.toInt(), ticketID.toInt())
                }
                val ticketInfo by viewModelTechInfo.taskInfo.collectAsState()
                TicketInfoScreen(navController = navController, ticketInfo = ticketInfo)
            }
        }

        // ADMIN ROUTES
        composable("${navRoutes.AdminTicketsForm.screenroute}/{adminID}") { navBackStackEntry ->
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
        composable("${navRoutes.AdminTicketsList.screenroute}/{adminID}") { navBackStackEntry ->
            val adminID = navBackStackEntry.arguments?.getString("adminID")
            if (adminID != null) {
                LaunchedEffect(Unit) {
                    viewModelCompletedTickets.getAllCompletedTasks()
                }
                val ticketList by viewModelCompletedTickets.ticketList.collectAsState()
                CompletedTicketsScreen(navController = navController, viewModel = viewModelCompletedTickets, adminID = adminID.toInt(), ticketList = ticketList)
            }
        }
    }
}
