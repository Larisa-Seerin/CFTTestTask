package com.example;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsPrinter {

    private final StatisticsCollector collector;

    public StatisticsPrinter(StatisticsCollector collector) {
        this.collector = collector;
    }

    public void printShortStatistics() {
        System.out.println("Short Statistics:");
        System.out.println("Integers: " + collector.getIntegerCount());
        System.out.println("Floats: " + collector.getFloatCount());
        System.out.println("Strings: " + collector.getStringCount());
    }

    public void printFullStatistics() {
        System.out.println("Full Statistics:");

        // Статистика для целых чисел
        if (collector.getIntegerCount() > 0) {
            List<Integer> integers = collector.getIntegers();
            int min = integers.stream().min(Integer::compare).orElse(0);
            int max = integers.stream().max(Integer::compare).orElse(0);
            int sum = integers.stream().mapToInt(Integer::intValue).sum();
            double average = integers.stream().mapToInt(Integer::intValue).average().orElse(0);

            System.out.println("Integers:");
            System.out.println("  Count: " + collector.getIntegerCount());
            System.out.println("  Min: " + min);
            System.out.println("  Max: " + max);
            System.out.println("  Sum: " + sum);
            System.out.println("  Average: " + average);
        }

        // Статистика для вещественных чисел
        if (collector.getFloatCount() > 0) {
            List<Double> floats = collector.getFloats();
            double min = floats.stream().min(Double::compare).orElse(0.0);
            double max = floats.stream().max(Double::compare).orElse(0.0);
            double sum = floats.stream().mapToDouble(Double::doubleValue).sum();
            double average = floats.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

            System.out.println("Floats:");
            System.out.println("  Count: " + collector.getFloatCount());
            System.out.println("  Min: " + min);
            System.out.println("  Max: " + max);
            System.out.println("  Sum: " + sum);
            System.out.println("  Average: " + average);
        }

        // Статистика для строк
        if (collector.getStringCount() > 0) {
            List<String> strings = collector.getStrings();
            int minLength = strings.stream().mapToInt(String::length).min().orElse(0);
            int maxLength = strings.stream().mapToInt(String::length).max().orElse(0);

            System.out.println("Strings:");
            System.out.println("  Count: " + collector.getStringCount());
            System.out.println("  Shortest length: " + minLength);
            System.out.println("  Longest length: " + maxLength);
        }
    }
}