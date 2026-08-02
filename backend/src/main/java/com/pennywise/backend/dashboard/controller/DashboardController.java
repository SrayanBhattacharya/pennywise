package com.pennywise.backend.dashboard.controller;

import com.pennywise.backend.dashboard.dto.response.DashboardSummaryResponse;
import com.pennywise.backend.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryResponse getDashboardSummary() {
        return dashboardService.getDashboardSummary();
    }
}
