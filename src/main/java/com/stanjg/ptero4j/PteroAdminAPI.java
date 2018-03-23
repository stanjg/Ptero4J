package com.stanjg.ptero4j;

import com.stanjg.ptero4j.controllers.GenericController;
import com.stanjg.ptero4j.controllers.admin.*;
import com.stanjg.ptero4j.controllers.TestController;

import java.io.IOException;

public class PteroAdminAPI {

    private String baseURL, key;

    private UsersController usersController;
    private ServersController serversController;
    private GenericController genericController;
    private NodesController nodesController;

    public PteroAdminAPI(String baseURL, String key) {

        this.baseURL = baseURL.endsWith("/") ? baseURL  + "api/application" : baseURL + "/api/application";
        this.key = "Bearer " + key;

        try {
            new TestController(null, this.baseURL, this.key).testConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.usersController = new UsersController(this, this.baseURL, this.key);
        this.genericController = new GenericController(this, this.baseURL, this.key);
        this.serversController = new ServersController(this, this.baseURL, this.key);
        this.nodesController = new NodesController(this, this.baseURL, this.key);
    }

    public UsersController getUsersController() {
        return this.usersController;
    }

    public GenericController getGenericController() {
        return genericController;
    }

    public ServersController getServersController() {
        return serversController;
    }

    public NodesController getNodesController() {
        return nodesController;
    }
}
