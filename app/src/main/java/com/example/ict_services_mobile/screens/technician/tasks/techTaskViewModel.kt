package com.example.ict_services_mobile.screens.technician.tasks

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

class techTaskViewModel: ViewModel() {
    private val _taskIDList = MutableStateFlow(emptyList<String>())
    val taskIDList = _taskIDList.asStateFlow()

    fun getTechTaskItems(email: String){
        val client: Call<UserDataModel> = RetrofitConfig.getApiService().getTechTaskItems(email)
        client.enqueue(object: Callback<UserDataModel> {
            override fun onResponse(call: Call<UserDataModel>, response: Response<UserDataModel>) {
                if (response.code() == 200) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val taskList = responseBody.tasks
                        val tempObjList = mutableListOf<String>()
                        for (i in taskList.indices) {
                            val equipmentID = taskList[i].equipmentID
                            tempObjList.add(i,equipmentID)
                        }
                        _taskIDList.value = tempObjList
                    }
                }
            }
            override fun onFailure(call: Call<UserDataModel>, t: Throwable) {
                Log.d("FAILURE", "Wala kang kwenta", t.cause)
            }
        })
    }
}
