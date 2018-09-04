package com.stanjg.ptero4j.actions.admin.locations;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.panel.admin.Location;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class LocationCreateAction implements PteroAction<Location> {

    private JSONObject json;
    private PteroAdminAPI api;

    public LocationCreateAction(PteroAdminAPI api) {
        this.api = api;
        this.json = new JSONObject();
    }

    public LocationCreateAction setShortName(String shortName) {
        json.put("short", shortName);
        return this;
    }

    public LocationCreateAction setLongName(String longName) {
        json.put("long", longName);
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
        return "/locations";
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.POST;
    }
}
