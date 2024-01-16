package com.dmb.sit.stats.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
public class Sit {
    private Long startTimestamp;
    private Long endTimestamp;
    private String deviceId;
    private Long sitDuration;
    private String timeBucket;
    private Long avgValue;

    public Sit(Long startTimestamp, Long endTimestamp, String deviceId, Long sitDuration, Long avgValue) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.deviceId = deviceId;
        this.sitDuration = sitDuration;
        this.avgValue = avgValue;
        this.timeBucket = determineTimeBucket(startTimestamp);
    }

    public Sit() {
    }

    private String determineTimeBucket(Long timestamp) {
        ZonedDateTime detroitTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.of("America/Detroit"));
        int hour = detroitTime.getHour();

        if (hour >= 6 && hour < 12) {
            return "morning";
        } else if (hour >= 12 && hour < 18) {
            return "afternoon";
        } else if (hour >= 18 && hour < 24) {
            return "evening";
        } else {
            return "night";
        }
    }


}
