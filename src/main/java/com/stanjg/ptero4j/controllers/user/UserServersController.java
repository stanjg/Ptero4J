package com.stanjg.ptero4j.controllers.user;

import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.controllers.Controller;
import com.stanjg.ptero4j.entities.panel.user.UserServer;
import com.stanjg.ptero4j.util.HTTPMethod;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServersController extends Controller {

    public UserServersController(PteroUserAPI api, String baseURL, String key) {
        super(api, baseURL, key);
    }

    public UserServer getServer(String id) {

        try {

            Response response = makeApiCall("/servers/"+id, HTTPMethod.GET);
            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            JSONObject json = new JSONObject(response.body().string());

            return new UserServer(getUserAPI(), json.getJSONObject("attributes"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * Required to get power states
     */
    @Override
    public Response makeApiCall(String endpoint, HTTPMethod method) throws IOException {
    	// TODO Auto-generated method stub
    	return super.makeApiCall(endpoint, method);
    }
    public List<UserServer> getServers() {

        try {

            Response response = makeApiCall("", HTTPMethod.GET);
            if (response.code() < 200 || response.code() >= 300) {
                PteroUtils.logRequestError(response);
                return null;
            }

            List<UserServer> servers = new ArrayList<>();

            JSONObject json = new JSONObject(response.body().string());
            int pages = json.getJSONObject("meta").getJSONObject("pagination").getInt("total_pages");
            for (int i = 1; i <= pages; i++) {
                if (i != 1)
                    json = new JSONObject(makeApiCall("?page="+i, HTTPMethod.GET).body().string());

                addPageOfServersToList(json, servers);
            }

            return servers;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addPageOfServersToList(JSONObject page, List<UserServer> list) {
        JSONArray arr = page.getJSONArray("data");
        for (int j = 0; j < arr.length(); j ++) {
            JSONObject jserver = arr.getJSONObject(j);

            list.add(new UserServer(getUserAPI(), jserver.getJSONObject("attributes")));
        }
    }

}
