package com.dmb.sit.stats.model;

import lombok.*;

@Data
@NoArgsConstructor
public class SensorData {
    private Long timestamp;
    private String deviceId;
    private Boolean sitStatus;
    private Long avgValue;

    public SensorData(Long timestamp, String deviceId, Boolean sitStatus) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.sitStatus = sitStatus;
    }

    public SensorData(Long timestamp, String deviceId, Boolean sitStatus, Long avgValue) {
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.sitStatus = sitStatus;
        this.avgValue = avgValue;
    }

}
