package com.stanjg.ptero4j.entities.panel.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.admin.locations.LocationCreateAction;
import com.stanjg.ptero4j.actions.admin.locations.LocationUpdateAction;
import org.json.JSONObject;

import java.util.List;

public class Location {

    private PteroAdminAPI api;

    private int id;
    private String shortName, longName;

    public Location(PteroAdminAPI api, JSONObject json) {
        this.api = api;
        this.id = json.getInt("id");
        this.shortName = json.getString("short");
        this.longName = json.getString("long");
    }

    public List<Server> getServers() {
        return api.getServersController().getServersForLocation(this.id);
    }

    public LocationUpdateAction edit() {
        return new LocationUpdateAction(api, this);
    }

    public boolean delete() {
        return api.getLocationsController().deleteLocation(this.id);
    }

    public int getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }
}
