package com.stanjg.ptero4j.controllers;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public abstract class Controller {

    private PteroAdminAPI adminAPI;
    private PteroUserAPI userAPI;

    private OkHttpClient client;
    private String baseURL, key;

    public Controller(PteroAdminAPI api, String baseURL, String key) {
        this.adminAPI = api;
        this.baseURL = baseURL;
        this.key = key;

        this.client = new OkHttpClient();
    }

    public Controller(PteroUserAPI api, String baseURL, String key) {
        this.userAPI = api;
        this.baseURL = baseURL;
        this.key = key;

        this.client = new OkHttpClient();
    }

    protected Response makeApiCall(String endpoint, HTTPMethod method) throws IOException {
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

    protected PteroAdminAPI getAdminAPI() {
        return adminAPI;
    }

    protected PteroUserAPI getUserAPI() {
        return userAPI;
    }
}
