package com.stanjg.ptero4j.entities.panel.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.admin.users.UserCreateAction;
import com.stanjg.ptero4j.actions.admin.users.UserUpdateAction;
import org.json.JSONObject;

import java.util.List;

public class User {

    private PteroAdminAPI api;
    private int id, externalId;
    private String uuid, username, email, firstName, lastName, language;
    private boolean root_admin, totpEnabled;

    private User(PteroAdminAPI api, int id, int externalId, String uuid, String username, String email, String firstName, String lastName, String language, boolean root_admin, boolean totpEnabled) {
        this.api = api;
        this.id = id;
        this.externalId = externalId;
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
        this.root_admin = root_admin;
        this.totpEnabled = totpEnabled;
    }

    public User(PteroAdminAPI api, JSONObject json) {
        this(
                api,
                json.getInt("id"),
                json.isNull("external_id") ? -1 : json.getInt("external_id"),
                json.getString("uuid"),
                json.getString("username"),
                json.getString("email"),
                json.getString("first_name"),
                json.getString("last_name"),
                json.getString("language"),
                json.getBoolean("root_admin"),
                json.getBoolean("2fa")
        );
    }

    public static UserCreateAction create(PteroAdminAPI api) {
        return new UserCreateAction(api);
    }

    public UserUpdateAction edit() {
        return new UserUpdateAction(api, this);
    }

    public List<Server> getServers() {
        return api.getServersController().getServersForUser(this.id);
    }

    public boolean delete() {
        return api.getUsersController().deleteUser(this.id);
    }

    public int getId() {
        return id;
    }

    public int getExternalId() {
        return externalId;
    }

    public String getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isAdmin() {
        return root_admin;
    }

    public boolean hasTotpEnabled() {
        return totpEnabled;
    }

}
