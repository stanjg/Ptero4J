package com.stanjg.ptero4j.entities.objects.server.creation;

import org.json.JSONObject;

public class CreationFeatureLimits {

    private int maxDatabases, maxAllocations;

    public CreationFeatureLimits() { }

    public CreationFeatureLimits(int maxDatabases, int maxAllocations) {
        this.maxDatabases = maxDatabases;
        this.maxAllocations = maxAllocations;
    }

    public JSONObject getAsJSON() {
        return new JSONObject()
                .put("databases", maxDatabases)
                .put("allocations", maxAllocations);
    }

    public int getMaxDatabases() {
        return maxDatabases;
    }

    public int getMaxAllocations() {
        return maxAllocations;
    }

    public void setMaxDatabases(int maxDatabases) {
        this.maxDatabases = maxDatabases;
    }

    public void setMaxAllocations(int maxAllocations) {
        this.maxAllocations = maxAllocations;
    }
}
