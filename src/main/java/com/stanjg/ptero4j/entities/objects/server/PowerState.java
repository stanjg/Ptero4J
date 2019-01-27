package com.stanjg.ptero4j.entities.objects.server;

public enum PowerState {
    ON("on"), OFF("off"), STARTING("starting"), STOPPING("stopping"), ERROR("error");

    private String value;

    PowerState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
