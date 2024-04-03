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
    private val _objList = MutableStateFlow(emptyList<TechTaskModel>())
    val objList = _objList.asStateFlow()

    fun getTechTaskList(email: String){
        val client: Call<UserDataModel> = RetrofitConfig.getApiService().getTechTaskList(email)
        client.enqueue(object: Callback<UserDataModel> {
            override fun onResponse(call: Call<UserDataModel>, response: Response<UserDataModel>) {
                if (response.code() == 200) {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val taskList = responseBody.tasks
                        val tempObjList = mutableListOf<TechTaskModel>()
                        for (i in taskList.indices) {
                            val equipmentID = taskList[i].equipmentID
                            val equipmentName = taskList[i].equipmentName
                            val location = taskList[i].location
                            val remarks = taskList[i].remarks
                            val issuedBy = taskList[i].issuedBy
                            tempObjList.add(
                                i,
                                TechTaskModel(
                                    equipmentID,
                                    equipmentName,
                                    location,
                                    remarks,
                                    issuedBy
                                )
                            )
                        }
                        _objList.value = tempObjList
                    }
                }
            }
            override fun onFailure(call: Call<UserDataModel>, t: Throwable) {
                Log.d("FAILURE", "Wala kang kwenta", t.cause)
            }
        })
    }
}
