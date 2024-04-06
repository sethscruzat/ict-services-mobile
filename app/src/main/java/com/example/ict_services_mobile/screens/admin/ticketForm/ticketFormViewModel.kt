package com.example.ict_services_mobile.screens.admin.ticketForm

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

class TicketFormViewModel:ViewModel(){
    fun assignTask(reqBody: TicketModel, resp: (Response<TicketModel>) -> Unit){
        viewModelScope.launch {
            val client: Call<TicketModel> = RetrofitConfig.getTicketApiService().assignTask(reqBody)
            Log.d("OPERATION", "authenticateUser() is executing!")
            client.enqueue(object: Callback<TicketModel> {
                override fun onResponse(call: Call<TicketModel>, response: Response<TicketModel>) {
                    if(response.code() == 200){
                        resp.invoke(response)
                    }

                    if(response.code() == 404){
                        resp.invoke(response)
                    }
                }

                override fun onFailure(call: Call<TicketModel>, t: Throwable) {
                    Log.d("FAILURE", "User", t.cause)
                }
            })
        }
    }

    private val _adminInfo = MutableStateFlow(UserDataModel(0, 0,"","","","", "", emptyList()))
    val adminInfo = _adminInfo.asStateFlow()
    fun getAdminData(adminID: Int){
        viewModelScope.launch {
            try {
                val client: Call<UserDataModel> = RetrofitConfig.getUserApiService().getAdminData(adminID)
                client.enqueue(object: Callback<UserDataModel> {
                    override fun onResponse(call: Call<UserDataModel>, response: Response<UserDataModel>) {
                        if(response.code() == 200){
                            val responseBody = response.body()

                            if (responseBody != null) {
                                _adminInfo.value = responseBody
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

    private val _techList = MutableStateFlow(emptyList<UserDataModel>())
    val techList = _techList.asStateFlow()
    fun getTechnicianList(){
        viewModelScope.launch {
            try {
                val client: Call<List<UserDataModel>> = RetrofitConfig.getUserApiService().getTechnicianList()
                client.enqueue(object: Callback<List<UserDataModel>> {
                    override fun onResponse(call: Call<List<UserDataModel>>, response: Response<List<UserDataModel>>) {
                        if(response.code() == 200){
                            val responseBody = response.body()

                            if (responseBody != null) {
                                _techList.value = responseBody
                            }
                        }
                    }
                    override fun onFailure(call: Call<List<UserDataModel>>, t: Throwable) {
                        Log.d("FAILURE", "Error retrieving", t.cause)
                    }
                })
            } catch (e: Exception) {
                Log.e("profileViewModel", "Exception occurred", e)
            }
        }
    }
}