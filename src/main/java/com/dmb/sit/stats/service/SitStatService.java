package com.dmb.sit.stats.service;

import com.dmb.sit.stats.model.SensorData;
import com.dmb.sit.stats.model.Sit;
import com.dmb.sit.stats.model.dto.SummaryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SitStatService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private HashMap<String, List<Sit>> sitDataStore = new HashMap<>();
    private HashMap<String, SensorData> lastSensorRead = new HashMap<>();

    public void aggregateSitRecords(String message) {
        try {
            Sit sit = objectMapper.readValue(message, Sit.class);
            addSit(sit.getDeviceId(), sit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void liveSitSensorRecords(String message) {
        try {
            SensorData sensorData = objectMapper.readValue(message, SensorData.class);
            addStatus(sensorData.getDeviceId(), sensorData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSit(String key, Sit sit) {
        if (!sitDataStore.containsKey(key)) {
            sitDataStore.put(key, new ArrayList<>());
        }
        sitDataStore.get(key).add(sit);
    }

    public void addStatus(String key, SensorData sensorData) {
        if (!lastSensorRead.containsKey(key)) {
            lastSensorRead.put(key, sensorData);
        }
    }

    public List<Sit> getDeviceSits(String deviceId) {
        if (!sitDataStore.containsKey(deviceId)) {
            throw new IllegalArgumentException("Key not found: " + deviceId);
        }
        return sitDataStore.get(deviceId);
    }

    public SensorData getDeviceStatus(String deviceId) {
        if (!lastSensorRead.containsKey(deviceId)) {
            throw new IllegalArgumentException("Key not found: " + deviceId);
        }
        return lastSensorRead.get(deviceId);
    }

    public SummaryDto getDeviceStats(String deviceId) {
        return new SummaryDto(this.getDeviceSits(deviceId), this.getDeviceStatus(deviceId));
    }


}
