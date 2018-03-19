package com.stanjg.ptero4j.entities.objects.server;

public enum PowerAction {

    START("start"), STOP("stop"), RESTART("restart"), KILL("kill");

    private String value;

    PowerAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
