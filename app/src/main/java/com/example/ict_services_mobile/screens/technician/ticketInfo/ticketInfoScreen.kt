package com.example.ict_services_mobile.screens.technician.ticketInfo

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ict_services_mobile.api.model.TicketModel
import com.example.ict_services_mobile.screens.technician.ticketList.TicketListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicketInfoScreen(modifier: Modifier = Modifier, navController: NavHostController, ticketInfo: TicketModel, viewModel: TicketInfoViewModel, viewModel2: TicketListViewModel){
    val ctx = LocalContext.current
    val equipmentID = ticketInfo.equipmentID
    val location = ticketInfo.location
    val remarks = ticketInfo.remarks
    val issuedBy = ticketInfo.issuedBy.adminName
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
        ) { Text("Back") }
        Spacer(modifier = Modifier.weight(0.1f))
        Column(modifier = modifier.weight(2f)){
            Text(text = equipmentID, modifier = modifier
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

            Button(
                modifier = modifier
                    .padding(12.dp)
                    .align(Alignment.End),
                onClick = {
                    viewModel.markTaskAsDone(ticketInfo.ticketID)
                    viewModel2.getTicketList(ticketInfo.assignedTo.techID)
                    Toast.makeText(ctx, "Ticket marked as Done", Toast.LENGTH_SHORT).show()
                    navController.navigateUp()

                    /* TODO: 1) IMPLEMENT NOTIFY*/
                }
            )
            {
                Text("Mark As Done")
            }
        }
    }
}