package com.stanjg.ptero4j.controllers.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.actions.admin.servers.settings.ServerUpdateBuildAction;
import com.stanjg.ptero4j.actions.admin.servers.settings.ServerUpdateDetailsAction;
import com.stanjg.ptero4j.actions.admin.servers.settings.ServerUpdateStartupAction;
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

    public List<Server> getServersForUser(int user) {

        try {
            Response response = makeApiCall("/users/"+user+"?include=servers", HTTPMethod.GET);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<Server> resources = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string())
                    .getJSONObject("attributes")
                    .getJSONObject("relationships")
                    .getJSONObject("servers");
            super.addPageToList(json, resources);

            return resources;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected Server getNewInstance(JSONObject json) {
        return new Server(getAdminAPI(), json);
    }

}
