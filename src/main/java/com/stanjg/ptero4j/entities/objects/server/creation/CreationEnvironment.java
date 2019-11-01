package com.stanjg.ptero4j.entities.objects.server.creation;

import org.json.JSONObject;

public class CreationEnvironment {

    private String name, value;

    public CreationEnvironment() {
    }

    public CreationEnvironment(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public JSONObject getAsJSON() {
        return new JSONObject()
                .put("name", name)
                .put("value", value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
