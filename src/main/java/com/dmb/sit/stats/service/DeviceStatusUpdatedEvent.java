package com.dmb.sit.stats.service;

import com.dmb.sit.stats.model.DeviceStatus;
import org.springframework.context.ApplicationEvent;

public class DeviceStatusUpdatedEvent extends ApplicationEvent {
    private final DeviceStatus deviceStatus;

    public DeviceStatusUpdatedEvent(Object source, DeviceStatus deviceStatus) {
        super(source);
        this.deviceStatus = deviceStatus;
    }

    public DeviceStatus getDeviceStatus() {
        return deviceStatus;
    }
}

