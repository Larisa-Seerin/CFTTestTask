package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class DataFilter {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

    @Autowired
    private FileWriterService fileWriterService;

    @Autowired
    private StatisticsCollector statisticsCollector;

    private List<String> integers = new ArrayList<>();
    private List<String> floats = new ArrayList<>();
    private List<String> strings = new ArrayList<>();

    public void filterAndWrite(String data) {
        if (data == null || data.isEmpty()) {
            System.err.println("Warning: Empty line skipped.");
            return;
        }

        if (INTEGER_PATTERN.matcher(data).matches()) {
            integers.add(data);
            statisticsCollector.collectInteger(Integer.parseInt(data));
        } else if (DOUBLE_PATTERN.matcher(data).matches()) {
            floats.add(data);
            statisticsCollector.collectFloat(Double.parseDouble(data));
        } else {
            strings.add(data);
            statisticsCollector.collectString(data);
        }
    }

    public void writeAllToFiles(String integersFile, String floatsFile, String stringsFile, boolean appendMode) throws IOException {
        fileWriterService.writeToFile(integersFile, integers, appendMode);
        fileWriterService.writeToFile(floatsFile, floats, appendMode);
        fileWriterService.writeToFile(stringsFile, strings, appendMode);
        statisticsCollector.clear();
    }
}
