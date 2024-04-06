package com.example.ict_services_mobile.screens.technician.ticketList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ict_services_mobile.api.RetrofitConfig
import com.example.ict_services_mobile.api.model.TicketModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketListViewModel: ViewModel() {
    private val _taskIDList = MutableStateFlow(emptyList<Pair<Int,String>>())
    val taskIDList = _taskIDList.asStateFlow()

    fun getTechTaskItems(techID: Int){
        viewModelScope.launch {
            try{
                val client: Call<List<TicketModel>> = RetrofitConfig.getUserApiService().getTechTaskItems(techID)
                client.enqueue(object: Callback<List<TicketModel>> {
                    override fun onResponse(call: Call<List<TicketModel>>, response: Response<List<TicketModel>>) {
                        if (response.code() == 200) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                val tempObjList = mutableListOf<Pair<Int, String>>()
                                for (i in responseBody.indices) {
                                    val ticketID = responseBody[i].ticketID
                                    val equipmentID = responseBody[i].equipmentID
                                    tempObjList.add(i, Pair(ticketID, equipmentID))
                                }
                                _taskIDList.value = tempObjList
                            }
                        }
                    }
                    override fun onFailure(call: Call<List<TicketModel>>, t: Throwable) {
                        Log.d("FAILURE", "Tasks not found", t.cause)
                    }
                })
            }catch (e: Exception) {
                Log.e("techTaskViewModel", "Exception occurred", e)
            }
        }
    }
}
