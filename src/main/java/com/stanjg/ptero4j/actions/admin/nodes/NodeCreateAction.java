package com.stanjg.ptero4j.actions.admin.nodes;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.panel.admin.Node;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class NodeCreateAction implements PteroAction<Node> {

    private PteroAdminAPI api;
    private JSONObject json;

    public NodeCreateAction(PteroAdminAPI api) {
        this.api = api;

        // Setting defaults
        setPublic(true);
        setDaemonListenPort(8080);
        setDaemonSFTPPort(2022);
        setDaemonBaseDirectory("/srv/daemon-data");
        setDiskOverAllocation(0);
        setMemoryOverAllocation(0);
        setBehindProxy(false);
    }

    public NodeCreateAction setName(String name) {
        json.put("name", name);
        return this;
    }

    public NodeCreateAction setPublic(boolean isPublic) {
        json.put("public", isPublic);
        return this;
    }

    public NodeCreateAction setLocationId(int id) {
        json.put("location_id", id);
        return this;
    }

    public NodeCreateAction setFQDN(String fqdn) {
        json.put("fqdn", fqdn);
        return this;
    }

    public NodeCreateAction setScheme(String scheme) {
        // Starting on client side validation
        if (scheme != "http" && scheme != "https")
            throw new IllegalArgumentException("Node scheme should either be \"http\" or \"https\"!");

        json.put("scheme", scheme);
        return this;
    }

    public NodeCreateAction setBehindProxy(boolean behindProxy) {
        json.put("behind_proxy", behindProxy);
        return this;
    }

    public NodeCreateAction setMemory(int memory) {
        json.put("memory", memory);
        return this;
    }

    public NodeCreateAction setMemoryOverAllocation(int overAllocation) {
        json.put("memory_overallocate", overAllocation);
        return this;
    }

    public NodeCreateAction setDisk(int disk) {
        json.put("disk", disk);
        return this;
    }

    public NodeCreateAction setDiskOverAllocation(int overAllocation) {
        json.put("disk_overallocate", overAllocation);
        return this;
    }

    public NodeCreateAction setUploadSize(int uploadSize) {
        json.put("upload_size", uploadSize);
        return this;
    }

    public NodeCreateAction setDaemonListenPort(int daemonListenPort) {
        json.put("daemonListen", daemonListenPort);
        return this;
    }

    public NodeCreateAction setDaemonSFTPPort(int daemonSFTPPort) {
        json.put("daemonSFTP", daemonSFTPPort);
        return this;
    }

    public NodeCreateAction setDaemonBaseDirectory(String directory) {
        json.put("daemonBase", directory);
        return this;
    }

    @Override
    public Node execute() {
        return api.getNodesController().executeAction(this);
    }

    @Override
    public JSONObject getAsJSON() {
        return this.json;
    }

    @Override
    public String getEndpoint() {
        return "/nodes";
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.POST;
    }
}
