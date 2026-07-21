package com.ecommerce.jerseyverse.controller.admin;

import com.ecommerce.jerseyverse.service.admin.DashboardService;

public class AdminDashboardController {

    private final DashboardService dashboardService;

    public AdminDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

}
