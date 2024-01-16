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

    @KafkaListener(topics = "sit-topic", containerFactory = "kafkaListenerContainerFactory")
    public void listenToSitTopic(String message) {
        sitStatService.aggregateSitRecords(message);
    }

    @KafkaListener(topics = "raw-sit-topic", containerFactory = "kafkaListenerContainerFactoryNoAutoCommit")
    public void listenToLiveSensorData(String message) {
        sitStatService.liveSitSensorRecords(message);
    }
}
