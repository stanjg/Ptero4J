package com.stanjg.ptero4j.controllers;

import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public abstract class Controller {

    private OkHttpClient client;
    private String baseURL, key;

    public Controller(String baseURL, String key) {
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
}
