package com.stanjg.ptero4j.actions.users;

import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.actions.PteroVoidAction;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class GenericUserAction implements PteroVoidAction {

    private PteroUserAPI api;
    private String endpoint;
    private HTTPMethod method;
    private JSONObject json;

    public GenericUserAction(PteroUserAPI api, String endpoint, HTTPMethod method) {
        this(
                api,
                endpoint,
                method,
                new JSONObject()
        );
    }

    public GenericUserAction(PteroUserAPI api, String endpoint, HTTPMethod method, JSONObject json) {
        this.api = api;
        this.endpoint = endpoint;
        this.method = method;
        this.json = json;
    }

    @Override
    public int execute() {
        return api.getGenericController().executeAction(this);
    }

    @Override
    public JSONObject getAsJSON() {
        return this.json;
    }

    @Override
    public String getEndpoint() {
        return this.endpoint;
    }

    @Override
    public HTTPMethod getMethod() {
        return this.method;
    }
}
