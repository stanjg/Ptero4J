package com.stanjg.ptero4j.controllers.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.admin.servers.ServerCreateAction;
import com.stanjg.ptero4j.actions.admin.servers.ServerUpdateBuildAction;
import com.stanjg.ptero4j.actions.admin.servers.ServerUpdateDetailsAction;
import com.stanjg.ptero4j.actions.admin.servers.ServerUpdateStartupAction;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServersController extends ResourceController<Server> {

    public ServersController(PteroAdminAPI api, String baseURL, String key) {
        super(api, baseURL, key, "servers");
    }

    public ServerCreateAction createNew() {
        return new ServerCreateAction(getAdminAPI());
    }

    public ServerUpdateDetailsAction editServerDetails(int id) {
        return new ServerUpdateDetailsAction(getAdminAPI(), id);
    }

    public ServerUpdateBuildAction editServerBuild(int id) {
        return new ServerUpdateBuildAction(getAdminAPI(), id);
    }

    public ServerUpdateStartupAction editServerStartup(int id) {
        return new ServerUpdateStartupAction(getAdminAPI(), id);
    }

    public List<Server> getAllServers() {
        return super.getAllResources();
    }

    public List<Server> getServers(String search) {
        return super.getResourcesWithQuery(search);
    }

    public Server getServer(int id) {
        return super.getResource(id);
    }

    public List<Server> getServerPage(int page) {
        return super.getResourcesPage(page);
    }

    public boolean deleteServer(int id) {
        return super.delete(id);
    }

    public List<Server> getServersForUser(int id) {
        return super.getResourcesFor("users", id);
    }

    public List<Server> getServersForLocation(int id) {
        return super.getResourcesFor("locations", id);
    }

    @Override
    protected Server getNewInstance(JSONObject json) {
        return new Server(getAdminAPI(), json);
    }

}
