package com.stanjg.ptero4j.actions;

import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public interface PteroVoidAction {

    int execute();

    JSONObject getAsJSON();

    String getEndpoint();

    HTTPMethod getMethod();

}
