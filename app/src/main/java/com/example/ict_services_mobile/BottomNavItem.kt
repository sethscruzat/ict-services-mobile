package com.example.ict_services_mobile

sealed class BottomNavItem(var title:String, var icon:Int, var screenroute:String){

    object Calculator : BottomNavItem("Profile", R.drawable.baseline_calculate_24,"techProfile")
    object Generator: BottomNavItem("Task List",R.drawable.baseline_123_24,"techTasks")
}