package com.dmb.sit.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Sit {

    @JsonProperty("start_timestamp")
    private Long startTimestamp;

    @JsonProperty("end_timestamp")
    private Long endTimestamp;

    @JsonProperty("device_id")
    private String deviceId;

    @JsonProperty("sit_duration")
    private Long sitDuration;

    @JsonProperty("time_bucket")
    private String timeBucket;

    @JsonProperty("avg_value")
    private Long avgValue;

    public Sit(Long startTimestamp, Long endTimestamp, String deviceId, Long sitDuration, Long avgValue) {
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.deviceId = deviceId;
        this.sitDuration = sitDuration;
        this.avgValue = avgValue;
        this.timeBucket = determineTimeBucket(startTimestamp);
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
