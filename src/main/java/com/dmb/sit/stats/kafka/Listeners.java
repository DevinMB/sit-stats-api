package com.dmb.sit.stats.kafka;

import com.dmb.sit.stats.service.SitStatService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listeners {

    private final SitStatService sitStatService;

    public Listeners(SitStatService myService) {
        this.sitStatService = myService;
    }

    @KafkaListener(topics = "sit-topic", containerFactory = "kafkaListenerContainerFactoryNoAutoCommit")
    public void listenToSitTopic(String message) {
        sitStatService.aggregateSitRecords(message);
    }

    @KafkaListener(topics = "device-status", containerFactory = "kafkaListenerContainerFactoryNoAutoCommit")
    public void listenToLiveSensorData(String message) {
        sitStatService.updateDeviceStatus(message);
    }
}
