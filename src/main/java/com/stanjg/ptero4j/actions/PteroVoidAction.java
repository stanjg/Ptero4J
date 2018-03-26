package com.stanjg.ptero4j.actions;

import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public interface PteroVoidAction {

    /**
     * The method to execute the action, should return the returned http status code.
     * @return
     */
    int execute();

    /**
     * Define the data to be sent in the request in a JSON form
     * @return request body data
     */
    JSONObject getAsJSON();

    /**
     * Define the endpoint that the http request should be made to
     * @return endpoint
     */
    String getEndpoint();

    /**
     * Define what HTTP method should be used to make the request
     * @return
     */
    HTTPMethod getMethod();

}
