package com.stanjg.ptero4j.actions.admin.servers;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class ServerUpdateStartupAction implements PteroAction<Server> {

    private PteroAdminAPI api;

    private int serverId;
    private JSONObject json;

    public ServerUpdateStartupAction(PteroAdminAPI api, int serverId) {
        this(
                api,
                api.getServersController().getServer(serverId)
        );
    }

    public ServerUpdateStartupAction(PteroAdminAPI api, Server server) {
        this.api = api;
        this.serverId = server.getShortId();
        this.json = new JSONObject();

        json.put("startup", server.getContainer().getStartupCommand());
        json.put("egg", server.getEggId());
        json.put("pack", server.getPackId() == -1 ? null : server.getPackId());
        json.put("image", server.getContainer().getImage());
        json.put("skip_scripts", false); // TODO:sjg add this

        JSONObject envVars = new JSONObject();
        for (String key : server.getContainer().getEnvironmentVariables().keySet()) {
            envVars.put(key, server.getContainer().getEnvironmentVariables().get(key));
        }

        json.put("environment", envVars);
    }

    public ServerUpdateStartupAction setStartupCommand(String command) {
        json.put("startup", command);
        return this;
    }

    public ServerUpdateStartupAction setEggId(int id) {
        json.put("egg", id);
        return this;
    }

    public ServerUpdateStartupAction setPackId(int id) {
        json.put("pack", id);
        return this;
    }

    public ServerUpdateStartupAction setImage(String image) {
        json.put("image", image);
        return this;
    }

    public ServerUpdateStartupAction setSkipScripts(boolean skipScripts) {
        json.put("skip_scripts", skipScripts);
        return this;
    }

    public ServerUpdateStartupAction setEnvironmentVar(String key, String value) {
        json.getJSONObject("environment").put(key, value);
        return this;
    }

    @Override
    public Server execute() {
        return api.getServersController().executeAction(this);
    }

    @Override
    public JSONObject getAsJSON() {
        return json;
    }

    @Override
    public String getEndpoint() {
        return "/servers/"+serverId+"/startup";
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.PATCH;
    }

}
