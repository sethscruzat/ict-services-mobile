package com.example.ict_services_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ict_services_mobile.ui.theme.IctservicesmobileTheme
import com.example.ict_services_mobile.screens.login.LoginScreen
import com.example.ict_services_mobile.screens.login.loginViewModel
import com.example.ict_services_mobile.navigation.navRoutes
import com.example.ict_services_mobile.screens.technician.profile.ProfileScreen
import com.example.ict_services_mobile.screens.technician.profile.profileViewModel
import com.example.ict_services_mobile.screens.technician.tasks.TechTaskScreen
import com.example.ict_services_mobile.screens.technician.tasks.techTaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IctservicesmobileTheme {
                val loginViewModel = loginViewModel()
                val profileViewModel = profileViewModel()
                val techTaskViewModel = techTaskViewModel()

                val modifier: Modifier = Modifier
                val iViewModelStore = ViewModelStore()
                val navController = rememberNavController().apply {setViewModelStore(iViewModelStore)  }
                NavHost(navController = navController, startDestination = navRoutes.Login.screenroute) {
                    composable(navRoutes.Login.screenroute) {
                        LoginScreen(modifier, navController, loginViewModel)
                    }
                    composable("${navRoutes.TechnicianProfile.screenroute}/{email}") { navBackStackEntry ->
                        val email = navBackStackEntry.arguments?.getString("email")
                        if (email != null) {
                            ProfileScreen(modifier, navController, profileViewModel, email)
                        }
                    }
                    composable("${navRoutes.TechnicianTasks.screenroute}/{email}") { navBackStackEntry ->
                        val email = navBackStackEntry.arguments?.getString("email")
                        if (email != null) {
                            TechTaskScreen(modifier, navController, techTaskViewModel, email)
                        }
                    }
                }
            }
        }
    }
}
