package com.example.ict_services_mobile.screens.admin.rateTechnician

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ict_services_mobile.api.RetrofitConfig
import com.example.ict_services_mobile.api.model.AssignedToModel
import com.example.ict_services_mobile.api.model.IssuedByModel
import com.example.ict_services_mobile.api.model.RemarkModel
import com.example.ict_services_mobile.api.model.TicketModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RateTicketsViewModel: ViewModel(){
    private val _ticketInfo = MutableStateFlow(TicketModel( 0,"","","", IssuedByModel(0,""), AssignedToModel(0,"")))
    val ticketInfo = _ticketInfo.asStateFlow()

    fun getCompletedTicketInfo(ticketID: Int){
        viewModelScope.launch {
            try{
                val client: Call<TicketModel> =
                    RetrofitConfig.getTicketApiService().getCompletedTicketInfo(ticketID)
                client.enqueue(object : Callback<TicketModel> {
                    override fun onResponse(
                        call: Call<TicketModel>,
                        response: Response<TicketModel>
                    ) {
                        if (response.code() == 200) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                _ticketInfo.value = responseBody
                            }
                        }
                    }

                    override fun onFailure(call: Call<TicketModel>, t: Throwable) {
                        Log.d("FAILURE", "Admin Card Error", t.cause)
                    }
                })
            }catch (e: Exception) {
                Log.e("techCardViewModel", "Exception occurred", e)
            }
        }
    }

    fun rateTechnicianPerformance(techID: Int,reqBody: RemarkModel, resp: (Response<RemarkModel>)-> Unit){
        viewModelScope.launch {
            val client: Call<RemarkModel> = RetrofitConfig.getUserApiService().rateTechnicianPerformance(techID,reqBody)
            Log.d("OPERATION", "authenticateUser() is executing!")
            client.enqueue(object: Callback<RemarkModel> {
                override fun onResponse(call: Call<RemarkModel>, response: Response<RemarkModel>) {
                    if(response.code() == 200){
                        resp.invoke(response)
                    }
                }

                override fun onFailure(call: Call<RemarkModel>, t: Throwable) {
                    Log.d("FAILURE", "User", t.cause)
                }
            })
        }
    }
}