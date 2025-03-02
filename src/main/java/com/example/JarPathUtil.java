package com.example;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class JarPathUtil {

    /**
     * Возвращает путь к директории, где находится исполняемый .jar файл.
     * Если путь не может быть получен, возвращает текущую рабочую директорию.
     *
     * @return Абсолютный путь к директории.
     */
    public static String getJarDirectory() {
        try {
            URL jarUrl = Application.class.getProtectionDomain().getCodeSource().getLocation();
            File jarFile = new File(jarUrl.toURI());
            return jarFile.getParentFile().getAbsolutePath();

        } catch (URISyntaxException | NullPointerException e) {
            return System.getProperty("user.dir");
        }
    }
}