package com.dmb.sit.stats.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SitCounter {

    private int morningSitCount;
    private int afternoonSitCount;
    private int eveningSitCount;
    private int nightSitCount;
    private int totalSitCount;

    public SitCounter addSit(Sit sit) {
        switch (sit.getTimeBucket()) {
            case "morning" -> morningSitCount++;
            case "afternoon" -> afternoonSitCount++;
            case "evening" -> eveningSitCount++;
            case "night" -> nightSitCount++;
            default -> {
            }
        }
        totalSitCount++;
        return this;
    }


}
