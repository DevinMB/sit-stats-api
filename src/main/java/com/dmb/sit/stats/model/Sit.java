package com.dmb.sit.stats.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Data
@NoArgsConstructor
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

    @JsonProperty("startTimestamp")
    public Long getStartTimestamp() {
        return startTimestamp;
    }

    @JsonProperty("start_timestamp")
    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    @JsonProperty("endTimestamp")
    public Long getEndTimestamp() {
        return endTimestamp;
    }

    @JsonProperty("end_timestamp")
    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @JsonProperty("deviceId")
    @JsonIgnore
    public String getDeviceId() {
        return deviceId;
    }

    @JsonProperty("device_id")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @JsonProperty("sitDuration")
    public Long getSitDuration() {
        return sitDuration;
    }

    @JsonProperty("sit_duration")
    public void setSitDuration(Long sitDuration) {
        this.sitDuration = sitDuration;
    }

    @JsonProperty("timeBucket")
    public String getTimeBucket() {
        return timeBucket;
    }

    @JsonProperty("time_bucket")
    public void setTimeBucket(String timeBucket) {
        this.timeBucket = timeBucket;
    }


    @JsonProperty("avgValue")
    public Long getAvgValue() {
        return avgValue;
    }

    @JsonProperty("avg_value")
    public void setAvgValue(Long avgValue) {
        this.avgValue = avgValue;
    }
}
