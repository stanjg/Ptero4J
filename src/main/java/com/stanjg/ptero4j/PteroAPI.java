package com.stanjg.ptero4j;

import com.stanjg.ptero4j.entities.User;
import com.stanjg.ptero4j.exceptions.NoPermissionException;
import com.stanjg.ptero4j.exceptions.NotFoundException;
import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PteroAPI {

    private OkHttpClient client;
    private String baseURL, key;

    public PteroAPI(String baseURL, String key) {
        this.baseURL = baseURL.endsWith("/") ? baseURL  + "api/application" : baseURL + "/api/application";
        this.key = "Bearer " + key;
        this.client = new OkHttpClient();

        try {
            testConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PteroUtils.log(getUsers(4534).size());
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
                return null;
            }

            List<User> users = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("/users?page="+i, HTTPMethod.GET).body().string());

                JSONArray arr = json.getJSONArray("data");
                for (int j = 0; j < arr.length(); j ++) {
                    JSONObject juser = arr.getJSONObject(j);

                    users.add(new User(juser.getJSONObject("attributes")));
                }
            }

            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUser(int id) {

        try {
            Response response = makeApiCall("/users/"+id, HTTPMethod.GET);

            if (response.code() != 200) {
                if (response.code() == 404)
                    throw new NotFoundException("A user with id " + id + " was not found!");

                return null;
            }

            JSONObject json = new JSONObject(response.body().string()).getJSONObject("attributes");

            return new User(json);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void testConnection() throws IOException {
        Response response = makeApiCall("/users", HTTPMethod.GET);


        switch (response.code()) {

            case 200:
                PteroUtils.log("Ptero4J loaded! Successfully made contact with the panel.");
                return;
            case 401:
                PteroUtils.log("Ptero4J failed to load! Unable to authenticated. Your key might be invalid.");
                break;
            case 403:
                PteroUtils.log("Mixed: not authorized to access /users, no permission.");
                return;
            case 404:
                PteroUtils.log("Ptero4J failed to load! An invalid URL was provided.");
                break;
            case 500:
                PteroUtils.log("An error occurred on the panel side, please check panel logs/");
                break;
        }

        System.exit(1);
    }

    private Response makeApiCall(String endpoint, HTTPMethod method) throws IOException {
        Response response = null;

        try {

            switch (method) {

                case GET:
                    Request request = new Request.Builder()
                            .url(baseURL + endpoint)
                            .addHeader("Authorization", key)
                            .addHeader("Accept", "application/vnd.pterodactyl.v1+json")
                            .addHeader("User-Agent", "Ptero4J/v0.1")
                            .build();

                    response = client.newCall(request).execute();
                    return response;

            }

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        throw new RuntimeException("Invalid Method");
    }

}
