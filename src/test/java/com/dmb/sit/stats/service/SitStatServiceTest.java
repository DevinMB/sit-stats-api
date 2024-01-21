package com.dmb.sit.stats.service;

import com.dmb.sit.stats.exception.DeviceNotFoundException;
import com.dmb.sit.stats.model.Sit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SitStatServiceTest {

    private SitStatService sitStatService;

    private Sit sit1 = new Sit(11111111L, 222232222L, "Sensor", 2L, -22223333L);
    private Sit sit2 = new Sit(11111111L, 222232222L, "Sensor", 2L, -22223333L);

    @BeforeEach
    public void setUp() {
        sitStatService = new SitStatService(); // Assuming a default constructor is available
        sitStatService.addSit("Sensor", sit1);
        sitStatService.addSit("Sensor", sit2);
    }

    @Test
    public void testGetDeviceSitsWithValidDeviceId() {
        String deviceId = "Sensor";
        List<Sit> actualSits = sitStatService.getDeviceSits(deviceId, null, null, null, null, null, null);
        assertNotNull(actualSits);
        assertEquals(2, actualSits.size());
        assertTrue(actualSits.contains(sit1));
        assertTrue(actualSits.contains(sit2));
    }

    @Test
    public void testGetDeviceSitsWithInvalidDeviceId() {
        String deviceId = "invalidDeviceId";
        assertThrows(DeviceNotFoundException.class, () -> {
            sitStatService.getDeviceSits(deviceId, null, null, null, null, null, null);
        });
    }

    // Additional tests for filtering by timestamps, duration, and average value
    // These tests should create a list of Sit objects, apply the filters, and verify the filtered results
}
