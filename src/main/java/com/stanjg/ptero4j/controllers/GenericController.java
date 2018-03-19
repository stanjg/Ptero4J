package com.stanjg.ptero4j.controllers;

import com.stanjg.ptero4j.PteroAdminAPI;
import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.actions.PteroAction;
import com.stanjg.ptero4j.actions.PteroVoidAction;
import com.stanjg.ptero4j.controllers.Controller;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.util.PteroUtils;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class GenericController extends Controller {

    public GenericController(PteroAdminAPI api, String baseURL, String key) {
        super(api, baseURL, key);
    }

    public GenericController(PteroUserAPI api, String baseURL, String key) {
        super(api, baseURL, key);
    }

    public int executeAction(PteroVoidAction action) {
        JSONObject data = action.getAsJSON();

        try {

            Response response = makeApiCall(action.getEndpoint(), action.getMethod(), data);
            if (response.code() >= 400) {
                PteroUtils.logRequestError(response);
            }

            return response.code();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
