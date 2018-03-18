package com.stanjg.ptero4j.entities.panel.user;

import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.entities.objects.server.FeatureLimits;
import com.stanjg.ptero4j.entities.objects.server.ServerLimits;
import org.json.JSONObject;

public class UserServer {

    private PteroUserAPI api;
    private String id, uuid, name, description;
    boolean owner;
    private ServerLimits limits;
    private FeatureLimits featureLimits;

    public UserServer(PteroUserAPI api, JSONObject json) {
        this(
                api,
                json.getString("identifier"),
                json.getString("uuid"),
                json.getString("name"),
                json.getString("description"),
                json.getBoolean("server_owner"),
                new ServerLimits(
                        json.getJSONObject("limits")
                ),
                new FeatureLimits(
                        json.getJSONObject("feature_limits")
                )
        );
    }

    private UserServer(PteroUserAPI api, String id, String uuid, String name, String description, boolean owner, ServerLimits limits, FeatureLimits featureLimits) {
        this.api = api;
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.limits = limits;
        this.featureLimits = featureLimits;
    }

    public String getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isOwner() {
        return owner;
    }

    public ServerLimits getLimits() {
        return limits;
    }

    public FeatureLimits getFeatureLimits() {
        return featureLimits;
    }
}
