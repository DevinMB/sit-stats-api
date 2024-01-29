package com.dmb.sit.stats.service;

import com.dmb.sit.stats.controller.DeviceStatusWebSocketController;
import com.dmb.sit.stats.exception.DeviceNotFoundException;
import com.dmb.sit.stats.model.DeviceStatus;
import com.dmb.sit.stats.model.SensorData;
import com.dmb.sit.stats.model.Sit;
import com.dmb.sit.stats.model.dto.SummaryDto;
import com.dmb.sit.stats.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SitStatService {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Utils utils = new Utils();
    private HashMap<String, List<Sit>> sitDataStore = new HashMap<>();
    private HashMap<String, DeviceStatus> deviceStatusStore = new HashMap<>();

    public void aggregateSitRecords(String message) {
        try {
            Sit sit = objectMapper.readValue(message, Sit.class);
            addSit(sit.getDeviceId(), sit);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateDeviceStatus(String message){
        try{
            DeviceStatus deviceStatus = objectMapper.readValue(message, DeviceStatus.class);
            deviceStatusStore.put(deviceStatus.getDeviceId(),deviceStatus);
            eventPublisher.publishEvent(new DeviceStatusUpdatedEvent(this, deviceStatus));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<DeviceStatus> getDevices() {
        List<DeviceStatus> devices = new ArrayList<>();
        devices.addAll(deviceStatusStore.values());
        return devices;
    }

    public void addSit(String key, Sit sit) {
        if (!sitDataStore.containsKey(key)) {
            sitDataStore.put(key, new ArrayList<>());
        }
        sitDataStore.get(key).add(sit);
    }

    public SummaryDto getDeviceStats(String deviceId, Long startTimestamp, Long endTimestamp, Long minDuration, Long maxDuration, Long minAvgValue, Long maxAvgValue) {
        return new SummaryDto(deviceId,
                this.getDeviceSits(deviceId, startTimestamp, endTimestamp, minDuration, maxDuration, minAvgValue, maxAvgValue));
    }

    public List<Sit> getDeviceSits(String deviceId) {
        if (!sitDataStore.containsKey(deviceId)) {
            throw new DeviceNotFoundException("No sits exist for this device id: " + deviceId);
        }
        return sitDataStore.get(deviceId);
    }

    public List<Sit> getDeviceSits(String deviceId, Long startTimestamp, Long endTimestamp, Long minDuration, Long maxDuration, Long minAvgValue, Long maxAvgValue) {
        if (!sitDataStore.containsKey(deviceId)) {
            throw new DeviceNotFoundException("No sits exist for this device id: " + deviceId);
        }

        List<Sit> sits = sitDataStore.get(deviceId);
        Stream<Sit> sitStream = sits.stream();

        if (startTimestamp != null) {
            sitStream = sitStream.filter(sit -> sit.getStartTimestamp() >= startTimestamp);
        }
        if (endTimestamp != null) {
            sitStream = sitStream.filter(sit -> sit.getEndTimestamp() <= endTimestamp);
        }
        if (minDuration != null) {
            sitStream = sitStream.filter(sit -> sit.getSitDuration() != null && sit.getSitDuration() >= minDuration);
        }
        if (maxDuration != null) {
            sitStream = sitStream.filter(sit -> sit.getSitDuration() != null && sit.getSitDuration() <= maxDuration);
        }
        if (minAvgValue != null) {
            sitStream = sitStream.filter(sit -> sit.getAvgValue() != null && sit.getAvgValue() >= minAvgValue);
        }
        if (maxAvgValue != null) {
            sitStream = sitStream.filter(sit -> sit.getAvgValue() != null && sit.getAvgValue() <= maxAvgValue);
        }

        return sitStream.collect(Collectors.toList());
    }




}
