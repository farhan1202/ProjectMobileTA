package com.dev.projectta.utils.apihelper;

public class UtilsApi {
    public static final String BASE_URL = "http://192.168.43.120/pemilu/api/";

    public static ApiInterface getApiService(){
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
