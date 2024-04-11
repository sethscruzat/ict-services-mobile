package com.example.ict_services_mobile.screens.admin.rateTechnician

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ict_services_mobile.api.model.IssuedByModel
import com.example.ict_services_mobile.api.model.RemarkModel
import com.example.ict_services_mobile.api.model.TicketModel

@Composable
fun RateTicketScreen(modifier: Modifier = Modifier, navController: NavHostController, ticketInfo: TicketModel, viewModel: RateTicketsViewModel){
    val ctx = LocalContext.current
    val equipmentID = ticketInfo.equipmentID
    val location = ticketInfo.location
    val remarks = ticketInfo.remarks
    val assignedTo = ticketInfo.assignedTo.techName

    var rating by remember { mutableDoubleStateOf(1.0) }
    var comment by remember { mutableStateOf("") }
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
        Spacer(modifier = Modifier.weight(0.5f))
        Column(modifier = modifier.weight(3f)){
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
            Text(text = assignedTo, modifier = modifier
                .padding(
                    vertical = 6.dp,
                    horizontal = 10.dp
                ),
                fontSize = 17.sp)
            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        start = 12.dp,
                        top = 24.dp,
                        end = 12.dp,
                        bottom = 12.dp
                    ),
                thickness = 3.dp
            )

            StarRatingBar(
                maxStars = 5,
                rating = rating,
                onRatingChanged = {
                    rating = it
                }
            )
            TextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text(text = "Comment") },
                maxLines = 5,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 24.dp, end = 16.dp, bottom = 6.dp)
            )
            Button(
                modifier = modifier
                    .padding(12.dp)
                    .align(Alignment.End),
                onClick = {
                    val reqBody = RemarkModel(
                        rating,
                        ticketInfo.ticketID,
                        comment,
                        IssuedByModel(ticketInfo.issuedBy.adminID,ticketInfo.issuedBy.adminName)
                    )

                    when (val validationResult = viewModel.validateRateForm(reqBody = reqBody)){
                        is RateTicketsViewModel.FormValidationResult.Valid -> viewModel.rateTechnicianPerformance(reqBody=reqBody, techID = ticketInfo.assignedTo.techID){
                            if (it.code() == 200) {
                                rating = 0.0
                                comment = ""
                                Toast.makeText(ctx, "Rated successfully", Toast.LENGTH_SHORT).show()
                            }
                        }
                        is RateTicketsViewModel.FormValidationResult.Invalid ->{
                            Toast.makeText(ctx, validationResult.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                    /* TODO: 1) IMPLEMENT NOTIFY*/
                }
            )
            {
                Text("Rate Performance")
            }
        }
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Double,
    onRatingChanged: (Double) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup().background(color = Color.Black).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x20FFFFFF)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toDouble())
                        }
                    )
                    .width(starSize).height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}