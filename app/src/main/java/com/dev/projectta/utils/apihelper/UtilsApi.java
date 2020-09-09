package com.dev.projectta.utils.apihelper;

public class UtilsApi {
//    public static final String BASE_URL = "http://192.168.43.120/pemilu/api/"; //pake jaringan lokal
//    public static final String BASE_URL1 = "http://192.168.43.120/pemilu/"; //pake jaringan lokal
//    public static final String BASE_URL = "http://evotingpnp.000webhostapp.com/pemilu/api/";  //hostingan di 000webhost,com
//    public static final String BASE_URL1 = "http://evotingpnp.000webhostapp.com/pemilu/";  //hostingan di 000webhost,com
    public static final String BASE_URL = "http://52.204.60.245/API_PEMILU/api/";  //hostingan di 000webhost,com
    public static final String BASE_URL1 = "http://52.204.60.245/API_PEMILU/";  //hostingan di 000webhost,com

    public static ApiInterface getApiService(){
        return ApiClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
