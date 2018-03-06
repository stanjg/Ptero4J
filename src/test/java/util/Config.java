package util;

import java.io.*;
import java.util.Properties;

public class Config {

    private static Properties props;
    private static Config instance;

    public Config() {
        if (instance != null)
            return;

        instance = this;
        props = new Properties();

        File file = new File("config.properties");

        if (!file.exists())
            createFile();

        try (FileInputStream stream = new FileInputStream("config.properties")) {

            props.load(stream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createFile() {

        try (OutputStream out = new FileOutputStream("config.properties")) {

            Properties prop = new Properties();

            prop.setProperty("baseurl", "");
            prop.setProperty("key", "");

            prop.store(out, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Config file created, please fill in the fields");
        System.exit(0);
    }

    public static String getBaseURL() {
        return props.getProperty("baseurl");
    }

    public static String getKey() {
        return props.getProperty("key");
    }

}
