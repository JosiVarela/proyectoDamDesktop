package controller;

import services.Resources;

import java.io.*;
import java.util.Locale;
import java.util.Properties;

public class AppConfigurations {
    public static void generateConfig() throws IOException {
        File file = new File(Resources.SETTINGS_FILE_URL);

        if(!file.exists()){
            file.createNewFile();
            storeTranslations("es");
        }
    }

    public static void storeTranslations(String language) throws IOException {
        try (
                OutputStream outputStream = new FileOutputStream(Resources.SETTINGS_FILE_URL);
                ){
            Properties properties = new Properties();
            properties.setProperty("lang", language);
            properties.store(outputStream, null);
        }
    }

    public static String getTranslations() throws IOException {
        String value;
        try(
                InputStream inputStream = new FileInputStream(Resources.SETTINGS_FILE_URL);
                ){
            Properties properties = new Properties();
            properties.load(inputStream);
            value = properties.getProperty("lang");
        }

        return value;
    }

    public static void switchLanguage(String lang){
        switch (lang){
            case "es" -> Locale.setDefault(new Locale("es", "ES"));
            case "gl" -> Locale.setDefault(new Locale("gl", "ES"));
        }
    }
}
