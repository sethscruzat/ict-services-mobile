package com.example.ict_services_mobile.navigation

sealed class navRoutes(var screenroute:String) {
    object Login : navRoutes("login")
    object AdminTicketsForm: navRoutes("adminTicketForm") // Form when issuing tasks
    object AdminTicketsList: navRoutes("adminTicketList") // Issued tasks by admin
    object AdminRate: navRoutes("adminRate") //Page when admin rates after technician completes
    object TechnicianProfile: navRoutes("techProfile")
    object TechnicianTickets: navRoutes("techTicketList") // List of tickets for technician
    object TechnicianTicketInfo: navRoutes("techTicketInfo") // Expanded
}