package com.example.ict_services_mobile.screens.technician.ticketInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ict_services_mobile.api.RetrofitConfig
import com.example.ict_services_mobile.api.model.AssignedToModel
import com.example.ict_services_mobile.api.model.IssuedByModel
import com.example.ict_services_mobile.api.model.TicketModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketInfoViewModel : ViewModel(){

    private val _taskInfo = MutableStateFlow(TicketModel( 0,"","","", IssuedByModel(0,""), AssignedToModel(0,"")))
    val taskInfo = _taskInfo.asStateFlow()

    fun getTechTaskList(techID: Int, taskID: Int){
        viewModelScope.launch {
            try{
                val client: Call<TicketModel> =
                    RetrofitConfig.getUserApiService().getTechTaskList(techID, taskID)
                client.enqueue(object : Callback<TicketModel> {
                    override fun onResponse(
                        call: Call<TicketModel>,
                        response: Response<TicketModel>
                    ) {
                        if (response.code() == 200) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                _taskInfo.value = responseBody
                            }
                        }
                    }

                    override fun onFailure(call: Call<TicketModel>, t: Throwable) {
                        Log.d("FAILURE", "Tech Card Error", t.cause)
                    }
                })
            }catch (e: Exception) {
                Log.e("techCardViewModel", "Exception occurred", e)
            }
        }
    }
}