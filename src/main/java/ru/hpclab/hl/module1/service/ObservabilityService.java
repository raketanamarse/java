package ru.hpclab.hl.module1.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
public class ObservabilityService {

    private static class TimerRecord {
        String tag;
        long startTime;

        TimerRecord(String tag, long startTime) {
            this.tag = tag;
            this.startTime = startTime;
        }
    }

    private static class TimingEntry {
        String tag;
        long duration;
        long timestamp;

        TimingEntry(String tag, long duration, long timestamp) {
            this.tag = tag;
            this.duration = duration;
            this.timestamp = timestamp;
        }
    }

    private final Map<UUID, TimerRecord> timers = new ConcurrentHashMap<>();
    private final Queue<TimingEntry> timings = new ConcurrentLinkedQueue<>();

    @Value("${observability.maxAgeMs:60000}") // сколько максимум храним (по умолчанию 1 минута)
    private long maxAgeMs;

    public UUID startTimer(String tag) {
        UUID id = UUID.randomUUID();
        timers.put(id, new TimerRecord(tag, System.currentTimeMillis()));
        return id;
    }

    public void stopTimer(UUID id) {
        TimerRecord record = timers.remove(id);
        if (record != null) {
            long duration = System.currentTimeMillis() - record.startTime;
            timings.add(new TimingEntry(record.tag, duration, System.currentTimeMillis()));
        }
    }

    @Scheduled(fixedRate = 10000) // каждые 10 сек
    public void logStatistics() {
        long now = System.currentTimeMillis();
        timings.removeIf(entry -> now - entry.timestamp > maxAgeMs);

        List<Long> intervals = Arrays.stream(intervalValues.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .toList();

        for (long interval : intervals) {
            List<TimingEntry> filtered = timings.stream()
                    .filter(e -> now - e.timestamp <= interval)
                    .toList();

            if (!filtered.isEmpty()) {
                Map<String, List<Long>> grouped = new HashMap<>();
                for (TimingEntry entry : filtered) {
                    grouped.computeIfAbsent(entry.tag, k -> new ArrayList<>()).add(entry.duration);
                }

                log.info("=== Timing stats for last {}s ===", interval / 1000);
                for (Map.Entry<String, List<Long>> group : grouped.entrySet()) {
                    List<Long> values = group.getValue();
                    long avg = (long) values.stream().mapToLong(Long::longValue).average().orElse(0);
                    long max = values.stream().mapToLong(Long::longValue).max().orElse(0);
                    long min = values.stream().mapToLong(Long::longValue).min().orElse(0);
                    log.info("Tag: {}, count={}, avg={}ms, min={}ms, max={}ms",
                            group.getKey(), values.size(), avg, min, max);
                }
            }
        }
    }

    @Value("${observability.intervals:10000,30000,60000}")
    private String intervalValues;

    @PostConstruct
    public void init() {
        log.info("ObservabilityService started. Max age for timings: {}ms", maxAgeMs);
    }
}
