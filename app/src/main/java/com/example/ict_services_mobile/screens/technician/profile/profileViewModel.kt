package com.example.ict_services_mobile.screens.technician.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ict_services_mobile.api.RetrofitConfig
import com.example.ict_services_mobile.api.model.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class profileViewModel: ViewModel() {
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _tasks = MutableStateFlow("")
    val tasks = _tasks.asStateFlow()

    private val _remarks = MutableStateFlow("")
    val remarks = _remarks.asStateFlow()

    fun getTechnicianData(email: String){
        val client: Call<UserDataModel> = RetrofitConfig.getApiService().getTechnicianData(email)
        client.enqueue(object: Callback<UserDataModel> {
            override fun onResponse(call: Call<UserDataModel>, response: Response<UserDataModel>) {
                if(response.code() == 200){
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val fullName = "${responseBody.firstName} ${responseBody.lastName}"
                        _name.value = fullName
                    }
                }
            }

            override fun onFailure(call: Call<UserDataModel>, t: Throwable) {
                Log.d("FAILURE", "Error retrieving", t.cause)
            }
        })
    }
}