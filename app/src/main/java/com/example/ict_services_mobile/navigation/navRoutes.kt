package com.example.ict_services_mobile.navigation

sealed class navRoutes(var screenroute:String) {
    object Login : navRoutes("login")
    object AdminTickets: navRoutes("adminTickets") // Form when issuing tasks
    object AdminTasks: navRoutes("adminTasks") // Issued tasks by admin
    object AdminRate: navRoutes("adminRate") //Page when admin rates after technician completes
    object TechnicianProfile: navRoutes("techProfile")
    object TechnicianTasks: navRoutes("techTasks") // List of tasks
    object TechnicianTaskCards: navRoutes("techCards") // Expanded
}