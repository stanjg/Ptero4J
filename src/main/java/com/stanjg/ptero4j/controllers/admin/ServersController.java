package com.stanjg.ptero4j.controllers.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.actions.admin.servers.settings.ServerUpdateBuildAction;
import com.stanjg.ptero4j.actions.admin.servers.settings.ServerUpdateDetailsAction;
import com.stanjg.ptero4j.actions.admin.servers.settings.ServerUpdateStartupAction;
import com.stanjg.ptero4j.controllers.Controller;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServersController extends Controller {

    public ServersController(PteroAdminAPI api, String baseURL, String key) {
        super(api, baseURL, key);
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

    public Server executeAction(PteroAction action) {

        JSONObject data = action.getAsJSON();

        try {

            Response response = makeApiCall(action.getEndpoint(), action.getMethod(), data);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            JSONObject json = new JSONObject(response.body().string()).getJSONObject("attributes");

            return new Server(getAdminAPI(), json);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    public List<Server> getServers(int page) {

        try {
            Response response = makeApiCall("/servers?page="+page, HTTPMethod.GET);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            JSONObject json = new JSONObject(response.body().string());
            List<Server> servers = new ArrayList<>();

            addPageOfServersToList(json, servers);

            return servers;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Server> getServers() {

        try {
            Response response = makeApiCall("/servers", HTTPMethod.GET);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<Server> servers = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("/servers?page="+i, HTTPMethod.GET).body().string());

                addPageOfServersToList(json, servers);
            }

            return servers;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Server> getServers(String query) {

        try {
            Response response = makeApiCall("/servers?search="+query, HTTPMethod.GET);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<Server> servers = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("/servers?search="+query+"?page="+i, HTTPMethod.GET).body().string());

                addPageOfServersToList(json, servers);
            }

            return servers;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Server getServer(int id) {
        try {
            Response response = makeApiCall("/servers/"+id, HTTPMethod.GET);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            JSONObject json = new JSONObject(response.body().string()).getJSONObject("attributes");

            return new Server(getAdminAPI(), json);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Server> getServersForUser(int id) {

        try {
            Response response = makeApiCall("/users/"+id+"?include=servers", HTTPMethod.GET);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            JSONArray arr = new JSONObject(response.body().string())
                    .getJSONObject("attributes")
                    .getJSONObject("relationships")
                    .getJSONObject("servers")
                    .getJSONArray("data");

            List<Server> servers = new ArrayList<>();

            for (int i = 0; i < arr.length(); i++) {
                servers.add(new Server(getAdminAPI(), arr.getJSONObject(i).getJSONObject("attributes")));
            }

            return servers;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addPageOfServersToList(JSONObject page, List<Server> list) {
        JSONArray arr = page.getJSONArray("data");
        for (int j = 0; j < arr.length(); j ++) {
            JSONObject jserver = arr.getJSONObject(j);

            list.add(new Server(getAdminAPI(), jserver.getJSONObject("attributes")));
        }
    }

}
