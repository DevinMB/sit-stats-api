package com.dmb.sit.stats.model.dto;

import com.dmb.sit.stats.model.SensorData;
import com.dmb.sit.stats.model.Sit;
import com.dmb.sit.stats.model.SitCounter;
import lombok.Data;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Data
@ResponseBody
public class SummaryDto {
    private String deviceName;
    private Sit lastSit;
    private Sit maxSit;
    private SitCounter sitCounter;
    private Long totalSitTime;
    private SensorData lastSensorRead;

    public SummaryDto(String deviceName, List<Sit> sits) {
        this.deviceName = deviceName;
        this.sitCounter = new SitCounter();
        this.totalSitTime = 0L;
        this.maxSit = null;
        this.lastSit = null;

        if (sits != null && !sits.isEmpty()) {
            this.maxSit = sits.get(0);
            this.lastSit = sits.get(0);

            for (Sit sit : sits) {
                // Update maxSit if current sit has a longer duration
                if (sit.getSitDuration() > maxSit.getSitDuration()) {
                    maxSit = sit;
                }

                // Update lastSit if current sit has a later endTimestamp
                if (lastSit.getEndTimestamp() < sit.getEndTimestamp()) {
                    lastSit = sit;
                }

                // Aggregate sit counts
                sitCounter.addSit(sit);

                // Aggregate total sit time
                totalSitTime += sit.getSitDuration();
            }
        }
    }
}
