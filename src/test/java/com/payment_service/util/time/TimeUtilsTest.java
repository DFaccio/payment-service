package com.payment_service.util.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class TimeUtilsTest {

    @Test
    public void testGetZoneId() {
        ZoneId expectedZoneId = ZoneId.of("America/Sao_Paulo");

        ZoneId actualZoneId = TimeUtils.getZoneId();

        assertThat(actualZoneId).isEqualTo(expectedZoneId);
    }

    @Test
    public void testGetDate() {
        String dateString = "2024-08-08T12:34:56";
        LocalDateTime expectedDate = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        LocalDateTime actualDate = TimeUtils.getDate(dateString);

        assertThat(actualDate).isEqualTo(expectedDate);
    }

    @Test
    public void testGetTime() {
        LocalDateTime now = LocalDateTime.now()
                .atZone(ZoneId.of("America/Sao_Paulo"))
                .toLocalDateTime();

        assertNotNull(now);
    }

    @Test
    public void testNow() {
        LocalDateTime now = LocalDateTime.now(Clock.system(ZoneId.of("America/Sao_Paulo")));

        assertNotNull(now);
    }

    @Test
    public void testGetDurationBetweenInSeconds() {
        LocalDateTime start = LocalDateTime.of(2024, 8, 8, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 8, 8, 10, 1);
        long expectedDurationInSeconds = Duration.between(start, end).toSeconds();

        long actualDurationInSeconds = TimeUtils.getDurationBetweenInSeconds(start, end);

        assertThat(actualDurationInSeconds).isEqualTo(expectedDurationInSeconds);
    }
}
