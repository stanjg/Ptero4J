package com.stanjg.ptero4j;

import com.stanjg.ptero4j.controllers.TestController;
import com.stanjg.ptero4j.controllers.user.UserServersController;

import java.io.IOException;

public class PteroUserAPI {

    private String baseURL, key;

    private UserServersController serversController;

    public PteroUserAPI(String baseURL, String key) {

        this.baseURL = baseURL.endsWith("/") ? baseURL  + "api/client" : baseURL + "/api/client";
        this.key = "Bearer " + key;

        try {
            new TestController(null, this.baseURL, this.key).testUserConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.serversController = new UserServersController(this, this.baseURL, this.key);
    }

    public UserServersController getServersController() {
        return this.serversController;
    }

}
