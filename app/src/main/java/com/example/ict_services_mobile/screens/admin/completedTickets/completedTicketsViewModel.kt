package com.example.ict_services_mobile.screens.admin.completedTickets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ict_services_mobile.api.RetrofitConfig
import com.example.ict_services_mobile.api.model.TicketModel
import com.example.ict_services_mobile.api.model.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompletedTicketsViewModel: ViewModel(){
    private val _ticketList = MutableStateFlow(emptyList<TicketModel>())
    val ticketList = _ticketList.asStateFlow()
    fun getAllCompletedTasks(){
        viewModelScope.launch {
            try {
                val client: Call<List<TicketModel>> = RetrofitConfig.getTicketApiService().getAllCompletedTasks()
                client.enqueue(object: Callback<List<TicketModel>> {
                    override fun onResponse(call: Call<List<TicketModel>>, response: Response<List<TicketModel>>) {
                        if(response.code() == 200){
                            val responseBody = response.body()

                            if (responseBody != null) {
                                _ticketList.value = responseBody
                            }
                        }
                    }
                    override fun onFailure(call: Call<List<TicketModel>>, t: Throwable) {
                        Log.d("FAILURE", "Error retrieving", t.cause)
                    }
                })
            } catch (e: Exception) {
                Log.e("profileViewModel", "Exception occurred", e)
            }
        }
    }

}