package com.stanjg.ptero4j.controllers;

import com.stanjg.ptero4j.entities.panel.User;
import com.stanjg.ptero4j.exceptions.NotFoundException;
import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersController extends Controller {

    public UsersController(String baseURL, String key) {
        super(baseURL, key);
    }

    public List<User> getUsers(int page) {

        try {
            Response response = makeApiCall("/users?page="+page, HTTPMethod.GET);
            if (response.code() != 200) {
                return null;
            }

            List<User> users = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            JSONArray arr = json.getJSONArray("data");
            for (int i = 0; i < arr.length(); i++) {
                users.add(new User(arr.getJSONObject(i).getJSONObject("attributes")));
            }

            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getUsers() {

        try {
            Response response = makeApiCall("/users", HTTPMethod.GET);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<User> users = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("/users?page="+i, HTTPMethod.GET).body().string());

                    addPageOfUsersToList(json, users);

//                JSONArray arr = json.getJSONArray("data");
//                for (int j = 0; j < arr.length(); j ++) {
//                    JSONObject juser = arr.getJSONObject(j);
//
//                    users.add(new User(juser.getJSONObject("attributes")));
//                }
            }

            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getUsers(String query) {

        try {
            Response response = makeApiCall("/users?search="+query, HTTPMethod.GET);
            if (response.code() != 200) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<User> users = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("/users?search="+query+"?page="+i, HTTPMethod.GET).body().string());

                addPageOfUsersToList(json, users);
            }

            return users;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUser(int id) throws NotFoundException {

        try {
            Response response = makeApiCall("/users/"+id, HTTPMethod.GET);

            if (response.code() != 200) {
                if (response.code() == 404)
                    throw new NotFoundException("A user with id " + id + " was not found!");

                PteroUtils.logRequestError(response);
                return null;
            }

            JSONObject json = new JSONObject(response.body().string()).getJSONObject("attributes");

            return new User(json);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addPageOfUsersToList(JSONObject page, List<User> list) {
        JSONArray arr = page.getJSONArray("data");
        for (int j = 0; j < arr.length(); j ++) {
            JSONObject juser = arr.getJSONObject(j);

            list.add(new User(juser.getJSONObject("attributes")));
        }
    }

}
