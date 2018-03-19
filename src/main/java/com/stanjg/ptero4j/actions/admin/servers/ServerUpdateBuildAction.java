package com.stanjg.ptero4j.actions.admin.servers;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.objects.server.FeatureLimits;
import com.stanjg.ptero4j.entities.objects.server.ServerLimits;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class ServerUpdateBuildAction implements PteroAction<Server> {

    private PteroAdminAPI api;
    private int serverId;
    private JSONObject json;

    public ServerUpdateBuildAction(PteroAdminAPI api, int serverId) {
        this(
                api,
                api.getServersController().getServer(serverId)
        );
    }

    public ServerUpdateBuildAction(PteroAdminAPI api, Server server) {
        this.api = api;
        this.serverId = server.getShortId();
        json = new JSONObject();

        json.put("allocation", server.getAllocationId());

        ServerLimits limits = server.getLimits();
        json.put("memory", limits.getMemory());
        json.put("swap", limits.getSwap());
        json.put("io", limits.getIo());
        json.put("cpu", limits.getCpu());
        json.put("disk", limits.getDisk());

        FeatureLimits featureLimits = server.getFeatureLimits();
        JSONObject jsonFeatureLimits = new JSONObject();
        jsonFeatureLimits.put("databases", featureLimits.getMaxDatabases());
        jsonFeatureLimits.put("allocations", featureLimits.getMaxAllocations());
        json.put("feature_limits", jsonFeatureLimits);
    }

    public ServerUpdateBuildAction setMemory(int memory) {
        json.put("memory", memory);
        return this;
    }

    public ServerUpdateBuildAction setAllocation(int id) {
        json.put("allocation", id);
        return this;
    }

    public ServerUpdateBuildAction setSwap(int swap) {
        json.put("swap", swap);
        return this;
    }

    public ServerUpdateBuildAction setIO(int io) {
        json.put("io", io);
        return this;
    }

    public ServerUpdateBuildAction setCPU(int cpu) {
        json.put("cpu", cpu);
        return this;
    }

    public ServerUpdateBuildAction setDisk(int disk) {
        json.put("disk", disk);
        return this;
    }

    public ServerUpdateBuildAction setMaxDatabases(int max) {
        JSONObject featureLimits = getOrCreateSubJSONObject("feature_limits");
        featureLimits.put("databases", max);
        json.put("feature_limits", featureLimits);
        return this;
    }

    public ServerUpdateBuildAction setMaxAllocations(int max) {
        JSONObject featureLimits = getOrCreateSubJSONObject("feature_limits");
        featureLimits.put("allocations", max);
        json.put("feature_limits", featureLimits);
        return this;
    }

    private JSONObject getOrCreateSubJSONObject(String key) {
        return json.has(key) ? json.getJSONObject("feature_limits") : new JSONObject();
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
        return "/servers/"+serverId+"/build";
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.PATCH;
    }

}
