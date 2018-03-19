package com.stanjg.ptero4j.actions.admin.users;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.entities.panel.admin.User;
import com.stanjg.ptero4j.util.HTTPMethod;
import org.json.JSONObject;

public class UserUpdateAction implements PteroAction<User> {

    private PteroAdminAPI api;
    private User user;
    private JSONObject json;

    public UserUpdateAction(PteroAdminAPI api, int id) {
        this(
                api,
                api.getUsersController().getUser(id)
        );
    }

    public UserUpdateAction(PteroAdminAPI api, User user) {
        this.api = api;
        this.user = user;
        this.json = new JSONObject();

        json.put("email", user.getEmail());
        json.put("username", user.getUsername());
        json.put("first_name", user.getFirstName());
        json.put("last_name", user.getLastName());
    }

    public UserUpdateAction setEmail(String email) {
        json.put("email", email);
        return this;
    }

    public UserUpdateAction setUsername(String username) {
        json.put("username", username);
        return this;
    }

    public UserUpdateAction setFirstName(String firstName) {
        json.put("first_name", firstName);
        return this;
    }

    public UserUpdateAction setLastName(String lastName) {
        json.put("last_name", lastName);
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
        return "/users/"+user.getId();
    }

    @Override
    public HTTPMethod getMethod() {
        return HTTPMethod.PATCH;
    }

}
