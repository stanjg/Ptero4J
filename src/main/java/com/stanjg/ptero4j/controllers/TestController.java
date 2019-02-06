package com.stanjg.ptero4j.controllers;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.Response;

import java.io.IOException;

public class TestController extends Controller {

    public TestController(PteroAdminAPI api, String baseURL, String key) {
        super(api, baseURL, key);
    }

    public void testConnection() throws IOException {
        Response response = makeApiCall("/users", HTTPMethod.GET);

        switch (response.code()) {

            case 200:
                PteroUtils.log("Ptero4J loaded! Successfully made contact with the panel.");
                return;
            case 401:
                PteroUtils.log("Ptero4J failed to load! Unable to authenticate. Your key might be invalid.");
                break;
            case 403:
                PteroUtils.log("Mixed: not authorized to access /users, no permission.");
                return;
            case 404:
                PteroUtils.log("Ptero4J failed to load! An invalid URL was provided.");
                break;
            case 500:
                PteroUtils.log("An error occurred on the panel side, please check panel logs/");
                break;
        }

        System.exit(1);
    }

    public void testUserConnection() throws IOException {
        Response response = makeApiCall("/", HTTPMethod.GET);

        switch (response.code()) {

            case 200:
                PteroUtils.log("Ptero4J loaded! Successfully made contact with the panel.");
                return;
            case 401:
                PteroUtils.log("Ptero4J failed to load! Unable to authenticate. Your key might be invalid.");
                break;
            case 403:
                PteroUtils.log("Not authorized.");
                return;
            case 404:
                PteroUtils.log("Ptero4J failed to load! An invalid URL was provided.");
                break;
            case 500:
                PteroUtils.log("An error occurred on the panel side, please check panel logs/");
                break;
        }

        System.exit(1);
    }

}
