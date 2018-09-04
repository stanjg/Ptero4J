package com.stanjg.ptero4j.controllers.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.entities.panel.admin.Location;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import org.json.JSONObject;

import java.util.List;

public class LocationsController extends ResourceController<Location> {

    public LocationsController(PteroAdminAPI api, String baseURL, String key) {
        super(api, baseURL, key, "locations");
    }

    public List<Location> getAllLocations() {
        return super.getAllResources();
    }

    public List<Location> getLocations(String search) {
        return super.getResourcesWithQuery(search);
    }

    public Location getLocation(int id) {
        return super.getResource(id);
    }

    public List<Location> getLocationPage(int page) {
        return super.getResourcesPage(page);
    }

    public boolean deleteLocation(int id) {
        return super.delete(id);
    }

    public List<Server> getServersForLocation(int id) {
        return getAdminAPI().getServersController().getServersForLocation(id);
    }

    @Override
    protected Location getNewInstance(JSONObject json) {
        return new Location(getAdminAPI(), json);
    }
}
