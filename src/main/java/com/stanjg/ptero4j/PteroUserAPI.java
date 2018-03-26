package com.stanjg.ptero4j;

import com.stanjg.ptero4j.controllers.GenericController;
import com.stanjg.ptero4j.controllers.TestController;
import com.stanjg.ptero4j.controllers.user.UserServersController;

import java.io.IOException;

public class PteroUserAPI {

    private String baseURL, key;

    private UserServersController serversController;
    private GenericController genericController;

    /**
     * Create an instance of UserAPI
     * @param baseURL URL of the panel (like https://panel.myhost.com)
     * @param key The client API Key from the panel
     */
    public PteroUserAPI(String baseURL, String key) {

        this.baseURL = baseURL.endsWith("/") ? baseURL  + "api/client" : baseURL + "/api/client";
        this.key = "Bearer " + key;

        try {
            new TestController(null, this.baseURL, this.key).testUserConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.serversController = new UserServersController(this, this.baseURL, this.key);
        this.genericController = new GenericController(this, this.baseURL, this.key);
    }

    /**
     * Get the ServersController used to fetch servers and execute server actions
     * @return ServersController
     */
    public UserServersController getServersController() {
        return this.serversController;
    }

    /**
     * Get the GenericController used to execute generic actions
     * @return GenericController
     */
    public GenericController getGenericController() {
        return genericController;
    }
}
