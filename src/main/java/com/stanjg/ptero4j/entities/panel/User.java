package com.stanjg.ptero4j.entities.panel;

import com.stanjg.ptero4j.PteroAPI;
import org.json.JSONObject;

import java.util.List;

public class User {

    private int id, externalId;
    private String uuid, username, email, firstName, lastName, language;
    private boolean root_admin, totpEnabled;

    private User(int id, int externalId, String uuid, String username, String email, String firstName, String lastName, String language, boolean root_admin, boolean totpEnabled) {
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

    public User(JSONObject json) {
        this(
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

    public List<Server> getServers() {
        return PteroAPI.getInstance().getServersController().getServersForUser(this.id);
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
