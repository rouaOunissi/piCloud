package com.pi.users.controllers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/stat/api")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Value("${stats.file.path:registration-stats.txt}")
    private String filePath;

    @GetMapping("/statistics")
    public List<String> getStatistics() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        Map<Integer, Integer> monthCounts = new HashMap<>();

        // Assuming each line represents the latest count for that month.
        for (String line : lines) {
            String[] parts = line.split(",");
            int month = Integer.parseInt(parts[0].split(": ")[1].trim());
            int count = Integer.parseInt(parts[1].split(": ")[1].trim());
            // Simply overwrite the existing entry. This assumes that your data file
            // has lines ordered in such a way that the last occurrence of each month
            // is the most current data.
            monthCounts.put(month, count);
        }

        // Convert the map entries to a sorted list of strings.
        return monthCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // Sort by month (key)
                .map(entry -> "Month: " + entry.getKey() + ", Count: " + entry.getValue())
                .collect(Collectors.toList());
    }
}
