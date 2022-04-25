package controller;

import model.entities.ServerConfig;
import services.Resources;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class AppConfigurations {
    private static final Map<String,String> CONFIG = new HashMap<>();
    public static void generateConfig() throws IOException {
        File file = new File(Resources.SETTINGS_FILE_URL);

        CONFIG.put("lang", null);
        CONFIG.put("server_ip", null);
        CONFIG.put("server_port", null);

        if(!file.exists()){
            file.createNewFile();
            storeDefaultConfig();
        }else{
            loadConfig();
        }
    }

    private static void storeDefaultConfig() throws IOException {
        CONFIG.replace("lang", "es");
        CONFIG.replace("server_ip", "localhost");
        CONFIG.replace("server_port", "8080");
        storeConfiguration();
    }

    private static void storeConfiguration() throws IOException {
        try (
                OutputStream outputStream = new FileOutputStream(Resources.SETTINGS_FILE_URL);
        ){
            Properties properties = new Properties();
            properties.putAll(CONFIG);
            properties.store(outputStream, null);
        }
    }

    private static void loadConfig() throws IOException {
        try(
                InputStream inputStream = new FileInputStream(Resources.SETTINGS_FILE_URL);
                ){
            Properties properties = new Properties();
            properties.load(inputStream);

            if(!properties.containsKey("lang") || !properties.containsKey("server_ip") ||
                    !properties.containsKey("server_port")){
                storeDefaultConfig();
                return;
            }

            CONFIG.replace("lang", properties.getProperty("lang"));
            CONFIG.replace("server_ip", properties.getProperty("server_ip"));
            CONFIG.replace("server_port", properties.getProperty("server_port"));
        }
    }

    public static void storeTranslations(String language) throws IOException {
        CONFIG.replace("lang", language);
        storeConfiguration();
    }

    public static String getTranslations() {
        return CONFIG.get("lang");
    }

    public static void storeServerConfig(ServerConfig serverConfig) throws IOException {
        CONFIG.replace("server_ip", serverConfig.getIp());
        CONFIG.replace("server_port", String.valueOf(serverConfig.getPort()));
        storeConfiguration();
    }

    public static ServerConfig getServerConfig() {
        ServerConfig serverConfig;

        serverConfig = new ServerConfig(CONFIG.get("server_ip"), Integer.parseInt(CONFIG.get("server_port")));

        return serverConfig;
    }

    public static void switchLanguage(String lang){
        switch (lang){
            case "es" -> Locale.setDefault(new Locale("es", "ES"));
            case "gl" -> Locale.setDefault(new Locale("gl", "ES"));
        }
    }
}
