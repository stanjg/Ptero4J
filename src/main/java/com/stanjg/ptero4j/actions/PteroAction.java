package com.stanjg.ptero4j.actions;

import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public interface PteroAction<T> {

    T execute();

    JSONObject getAsJSON();

    String getEndpoint();

    HTTPMethod getMethod();

}
