package com.stanjg.ptero4j.actions.admin.users;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.panel.admin.User;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class UserCreateAction implements PteroAction<User> {

    private JSONObject json;
    private PteroAdminAPI api;

    public UserCreateAction(PteroAdminAPI api) {
        this.api = api;
        this.json = new JSONObject();

        json.put("root_admin", false);
    }

    public UserCreateAction setExternalId(int id) {
        json.put("external_id", id);
        return this;
    }

    public UserCreateAction setEmail(String email) {
        json.put("email", email);
        return this;
    }

    public UserCreateAction setUsername(String username) {
        json.put("username", username);
        return this;
    }

    public UserCreateAction setIsAdmin(boolean isAdmin) {
        json.put("root_admin", isAdmin);
        return this;
    }

    public UserCreateAction setFirstName(String firstName) {
        json.put("first_name", firstName);
        return this;
    }

    public UserCreateAction setLastName(String lastName) {
        json.put("last_name", lastName);
        return this;
    }

    public UserCreateAction setLanguage(String language) {
        json.put("language", language);
        return this;
    }

    public UserCreateAction setPassword(String password) {
        json.put("password", password);
        return this;
    }

    @Override
    public User execute() {
        return api.getUsersController().executeAction(this);
    }

    @Override
    public JSONObject getAsJSON() {
        return json;
    }

    @Override
    public String getEndpoint() {
        return "/users";
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.POST;
    }
}
