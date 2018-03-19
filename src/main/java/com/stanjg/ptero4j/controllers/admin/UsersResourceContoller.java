package com.stanjg.ptero4j.controllers.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.entities.panel.admin.User;
import org.json.JSONObject;

import java.util.List;

public class UsersResourceContoller extends ResourceController<User> {

    public UsersResourceContoller(PteroAdminAPI api, String baseURL, String key) {
        super(api, baseURL, key, "users");
    }

    public List<User> getAllUsers() {
        return super.getAllResources();
    }

    public List<User> getUsers(String search) {
        return super.getResourcesWithQuery(search);
    }

    public User getUser(int id) {
        return super.getResource(id);
    }

    public List<User> getUserPage(int page) {
        return super.getResourcesPage(page);
    }

    public List<Server> getServersForUser(int userId) {
        return getAdminAPI().getServersController().getServersForUser(userId);
    }

    @Override
    protected User getNewInstance(JSONObject json) {
        return new User(getAdminAPI(), json);
    }

}

