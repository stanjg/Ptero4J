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
    private LocationsController locationsController;

    /**
     * Create an instance of AdminAPI
     * @param baseURL URL of the panel (like https://panel.myhost.com)
     * @param key The application API Key from the panel
     */
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
        this.locationsController = new LocationsController(this, this.baseURL, this.key);
    }

    /**
     * Get the UsersController used to fetch users and execute user actions
     * @return UsersController
     */
    public UsersController getUsersController() {
        return this.usersController;
    }

    /**
     * Get the GenericController used to execute generic actions
     * @return GenericController
     */
    public GenericController getGenericController() {
        return genericController;
    }

    /**
     * Get the ServersController used to fetch servers and execute server actions
     * @return ServersController
     */
    public ServersController getServersController() {
        return serversController;
    }

    /**
     * Get the NodesController used to fetch nodes and execute node actions
     * @return NodesController
     */
    public NodesController getNodesController() {
        return nodesController;
    }

    /**
     * Get the LocationsController used to fetch locations and execute location actions
     * @return LocationsController
     */
    public LocationsController getLocationsController() {
        return locationsController;
    }
}
