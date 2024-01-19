package com.dmb.sit.stats.controller;


import com.dmb.sit.stats.model.dto.SummaryDto;
import com.dmb.sit.stats.service.SitStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sit-stats") // Base path for all endpoints in this controller
public class SitStatsController {

    private final SitStatService sitStatService;

    @Autowired
    public SitStatsController(SitStatService sitStatService) {
        this.sitStatService = sitStatService;
    }

    @GetMapping("/{deviceId}") // Maps to /sit-stats/{deviceId}
    public SummaryDto getDeviceStats(@PathVariable String deviceId) {
        return sitStatService.getDeviceStats(deviceId);
    }

}
