package com.stanjg.ptero4j;

import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpMethod;

import java.io.IOException;

public class PteroAPI {

    private OkHttpClient client;
    private String baseURL, key;
    private boolean admin;

    public PteroAPI(String baseURL, String key, boolean admin) {
        this.baseURL = baseURL.endsWith("/") ? baseURL  + "api/application" : baseURL + "/api/application";
        this.key = "Bearer " + key;
        this.admin = admin;
        this.client = new OkHttpClient();

        try {
            testConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testConnection() throws IOException {
        Response response = makeApiCall(admin ? "/users" : "/", HTTPMethod.GET);


        switch (response.code()) {

            case 200:
                PteroUtils.log("Ptero4J loaded! Successfully made contact with the panel.");
                break;
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
