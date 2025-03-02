package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private FileProcessor fileProcessor;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter file paths and options:");
            System.out.println("  -a              Append to existing output files (default: overwrite)");
            System.out.println("  -o <outputPath> Specify the output directory (default: current directory)");
            System.out.println("  -p <prefix>     Add a prefix to output file names (default: no prefix)");
            System.out.println("  -s              Display short statistics (count of elements)");
            System.out.println("  -f              Display full statistics (min, max, sum, average for numbers; length for strings)");
            System.out.println("  <inputFile>     Path to the input file (required)");
            System.out.println("Example: -a -o /some/path -p result_ -s file1.txt");
            String input = scanner.nextLine();

            Map<String, String> options = parseArguments(input.split(" "));

            if (!validateArguments(options)) {
                System.err.println("Invalid input. Please try again.");
                continue;
            }

            // Если -o не указан, используем директорию с .jar файлом
            if (!options.containsKey("-o")) {
                options.put("-o", JarPathUtil.getJarDirectory() + File.separator);
            }

            fileProcessor.processFiles(options);
        }
    }

    private Map<String, String> parseArguments(String[] args) {
        Map<String, String> options = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                String key = args[i];
                String value = (i + 1 < args.length && !args[i + 1].startsWith("-")) ? args[i + 1] : "";
                options.put(key, value);
            } else {
                options.put("input", args[i]);
            }
        }
        return options;
    }

    private boolean validateArguments(Map<String, String> options) {
        if (!options.containsKey("input")) {
            System.err.println("No input file specified.");
            return false;
        }

        // Проверка корректности пути для -o
        if (options.containsKey("-o")) {
            String outputPath = options.get("-o");
            File outputDir = new File(outputPath);
            if (!outputDir.exists() && !outputDir.mkdirs()) {
                System.err.println("Failed to create output directory: " + outputPath);
                return false;
            }
        }
        return true;
    }
}

