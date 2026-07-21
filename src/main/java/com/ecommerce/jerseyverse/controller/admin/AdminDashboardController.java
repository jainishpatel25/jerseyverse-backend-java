package com.ecommerce.jerseyverse.controller.admin;

import com.ecommerce.jerseyverse.dto.response.dashboard.DashboardResponse;
import com.ecommerce.jerseyverse.service.admin.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
public class AdminDashboardController {

    private final DashboardService dashboardService;

    public AdminDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboardOverview() {
        return ResponseEntity.ok(dashboardService.getDashboardOverview());
    }
}