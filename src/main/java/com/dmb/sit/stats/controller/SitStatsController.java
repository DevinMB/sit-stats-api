package com.dmb.sit.stats.controller;

import com.dmb.sit.stats.model.SensorData;
import com.dmb.sit.stats.service.SitStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/sit-stats")
public class SitStatsController {

    private final SitStatService sitStatService;

    @Autowired
    public SitStatsController(SitStatService sitStatService) {
        this.sitStatService = sitStatService;
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<?> getDeviceStats(@PathVariable String deviceId,
                                            @RequestParam(required = false) Long startTimestamp,
                                            @RequestParam(required = false) Long endTimestamp,
                                            @RequestParam(required = false) Long minDuration,
                                            @RequestParam(required = false) Long maxDuration,
                                            @RequestParam(required = false) Long minAvgValue,
                                            @RequestParam(required = false) Long maxAvgValue) {
        try {
            return ResponseEntity.ok(sitStatService.getDeviceStats(deviceId, startTimestamp, endTimestamp, minDuration, maxDuration, minAvgValue, maxAvgValue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

//    @GetMapping("/{deviceId}/status")
//    public ResponseEntity<?> getDeviceStatus(@PathVariable String deviceId) {
//        try {
//            return ResponseEntity.ok(sitStatService.getDeviceStatus(deviceId));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

    @GetMapping("/{deviceId}/sits")
    public ResponseEntity<?> getDeviceSits(@PathVariable String deviceId,
                                           @RequestParam(required = false) Long startTimestamp,
                                           @RequestParam(required = false) Long endTimestamp,
                                           @RequestParam(required = false) Long minDuration,
                                           @RequestParam(required = false) Long maxDuration,
                                           @RequestParam(required = false) Long minAvgValue,
                                           @RequestParam(required = false) Long maxAvgValue) {

        try {
            return ResponseEntity.ok(sitStatService.getDeviceSits(deviceId, startTimestamp, endTimestamp, minDuration, maxDuration, minAvgValue, maxAvgValue));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

//    @GetMapping("/devices")
//    public ResponseEntity<?> getDevices(){
//        try {
//            return ResponseEntity.ok(sitStatService.getDevices());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }

}
