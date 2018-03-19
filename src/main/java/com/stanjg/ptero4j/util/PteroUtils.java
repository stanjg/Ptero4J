package com.stanjg.ptero4j.util;

import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class PteroUtils {

    public static void log(Object o) {
        System.out.println(o);
    }

    public static void logRequestError(Response response) throws IOException {
        System.out.println(new JSONObject(response.body().string()).toString(4));
        System.out.println("An error occured while requesting "+response.request().method()+" "+response.request().url()+" CODE: "+response.code());
    }

}
