package com.stanjg.ptero4j.entities.objects.server;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerContainer {

    private String startupCommand, image;
    private int installed;
    private Map<String, Object> environmentVariables;

    public ServerContainer(JSONObject json) {
        this(
                json.getString("startup_command"),
                json.getString("image"),
                json.getInt("installed"),
                json.getJSONObject("environment")
        );
    }

    private ServerContainer(String startupCommand, String image, int installed, JSONObject json) {
        this.startupCommand = startupCommand;
        this.image = image;
        this.installed = installed;
        this.environmentVariables = getEnvironmentVariablesMap(json);
    }

    private static Map<String, Object> getEnvironmentVariablesMap(JSONObject json) {
        Map<String, Object> vars = new HashMap<>();
        for (String key : json.keySet()) {
            vars.put(key, json.get(key));
        }
        return vars;
    }

    public String getStartupCommand() {
        return startupCommand;
    }

    public String getImage() {
        return image;
    }

    public boolean isInstalled() {
        return installed == 1;
    }

    public Map<String, Object> getEnvironmentVariables() {
        return environmentVariables;
    }
}
