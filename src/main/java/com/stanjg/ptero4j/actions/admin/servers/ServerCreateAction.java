package com.stanjg.ptero4j.actions.admin.servers;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.objects.server.creation.CreationEnvironment;
import com.stanjg.ptero4j.entities.objects.server.creation.CreationFeatureLimits;
import com.stanjg.ptero4j.entities.objects.server.creation.CreationServerLimits;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class ServerCreateAction implements PteroAction<Server> {

    private JSONObject json;
    private PteroAdminAPI api;

    public ServerCreateAction(PteroAdminAPI api) {
        this.api = api;
        this.json = new JSONObject();
    }

    public ServerCreateAction setExternalId(int id) {
        json.put("external_id", id);
        return this;
    }

    public ServerCreateAction setName(String name) {
        json.put("name", name);
        return this;
    }

    public ServerCreateAction setDescription(String description) {
        json.put("description", description);
        return this;
    }

    public ServerCreateAction setUserId(int id) {
        json.put("user", id);
        return this;
    }

    public ServerCreateAction setNestId(int id) {
        json.put("nest", id);
        return this;
    }

    public ServerCreateAction setEggId(int id) {
        json.put("egg", id);
        return this;
    }

    public ServerCreateAction setPackId(int id) {
        json.put("pack", id);
        return this;
    }

    public ServerCreateAction setDockerImage(String image) {
        json.put("docker_image", image);
        return this;
    }

    public ServerCreateAction setStartupCommand(String command) {
        json.put("startup", command);
        return this;
    }

    public ServerCreateAction addEnvironmentValue(String name, String value) {
        return addEnvironmentValue(new CreationEnvironment(name, value));
    }

    public ServerCreateAction addEnvironmentValue(CreationEnvironment environment) {
        JSONObject environments = getOrCreateJSONObject(json, "environment");
        environments.put(environment.getName(), environment.getValue());
        return this;
    }

    public ServerCreateAction setEnvironmentValues(List<CreationEnvironment> environments) {
        JSONObject environmentsJSON = new JSONObject();
        environments.forEach(environment -> environmentsJSON.put(environment.getName(), environment.getValue()));
        json.put("environment", environmentsJSON.toString());
        return this;
    }

    public ServerCreateAction setEnvironmentValues(CreationEnvironment... environments) {
        return setEnvironmentValues(Arrays.asList(environments));
    }

    public ServerCreateAction setSkipScripts(boolean skipScripts) {
        json.put("skip_scripts", skipScripts);
        return this;
    }

    public ServerCreateAction setLimits(int disk, int memory, int swap, int io, int cpu) {
        return setLimits(new CreationServerLimits(disk, memory, swap, io, cpu));
    }

    public ServerCreateAction setLimits(CreationServerLimits limits) {
        json.put("limits", limits.getAsJSON());
        return this;
    }

    public ServerCreateAction setFeatureLimits(int databases, int allocations) {
        return setFeatureLimits(new CreationFeatureLimits(databases, allocations));
    }

    public ServerCreateAction setFeatureLimits(CreationFeatureLimits limits) {
        json.put("feature_limits", limits.getAsJSON());
        return this;
    }

    public ServerCreateAction setAllocation(int id) {
        JSONObject allocations = getOrCreateJSONObject(json, "allocations");
        allocations.put("default", id);
        return this;
    }

    public ServerCreateAction addAdditionalAllocations(int... ids) {
        JSONObject allocations = getOrCreateJSONObject(json, "allocations");
        JSONArray additional = getOrCreateJSONArray(allocations, "additional");
        for (int i : ids) {
            additional.put(i);
        }
        return this;
    }

    public ServerCreateAction setDeployLocations(int... ids) {
        JSONObject deploy = getOrCreateJSONObject(json, "deploy");
        JSONArray locations = getOrCreateJSONArray(deploy, "locations");
        for (int i : ids) {
            locations.put(i);
        }
        return this;
    }

    public ServerCreateAction setDedicatedIp(boolean dedicatedIp) {
        JSONObject deploy = getOrCreateJSONObject(json, "deploy");
        deploy.put("dedicated_ip", dedicatedIp);
        return this;
    }

    public ServerCreateAction setPortRange(String... ranges) {
        JSONObject deploy = getOrCreateJSONObject(json, "deploy");
        JSONArray portRanges = getOrCreateJSONArray(deploy, "port_range");
        for (String s : ranges) {
            portRanges.put(s);
        }
        return this;
    }

    public ServerCreateAction setStartOnCompletion(boolean startOnCompletion) {
        json.put("start_on_completion", startOnCompletion);
        return this;
    }

    private JSONObject getOrCreateJSONObject(JSONObject origin, String key) {
        if (!origin.has(key))
            origin.put(key, new JSONObject());

        return origin.getJSONObject(key);
    }

    private JSONArray getOrCreateJSONArray(JSONObject origin, String key) {
        if (!origin.has(key))
            origin.put(key, new JSONArray());

        return origin.getJSONArray(key);
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
        return "/servers";
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.POST;
    }
}
