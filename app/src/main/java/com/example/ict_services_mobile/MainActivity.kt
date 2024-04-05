package com.example.ict_services_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ict_services_mobile.navigation.navRoutes
import com.example.ict_services_mobile.screens.login.LoginScreen
import com.example.ict_services_mobile.screens.login.LoginViewModel
import com.example.ict_services_mobile.screens.technician.cards.TechCardScreen
import com.example.ict_services_mobile.screens.technician.cards.TechCardViewModel
import com.example.ict_services_mobile.screens.technician.profile.ProfileScreen
import com.example.ict_services_mobile.screens.technician.profile.ProfileViewModel
import com.example.ict_services_mobile.screens.technician.tasks.TechTaskScreen
import com.example.ict_services_mobile.screens.technician.tasks.TechTaskViewModel
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
    val viewModelProfile: ProfileViewModel = viewModel()
    val viewModelTechTask: TechTaskViewModel = viewModel()
    val viewModelTechCard: TechCardViewModel = viewModel()

    NavHost(modifier = modifier,navController = navController, startDestination = startDestination) {
        composable(navRoutes.Login.screenroute) {
            LoginScreen(modifier, navController, loginViewModel)
        }
        composable("${navRoutes.TechnicianProfile.screenroute}/{email}") { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            if (email != null) {
                LaunchedEffect(Unit) {
                    viewModelProfile.getTechnicianData(email)
                }
                val userInfo by viewModelProfile.userInfo.collectAsState()
                ProfileScreen(navController = navController, userInfo = userInfo, email = email)
            }
        }
        composable("${navRoutes.TechnicianTasks.screenroute}/{email}") { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            if (email != null) {
                LaunchedEffect(Unit) {
                    viewModelTechTask.getTechTaskItems(email)
                }
                val taskIDList by viewModelTechTask.taskIDList.collectAsState()
                TechTaskScreen(navController = navController, taskIDList = taskIDList,  email = email)
            }
        }
        composable("${navRoutes.TechnicianTaskCards.screenroute}/{email}/{index}") { navBackStackEntry ->
            val email = navBackStackEntry.arguments?.getString("email")
            val index = navBackStackEntry.arguments?.getString("index")
            if (email != null && index != null) {
                LaunchedEffect(Unit) {
                    viewModelTechCard.getTechTaskList(email, index.toInt())
                }
                val taskInfo by viewModelTechCard.taskInfo.collectAsState()
                TechCardScreen(navController = navController, taskInfo = taskInfo)
            }
        }
    }
}
