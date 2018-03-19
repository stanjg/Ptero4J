package com.stanjg.ptero4j.controllers.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.admin.users.UserCreateAction;
import com.stanjg.ptero4j.actions.admin.users.UserUpdateAction;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.entities.panel.admin.User;
import org.json.JSONObject;

import java.util.List;

public class UsersController extends ResourceController<User> {

    public UsersController(PteroAdminAPI api, String baseURL, String key) {
        super(api, baseURL, key, "users");
    }

    public UserCreateAction createNew() {
        return new UserCreateAction(getAdminAPI());
    }

    public UserUpdateAction editUser(int id) {
        return new UserUpdateAction(getAdminAPI(), id);
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

    public boolean deleteUser(int id) {
        return super.delete(id);
    }

    @Override
    protected User getNewInstance(JSONObject json) {
        return new User(getAdminAPI(), json);
    }

}

