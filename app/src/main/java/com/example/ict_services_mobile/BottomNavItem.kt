package com.example.ict_services_mobile

sealed class TechnicianNavItem(var title:String, var icon:Int, var screenroute:String){
    data object Profile : TechnicianNavItem("Profile", R.drawable.baseline_account_box_24,"techProfile")
    data object TechTicketList: TechnicianNavItem("Ticket List",R.drawable.baseline_align_horizontal_left_24,"techTicketList")
}

sealed class AdminNavItem(var title:String, var icon:Int, var screenroute:String){
    data object Scan : AdminNavItem("Scan", R.drawable.baseline_airplay_24,"")
    data object Ticket : AdminNavItem("Ticket Form", R.drawable.baseline_add_box_24,"adminTicketForm")
    data object TicketList: AdminNavItem("Ticket List",R.drawable.baseline_align_horizontal_left_24,"adminTicketList")
}