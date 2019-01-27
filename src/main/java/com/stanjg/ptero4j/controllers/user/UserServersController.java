package com.stanjg.ptero4j.controllers.user;

import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.controllers.Controller;
import com.stanjg.ptero4j.entities.objects.server.PowerState;
import com.stanjg.ptero4j.entities.objects.server.ServerUsage;
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

	public PowerState getPowerState(String serverID) {
		try {
			Response response = getUserAPI().getServersController().makeApiCall("/servers/"+serverID+"/utilization", HTTPMethod.GET);
            JSONObject json = new JSONObject(response.body().string());
            if(json.has("attributes")) json = json.getJSONObject("attributes");
            else return PowerState.ERROR;
            if(!json.has("state")) return PowerState.ERROR;
            switch(json.getString("state")) {
            case "on":
            	return PowerState.ON;
            case "off":
            	return PowerState.OFF;
            case "starting":
            	return PowerState.STARTING;
            case "stopping":
            	return PowerState.STOPPING;
            }
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return PowerState.ERROR;
	}

	public ServerUsage getServerUsage(String id) {
		try {
	    	Response response = getUserAPI().getServersController().makeApiCall("/servers/"+id+"/utilization", HTTPMethod.GET);
	        JSONObject json = new JSONObject(response.body().string());
	        if(json.has("attributes")) {
	        	json = json.getJSONObject("attributes");
	        	if(json.has("memory")&&json.has("disk")&&json.has("cpu")) return new ServerUsage( Math.round((json.getJSONObject("cpu").getFloat("current")/json.getJSONObject("cpu").getFloat("limit"))*100), json.getJSONObject("memory").getInt("current"), json.getJSONObject("disk").getInt("current"));
	        	else return null;
	        }else {
	        	System.err.println(json);
	        	return null;
	        }
	    	}catch (Exception e) {
	    		return null;
			}
	}

}
