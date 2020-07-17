package com.dev.projectta.utils.apihelper;

public class UtilsApi {
    public static final String BASE_URL = "127.0.0.1/pemilu/api/";

    public static ApiInterface getApiService(){
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
