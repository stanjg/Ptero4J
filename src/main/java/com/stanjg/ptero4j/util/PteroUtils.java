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
        System.out.println(getErrorMessage(response));
    }

    private static String getErrorMessage(Response response) {

        switch (response.code()) {
            case 400:
            case 405:
                return "An invalid request was made by the library, please create an issue on github.\n" + response.request().method() + " " + response.request().url();
            case 403:
                return "The given key does not have permission for this action!\n" + response.request().method() + " " + response.request().url();
            case 404:
                return "An invalid endpoint was requested by the library, please create an issue on github.\n" + response.request().method() + " " + response.request().url();
            case 429:
                return "You have been rate limited!";
            case 500:
                return "An error occurred on the panel's side, please check panel logs.\n" + response.request().method() + " " + response.request().url();
            default:
                return "An error occurred while making a request to the panel, if the issue persists please create an issue on github.\n" + response.code() + " " + response.request().method() + " " + response.request().url();
        }

    }

}
