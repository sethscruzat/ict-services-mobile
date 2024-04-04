package com.example.ict_services_mobile.screens.technician.cards

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ict_services_mobile.api.RetrofitConfig
import com.example.ict_services_mobile.api.model.TechTaskModel
import com.example.ict_services_mobile.api.model.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class techCardViewModel : ViewModel(){
    private val _equipmentID = MutableStateFlow("")
    val equipmentID = _equipmentID.asStateFlow()

    private val _equipmentName = MutableStateFlow("")
    val equipmentName = _equipmentName.asStateFlow()

    private val _location = MutableStateFlow("")
    val location = _location.asStateFlow()

    private val _remarks = MutableStateFlow("")
    val remarks = _remarks.asStateFlow()

    private val _issuedBy = MutableStateFlow("")
    val issuedBy = _issuedBy.asStateFlow()

    fun getTechTaskList(email: String, index: Int){
        val client: Call<TechTaskModel> = RetrofitConfig.getApiService().getTechTaskList(email, index)
        client.enqueue(object: Callback<TechTaskModel> {
            override fun onResponse(call: Call<TechTaskModel>, response: Response<TechTaskModel>) {
                if (response.code() == 200) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        _equipmentID.value = responseBody.equipmentID
                        _equipmentName.value = responseBody.equipmentName
                        _location.value = responseBody.location
                        _remarks.value = responseBody.remarks
                        _issuedBy.value = responseBody.issuedBy
                    }
                }
            }
            override fun onFailure(call: Call<TechTaskModel>, t: Throwable) {
                Log.d("FAILURE", "Tech Card Error", t.cause)
            }
        })
    }
}