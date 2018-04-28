package com.stanjg.ptero4j.actions.admin.nodes;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.panel.admin.Node;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class NodeUpdateAction implements PteroAction<Node> {

    private PteroAdminAPI api;
    private Node node;
    private JSONObject json;

    public NodeUpdateAction(PteroAdminAPI api, int id) {
        this(
                api,
                api.getNodesController().getNode(id)
        );
    }

    public NodeUpdateAction(PteroAdminAPI api, Node node) {
        this.api = api;
        this.node = node;

        setName(node.getName());
        setDescription(node.getDescription());
        setLocationId(node.getLocationId());
        setPublic(node.isPublic());
        setFQDN(node.getFqdn());
        setBehindProxy(node.isBehindProxy());
        setMemory(node.getMemory());
        setMemoryOverallaction(node.getMemoryOverAllocation());
        setDisk(node.getDisk());
        setDiskOverallocation(node.getDiskOverAllocation());
        setDaemonBase(node.getDaemonBase());
        setDaemonSFTPPort(node.getDaemonSftp());
        setDaemonListenPort(node.getDaemonSftp());
    }

    public NodeUpdateAction setName(String name) {
        json.put("name", name);
        return this;
    }

    public NodeUpdateAction setDescription(String description) {
        json.put("description", description);
        return this;
    }

    public NodeUpdateAction setLocationId(int id) {
        json.put("location_id", id);
        return this;
    }

    public NodeUpdateAction setPublic(boolean isPublic) {
        json.put("public", isPublic);
        return this;
    }

    public NodeUpdateAction setFQDN(String fqdn) {
        json.put("fqdn", fqdn);
        return this;
    }

    public NodeUpdateAction setBehindProxy(boolean behindProxy) {
        json.put("behind_proxy", behindProxy);
        return this;
    }

    public NodeUpdateAction setMemory(int memory) {
        json.put("memory", memory);
        return this;
    }

    public NodeUpdateAction setMemoryOverallaction(int memoryOverallaction) {
        json.put("memory_overallocate", memoryOverallaction);
        return this;
    }

    public NodeUpdateAction setDisk(int disk) {
        json.put("disk", disk);
        return this;
    }

    public NodeUpdateAction setDiskOverallocation(int diskOverallocation) {
        json.put("disk_overallocate", diskOverallocation);
        return this;
    }

    public NodeUpdateAction setDaemonBase(String base) {
        json.put("daemonBase", base);
        return this;
    }

    public NodeUpdateAction setDaemonSFTPPort(int sftpPort) {
        json.put("daemonSFTP", sftpPort);
        return this;
    }

    public NodeUpdateAction setDaemonListenPort(int daemonListenPort) {
        json.put("daemonListen", daemonListenPort);
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
        return HTTPMethod.PATCH;
    }

}
