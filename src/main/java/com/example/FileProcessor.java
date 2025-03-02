package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

@Component
public class FileProcessor {

    @Autowired
    private DataFilter dataFilter;

    @Autowired
    private StatisticsPrinter statisticsPrinter;

    public void processFiles(Map<String, String> options) {
        String outputPath = options.getOrDefault("-o", "");
        String prefix = options.getOrDefault("-p", "");
        boolean appendMode = options.containsKey("-a");

        String integersFile = outputPath + prefix + "integers.txt";
        String floatsFile = outputPath + prefix + "floats.txt";
        String stringsFile = outputPath + prefix + "strings.txt";

        String inputFilePath = options.get("input");

        // Проверяем, существует ли файл
        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            // Если файл не существует, пробуем найти его в текущей рабочей директории
            inputFile = new File(System.getProperty("user.dir"), inputFilePath);
            if (!inputFile.exists()) {
                System.err.println("Error: Input file not found: " + inputFilePath);
                return;
            }
        }

        // Читаем входной файл и фильтруем данные
        processFile(inputFile.getAbsolutePath());

        // Записываем все данные в выходные файлы
        try {
            dataFilter.writeAllToFiles(integersFile, floatsFile, stringsFile, appendMode);
        } catch (IOException e) {
            System.err.println("Error writing to output files.");
            e.printStackTrace();
        }

        // Вывод статистики
        if (options.containsKey("-f")) {
            statisticsPrinter.printFullStatistics();
        } else if (options.containsKey("-s")) {
            statisticsPrinter.printShortStatistics();
        }
    }

    private void processFile(String inputFilePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataFilter.filterAndWrite(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + inputFilePath);
            System.err.println("Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}