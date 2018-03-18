package com.stanjg.ptero4j.entities.objects.server;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerContainer {

    private String startupCommand, image;
    private boolean installed;
    private Map<String, String> environmentVariables;

    public ServerContainer(JSONObject json) {
        this(
                json.getString("startup_command"),
                json.getString("image"),
                json.getBoolean("installed"),
                json.getJSONObject("environment")
        );
    }

    private ServerContainer(String startupCommand, String image, boolean installed, JSONObject json) {
        this.startupCommand = startupCommand;
        this.image = image;
        this.installed = installed;
        this.environmentVariables = getEnvironmentVariablesMap(json);
    }

    private static Map<String, String> getEnvironmentVariablesMap(JSONObject json) {
        Map<String, String> vars = new HashMap<>();
        for (String key : json.keySet()) {
            vars.put(key, json.getString(key));
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
        return installed;
    }

    public Map<String, String> getEnvironmentVariables() {
        return environmentVariables;
    }
}
