package com.stanjg.ptero4j.actions.admin.servers;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.entities.panel.admin.User;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class ServerUpdateDetailsAction implements PteroAction<Server> {

    private PteroAdminAPI api;

    private int serverId;
    private JSONObject json;

    public ServerUpdateDetailsAction(PteroAdminAPI api, int id) {
        this(
                api,
                api.getServersController().getServer(id)
        );
    }

    public ServerUpdateDetailsAction(PteroAdminAPI api, Server server) {
        this.api = api;
        this.serverId = server.getShortId();
        json = new JSONObject();

        json.put("name", server.getName());
        json.put("description", server.getDescription());
        json.put("external_id", server.getExternalId() == -1 ? null : server.getExternalId());
        json.put("user", server.getOwnerId());
    }

    public ServerUpdateDetailsAction setName(String name) {
        json.put("name", name);
        return this;
    }

    public ServerUpdateDetailsAction setDescription(String description) {
        json.put("description", description);
        return this;
    }

    public ServerUpdateDetailsAction setExternalId(int id) {
        json.put("external_id", id);
        return this;
    }

    public ServerUpdateDetailsAction setOwner(int id) {
        json.put("user", id);
        return this;
    }

    public ServerUpdateDetailsAction setOwner(User user) {
        return setOwner(user.getId());
    }

    @Override
    public Server execute() {
        return api.getServersController().executeUpdate(this);
    }

    @Override
    public JSONObject getAsJSON() {
        return json;
    }

    @Override
    public String getEndpoint() {
        return "/servers/"+serverId+"/details";
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.PATCH;
    }

}
