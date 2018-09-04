package com.stanjg.ptero4j.controllers.admin;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.controllers.Controller;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ResourceController<T> extends Controller {

    private String resourceName;

    protected ResourceController(PteroAdminAPI api, String baseURL, String key, String resourceName) {
        super(api, baseURL, key);
        this.resourceName = resourceName;
    }

//    protected List<T> getResourcesCustom(String endpoint, JSONModifier jsonModifier, boolean paginated) {
//
//        try {
//            Response response = makeApiCall(endpoint, HTTPMethod.GET);
//
//            List<T> resources = new ArrayList<>();
//
//            JSONObject json = new JSONObject(response.body().string());
//            if (paginated) {
//                int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
//                for (int i = 1; i <= pages; i++) {
//                    if (i != 1)
//                        json = new JSONObject(makeApiCall("/" + resourceName + "?page=" + i, HTTPMethod.GET).body().string());
//
//                    addPageToList(json, resources, jsonModifier);
//                }
//            } else {
//                addPageToList(json, resources);
//            }
//
//            return resources;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public T executeAction(PteroAction<T> action) {

        JSONObject data = action.getAsJSON();

        try {

            Response response = makeApiCall(action.getEndpoint(), action.getMethod(), data);
            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            JSONObject json = new JSONObject(response.body().string()).getJSONObject("attributes");

            return getNewInstance(json);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    protected List<T> getResourcesWithParams(String queryParams) {

        try {
            Response response = makeApiCall("/"+resourceName+"?"+queryParams, HTTPMethod.GET);
            System.out.println(response.request().url());
            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<T> resources = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("/"+resourceName+"?page="+i+queryParams, HTTPMethod.GET).body().string());

                addPageToList(json, resources);
            }

            return resources;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected T getResource(int id) {

        try {
            Response response = makeApiCall("/"+resourceName+"/"+id, HTTPMethod.GET);
            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            T resource = getNewInstance(new JSONObject(response.body().string()).getJSONObject("attributes"));

            return resource;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected List<T> getResourcesWithQuery(String query) {

        try {
            Response response = makeApiCall("/"+resourceName+"?search="+query, HTTPMethod.GET);
            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<T> resources = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("/"+resourceName+"?search="+query+"?page="+i, HTTPMethod.GET).body().string());

                addPageToList(json, resources);
            }

            return resources;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    protected List<T> getResourcesPage(int page) {

        try {

            Response response = makeApiCall("/"+resourceName+"?page="+page, HTTPMethod.GET);

            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            JSONObject json = new JSONObject(response.body().string());
            List<T> resources = new ArrayList<>();

            addPageToList(json, resources);

            return resources;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    protected List<T> getAllResources() {

        try {

            Response response = makeApiCall("/"+resourceName, HTTPMethod.GET);

            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<T> resources = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("/"+resourceName+"?page="+i, HTTPMethod.GET).body().string());

                addPageToList(json, resources);
            }

            return resources;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    protected boolean delete(int id) {

        try {
            Response response = makeApiCall("/"+resourceName+"/"+id, HTTPMethod.DELETE);
            boolean success = response.code() == 204;

            if (!success) {
                PteroUtils.logRequestError(response);
                return success;
            }

            return success;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    protected List<T> getResourcesFor(String otherResource, int id) {
        try {
            Response response = makeApiCall("/"+otherResource+"/"+id+"?include="+this.resourceName, HTTPMethod.GET);
            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<T> resources = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string())
                    .getJSONObject("attributes")
                    .getJSONObject("relationships")
                    .getJSONObject(this.resourceName);
            this.addPageToList(json, resources);

            return resources;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void addPageToList(JSONObject page, List<T> list) {
        JSONArray arr = page.getJSONArray("data");
        for (int j = 0; j < arr.length(); j ++) {
            JSONObject json = arr.getJSONObject(j).getJSONObject("attributes");

            list.add(getNewInstance(json));
        }
    }

    protected abstract T getNewInstance(JSONObject json);

}
