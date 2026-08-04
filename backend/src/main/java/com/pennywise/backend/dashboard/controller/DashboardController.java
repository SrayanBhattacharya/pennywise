package com.pennywise.backend.dashboard.controller;

import com.pennywise.backend.dashboard.dto.response.CategoryBreakdownResponse;
import com.pennywise.backend.dashboard.dto.response.DashboardSummaryResponse;
import com.pennywise.backend.dashboard.dto.response.MonthlySummaryResponse;
import com.pennywise.backend.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummaryResponse getDashboardSummary() {
        return dashboardService.getDashboardSummary();
    }

    @GetMapping("/category-breakdown")
    public List<CategoryBreakdownResponse> getCategoryBreakdown() {
        return dashboardService.getCategoryBreakdown();
    }

    @GetMapping("/monthly-summary")
    public List<MonthlySummaryResponse> getMonthlySummary() {
        return dashboardService.getMonthlySummary();
    }
}
