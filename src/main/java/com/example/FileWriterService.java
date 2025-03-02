package com.example;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class FileWriterService {

    /**
     * Записывает данные в указанный файл.
     *
     * @param filePath   Путь к файлу, в который нужно записать данные.
     * @param data       Данные для записи.
     * @param appendMode Режим добавления (true) или перезаписи (false).
     */
    public void writeToFile(String filePath, List<String> data, boolean appendMode) {
        // Создаем директорию, если она не существует
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Создаем все недостающие директории
        }
        try (FileWriter writer = new FileWriter(filePath, appendMode)) {
            for (String line : data) {
                writer.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath);
            System.err.println("Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}