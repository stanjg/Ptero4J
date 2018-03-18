package com.stanjg.ptero4j;

import com.stanjg.ptero4j.controllers.TestController;
import com.stanjg.ptero4j.controllers.UsersController;

import java.io.IOException;

public class PteroAPI {

    private String baseURL, key;

    private UsersController usersController;

    public PteroAPI(String baseURL, String key) {
        this.baseURL = baseURL.endsWith("/") ? baseURL  + "api/application" : baseURL + "/api/application";
        this.key = "Bearer " + key;

        try {
            new TestController(this.baseURL, this.key).testConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.usersController = new UsersController(this.baseURL, this.key);
    }

    public UsersController getUsersController() {
        return this.usersController;
    }

}
