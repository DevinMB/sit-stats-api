package com.dmb.sit.stats.controller;
import com.dmb.sit.stats.model.DeviceStatus;
import com.dmb.sit.stats.service.DeviceStatusUpdatedEvent;
import com.dmb.sit.stats.service.SitStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DeviceStatusWebSocketController {

    @Autowired
    private SitStatService sitStatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/status")
    @SendTo("/topic/status")
    public List<DeviceStatus> broadcastStatus() {
        return new ArrayList<>(sitStatService.getDevices());
    }

    @EventListener
    public void handleDeviceStatusUpdate(DeviceStatusUpdatedEvent event) {
        // Extract device status from the event and send it to the WebSocket subscribers
        DeviceStatus updatedStatus = event.getDeviceStatus();
        messagingTemplate.convertAndSend("/topic/status", updatedStatus);
    }
}
