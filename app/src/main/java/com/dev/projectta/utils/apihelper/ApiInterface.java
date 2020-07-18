package com.dev.projectta.utils.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    //user
    @FormUrlEncoded
    @POST("regUser.php")
    Call<ResponseBody> registerUser(@Field("nobp") String nobp,
                                    @Field("password") String password,
                                    @Field("nama") String name);
}
