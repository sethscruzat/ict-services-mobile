package com.example.ict_services_mobile.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ict_services_mobile.AuthViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController, viewModel: LoginViewModel, authViewModel: AuthViewModel) {
    val ctx = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        var passwordVisibility by rememberSaveable { mutableStateOf(false) }
        Spacer(modifier = Modifier.weight(1f))
        TextField(
            value = username,
            onValueChange = {username = it},
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp, bottom = 6.dp))

        TextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 24.dp, end = 12.dp, bottom = 6.dp),
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisibility)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisibility) "Hide password" else "Show password"

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector  = image, description)
                }
            }
        )

        Button(
            modifier = modifier.padding(12.dp),
            onClick = {
                val map: HashMap<String, String> = HashMap()
                map["password"] = password
                map["email"] = username
                viewModel.authenticateUser(map){
                    if(it.code() == 200){
                        authViewModel.setLoggedIn(true)
                        val responseBody = it.body()
                        if(responseBody != null && responseBody.role == "technician"){
                            authViewModel.setUserID(responseBody.techID)
                            authViewModel.setUserRole(responseBody.role)
                            navController.navigate("techProfile/{techID}".replace(oldValue = "{techID}", newValue = responseBody.techID.toString())) {
                                navController.graph.startDestinationRoute?.let { screenroute ->
                                    popUpTo(screenroute) {
                                        saveState = false
                                    }
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }else if(responseBody != null && responseBody.role == "admin"){
                            authViewModel.setUserID(responseBody.adminID)
                            authViewModel.setUserRole(responseBody.role)
                            navController.navigate("adminTicketForm/{adminID}".replace(oldValue = "{adminID}", newValue = responseBody.adminID.toString())) {
                                navController.graph.startDestinationRoute?.let { screenroute ->
                                    popUpTo(screenroute) {
                                        saveState = false
                                    }
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                    }else if(it.code() == 404){
                        Toast.makeText(ctx, "404 User Not Found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        {
            Text("Login")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}