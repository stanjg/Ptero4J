package com.stanjg.ptero4j.entities.objects.server;

import org.json.JSONObject;

public class FeatureLimits {

    private int maxDatabases, maxAllocations;

    public FeatureLimits(JSONObject json) {
        this(
                json.isNull("databases") ? 0 : json.getInt("databases"),
                json.isNull("allocations") ? 0 : json.getInt("allocations")
        );
    }

    private FeatureLimits(int maxDatabases, int maxAllocations) {
        this.maxDatabases = maxDatabases;
        this.maxAllocations = maxAllocations;
    }

    public int getMaxDatabases() {
        return maxDatabases;
    }

    public int getMaxAllocations() {
        return maxAllocations;
    }
}
