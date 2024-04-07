package com.example.ict_services_mobile.screens.technician.profile

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

class ProfileViewModel: ViewModel() {
    private val _userInfo = MutableStateFlow(UserDataModel(0, 0,"","","","", "", emptyList()))
    val userInfo = _userInfo.asStateFlow()

    fun getTechnicianData(techID: Int){
        viewModelScope.launch {
            try {
                val client: Call<UserDataModel> = RetrofitConfig.getUserApiService().getTechnicianData(techID)
                client.enqueue(object: Callback<UserDataModel> {
                    override fun onResponse(call: Call<UserDataModel>, response: Response<UserDataModel>) {
                        if(response.code() == 200){
                            val responseBody = response.body()

                            if (responseBody != null) {
                                _userInfo.value = responseBody
                            }
                        }
                    }
                    override fun onFailure(call: Call<UserDataModel>, t: Throwable) {
                        Log.d("FAILURE", "Error retrieving", t.cause)
                    }
                })
            } catch (e: Exception) {
                Log.e("profileViewModel", "Exception occurred", e)
            }
        }
    }

    private val _recentlyCompletedList = MutableStateFlow(emptyList<TicketModel>())
    val recentlyCompletedList = _recentlyCompletedList.asStateFlow()
    fun getCompletedTickets(techID: Int){
        viewModelScope.launch {
            try {
                val client: Call<List<TicketModel>> = RetrofitConfig.getTicketApiService().getCompletedTickets(techID)
                client.enqueue(object: Callback<List<TicketModel>> {
                    override fun onResponse(call: Call<List<TicketModel>>, response: Response<List<TicketModel>>) {
                        if(response.code() == 200){
                            val responseBody = response.body()
                            if (responseBody != null) {
                                _recentlyCompletedList.value = responseBody
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