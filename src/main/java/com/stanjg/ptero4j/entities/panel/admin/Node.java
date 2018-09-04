package com.stanjg.ptero4j.entities.panel.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.admin.nodes.NodeUpdateAction;
import org.json.JSONObject;

public class Node {

    private PteroAdminAPI api;
    private int id, locationId, memory, memoryOverAllocation, disk, diskOverAllocation, uploadSize, daemonListen, daemonSftp;
    private String name, description, fqdn, scheme, daemonBase;
    private boolean isPublic, behindProxy;

    public Node(PteroAdminAPI api, JSONObject json) {
        this(
                api,
                json.getInt("id"),
                json.getInt("location_id"),
                json.getInt("memory"),
                json.getInt("memory_overallocate"),
                json.getInt("disk"),
                json.getInt("disk_overallocate"),
                json.getInt("upload_size"),
                json.getInt("daemon_listen"),
                json.getInt("daemon_sftp"),
                json.getString("name"),
                json.isNull("description") ? null : json.getString("description"),
                json.getString("fqdn"),
                json.getString("scheme"),
                json.getString("daemon_base"),
                // Need to do the check this way as older versions of pterodactyl had a fault where the public field was sent as an integer
                json.get("public") instanceof Integer ? json.getInt("public") == 1 : json.getBoolean("public"),
                json.getBoolean("behind_proxy")
        );
    }

    private Node(PteroAdminAPI api, int id,
                int locationId, int memory,
                int memoryOverAllocation, int disk,
                int diskOverAllocation, int uploadSize,
                int daemonListen, int daemonSftp,
                String name, String description,
                String fqdn, String scheme,
                String daemonBase, boolean isPublic,
                boolean behindProxy) {
        this.api = api;
        this.id = id;
        this.locationId = locationId;
        this.memory = memory;
        this.memoryOverAllocation = memoryOverAllocation;
        this.disk = disk;
        this.diskOverAllocation = diskOverAllocation;
        this.uploadSize = uploadSize;
        this.daemonListen = daemonListen;
        this.daemonSftp = daemonSftp;
        this.name = name;
        this.description = description;
        this.fqdn = fqdn;
        this.scheme = scheme;
        this.daemonBase = daemonBase;
        this.isPublic = isPublic;
        this.behindProxy = behindProxy;
    }

    public NodeUpdateAction edit() {
        return new NodeUpdateAction(api, this);
    }

    public Location getLocation() {
        return api.getLocationsController().getLocation(getLocationId());
    }

    public int getId() {
        return id;
    }

    public int getLocationId() {
        return locationId;
    }

    public int getMemory() {
        return memory;
    }

    public int getMemoryOverAllocation() {
        return memoryOverAllocation;
    }

    public int getDisk() {
        return disk;
    }

    public int getDiskOverAllocation() {
        return diskOverAllocation;
    }

    public int getUploadSize() {
        return uploadSize;
    }

    public int getDaemonListen() {
        return daemonListen;
    }

    public int getDaemonSftp() {
        return daemonSftp;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFqdn() {
        return fqdn;
    }

    public String getScheme() {
        return scheme;
    }

    public String getDaemonBase() {
        return daemonBase;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public boolean isBehindProxy() {
        return behindProxy;
    }
}
