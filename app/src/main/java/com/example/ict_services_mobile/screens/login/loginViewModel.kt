package com.example.ict_services_mobile.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ict_services_mobile.api.RetrofitConfig
import com.example.ict_services_mobile.api.model.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class loginViewModel:ViewModel(){
    fun authenticateUser(map: HashMap<String, String>, resp: (Response<UserDataModel>) -> Unit){
        viewModelScope.launch {
            val client: Call<UserDataModel> = RetrofitConfig.getApiService().authenticateUser(map)
            Log.d("OPERATION", "authenticateUser() is executing!")
            client.enqueue(object: Callback<UserDataModel> {
                override fun onResponse(call: Call<UserDataModel>, response: Response<UserDataModel>) {
                    if(response.code() == 200){
                        resp.invoke(response)
                    }

                    if(response.code() == 404){
                        resp.invoke(response)
                    }
                }

                override fun onFailure(call: Call<UserDataModel>, t: Throwable) {
                    Log.d("FAILURE", "User", t.cause)
                }
            })
        }
    }
}