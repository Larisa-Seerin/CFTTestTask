package com.example;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class StatisticsCollector {
    private int integerCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;

    private List<Integer> integers = new ArrayList<>();
    private List<Double> floats = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    public void collectInteger(int value) {
        integerCount++;
        integers.add(value);
    }

    public void collectFloat(double value) {
        floatCount++;
        floats.add(value);
    }

    public void collectString(String value) {
        stringCount++;
        strings.add(value);
    }

    public void clear() {
        integers.clear();
        floats.clear();
        strings.clear();
        integerCount = 0;
        floatCount = 0;
        stringCount = 0;
    }

}