package com.pi.users.servicesImpl;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Aspect
@Component
public class StatisticsLoggerAspect {

    @Value("${stats.file.path:./registration-stats.txt}")
    private String statsFilePath;

    @AfterReturning(pointcut = "execution(* com.pi.users.services.UserServices.getUsersRegistrationStats(..))", returning = "result")
    public void logStatistics(List<Object[]> result) {
        System.out.println("Aspect triggered for logging statistics.");

        try (FileWriter writer = new FileWriter(statsFilePath, true)) {
            for (Object[] record : result) {
                int month = Integer.parseInt(record[0].toString());
                long count = (Long) record[1];
                writer.append(String.format("Month: %d, Count: %d%n", month, count));
            }
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write statistics to file: " + e.getMessage());
        }
    }
}
