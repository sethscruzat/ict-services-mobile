package com.example.ict_services_mobile.screens.technician.cards

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ict_services_mobile.api.model.TechTaskModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TechCardScreen(modifier: Modifier = Modifier, navController: NavHostController, taskInfo: TechTaskModel){
    val equipmentID = taskInfo.equipmentID
    val equipmentName = taskInfo.equipmentName
    val location = taskInfo.location
    val remarks = taskInfo.remarks
    val issuedBy = taskInfo.issuedBy
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight())
    {
        Button(
            modifier = modifier
                .padding(12.dp)
                .align(Alignment.End),
            onClick = {
                navController.navigateUp()
            }
        )
        {
            Text("Back")
        }
        Spacer(modifier = Modifier.weight(0.1f))
        Column(modifier = modifier.weight(2f)){
            Text(text = equipmentID, modifier = modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 10.dp
                ),
                fontSize = 17.sp)
            Text(text = equipmentName, modifier = modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 10.dp
                ),
                fontSize = 17.sp)
            Text(text = location, modifier = modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 10.dp
                ),
                fontSize = 17.sp)
            Text(text = remarks, modifier = modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 10.dp
                ),
                fontSize = 17.sp)
            Text(text = issuedBy, modifier = modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 10.dp
                ),
                fontSize = 17.sp)
        }
    }
}