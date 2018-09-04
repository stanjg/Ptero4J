package com.stanjg.ptero4j.actions.admin.locations;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.panel.admin.Location;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class LocationUpdateAction implements PteroAction<Location> {

    private PteroAdminAPI api;
    private Location location;
    private JSONObject json;

    public LocationUpdateAction(PteroAdminAPI api, int id) {
        this(
                api,
                api.getLocationsController().getLocation(id)
        );
    }

    public LocationUpdateAction(PteroAdminAPI api, Location location) {
        this.api = api;
        this.json = new JSONObject();

        json.put("long", location.getLongName());
        json.put("short", location.getShortName());
    }

    public LocationUpdateAction setLongName(String longName) {
        json.put("long", longName);
        return this;
    }

    public LocationUpdateAction setShortName(String shortName) {
        json.put("short", shortName);
        return this;
    }

    @Override
    public Location execute() {
        return api.getLocationsController().executeAction(this);
    }

    @Override
    public JSONObject getAsJSON() {
        return json;
    }

    @Override
    public String getEndpoint() {
        return "/locations/" + location.getId();
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.PATCH;
    }
}
