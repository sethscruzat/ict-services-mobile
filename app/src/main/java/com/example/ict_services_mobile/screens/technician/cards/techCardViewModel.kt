package com.example.ict_services_mobile.screens.technician.cards

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ict_services_mobile.api.RetrofitConfig
import com.example.ict_services_mobile.api.model.TechTaskModel
import com.example.ict_services_mobile.api.model.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TechCardViewModel : ViewModel(){

    private val _taskInfo = MutableStateFlow(TechTaskModel( "","","","", ""))
    val taskInfo = _taskInfo.asStateFlow()

    fun getTechTaskList(email: String, index: Int){
        viewModelScope.launch {
            try{
                val client: Call<TechTaskModel> =
                    RetrofitConfig.getApiService().getTechTaskList(email, index)
                client.enqueue(object : Callback<TechTaskModel> {
                    override fun onResponse(
                        call: Call<TechTaskModel>,
                        response: Response<TechTaskModel>
                    ) {
                        if (response.code() == 200) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                _taskInfo.value = responseBody
                            }
                        }
                    }

                    override fun onFailure(call: Call<TechTaskModel>, t: Throwable) {
                        Log.d("FAILURE", "Tech Card Error", t.cause)
                    }
                })
            }catch (e: Exception) {
                Log.e("techCardViewModel", "Exception occurred", e)
            }
        }
    }
}