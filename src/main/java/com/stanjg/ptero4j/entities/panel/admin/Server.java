package com.stanjg.ptero4j.entities.panel.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.admin.servers.*;
import com.stanjg.ptero4j.entities.objects.server.FeatureLimits;
import com.stanjg.ptero4j.entities.objects.server.ServerContainer;
import com.stanjg.ptero4j.entities.objects.server.ServerLimits;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class Server {

    private PteroAdminAPI api;
    private String longId, name, description, uuid;
    private int shortId, allocationId, eggId, nestId, externalId, packId, nodeId, ownerId;
    private boolean suspended;
    private ServerContainer container;
    private ServerLimits limits;
    private FeatureLimits featureLimits;

    public Server(PteroAdminAPI api, JSONObject json) {
        this(
                api,
                json.getString("identifier"),
                json.getString("name"),
                json.getString("description"),
                json.getString("uuid"),
                json.getInt("id"),
                json.getInt("allocation"),
                json.getInt("egg"),
                json.getInt("nest"),
                json.isNull("external_id") ? -1 : json.getInt("external_id"),
                json.isNull("pack") ? -1 : json.getInt("pack"),
                json.getInt("node"),
                json.getInt("user"),
                json.getBoolean("suspended"),
                new ServerContainer(
                        json.getJSONObject("container")
                ),
                new ServerLimits(
                        json.getJSONObject("limits")
                ),
                new FeatureLimits(
                        json.getJSONObject("feature_limits")
                )
        );
    }

    private Server(PteroAdminAPI api, String longId,
                   String name, String description,
                   String uuid, int shortId,
                   int allocationId, int eggId,
                   int nestId, int externalId,
                   int packId, int nodeId,
                   int ownerId, boolean suspended,
                   ServerContainer container, ServerLimits limits,
                   FeatureLimits featureLimits) {
        this.api = api;
        this.longId = longId;
        this.name = name;
        this.description = description;
        this.uuid = uuid;
        this.shortId = shortId;
        this.allocationId = allocationId;
        this.eggId = eggId;
        this.nestId = nestId;
        this.externalId = externalId;
        this.packId = packId;
        this.nodeId = nodeId;
        this.ownerId = ownerId;
        this.suspended = suspended;
        this.container = container;
        this.limits = limits;
        this.featureLimits = featureLimits;
    }

    public static ServerCreateAction create(PteroAdminAPI api) {
        return new ServerCreateAction(api);
    }

    public ServerUpdateDetailsAction editDetails() {
        return new ServerUpdateDetailsAction(api, this);
    }

    public ServerUpdateBuildAction editBuild() {
        return new ServerUpdateBuildAction(api, this);
    }

    public ServerUpdateStartupAction editStartup() {
        return new ServerUpdateStartupAction(api, this);
    }

    public boolean suspend() {
        return new GenericAdminAction(api, "/servers/"+this.shortId+"/suspend", HTTPMethod.POST).execute() == 204;
    }

    public boolean unsuspend() {
        return new GenericAdminAction(api, "/servers/"+this.shortId+"/unsuspend", HTTPMethod.POST).execute() == 204;
    }

    public boolean reinstall() {
        return new GenericAdminAction(api, "/servers/"+this.shortId+"/reinstall", HTTPMethod.POST).execute() == 204;
    }

    public boolean rebuild() {
        return new GenericAdminAction(api, "/servers/"+this.shortId+"/rebuild", HTTPMethod.POST).execute() == 204;
    }

    public boolean delete() {
        return api.getServersController().deleteServer(this.shortId);
    }

    public User getOwner() {
        return api.getUsersController().getUser(this.ownerId);
    }

    public String getLongId() {
        return longId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUuid() {
        return uuid;
    }

    public int getShortId() {
        return shortId;
    }

    public int getAllocationId() {
        return allocationId;
    }

    public int getEggId() {
        return eggId;
    }

    public int getNestId() {
        return nestId;
    }

    public int getExternalId() {
        return externalId;
    }

    public int getPackId() {
        return packId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public ServerContainer getContainer() {
        return container;
    }

    public ServerLimits getLimits() {
        return limits;
    }

    public FeatureLimits getFeatureLimits() {
        return featureLimits;
    }
}
