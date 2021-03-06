package com.dev.projectta.utils.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    //user
    @FormUrlEncoded
    @POST("regUser.php")
    Call<ResponseBody> registerUser(@Field("nobp") String nobp,
                                    @Field("password") String password,
                                    @Field("nama") String name);

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginUser(@Field("nobp") String nobp,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("userData.php")
    Call<ResponseBody> userData(@Field("nobp") String nobp,
                                @Field("token") String token);

    @FormUrlEncoded
    @POST("userStatus.php")
    Call<ResponseBody> getUserStatus(@Field("nobp") String nobp,
                                     @Field("token") String token);

    //candidate
    @GET("getAllCandidate.php")
    Call<ResponseBody> getAllCandidate();

    @GET("getCandidateById.php")
    Call<ResponseBody> getCandidateById(@Query("id") String id);

    //vote
    @FormUrlEncoded
    @POST("addVote.php")
    Call<ResponseBody> vote(@Field("nobp_voter") String nobp_voter,
                            @Field("nobp_candidate") String nobp_candidate,
                            @Field("token") String token);

    //result
    @GET("getResultVote.php")
    Call<ResponseBody> getResultVote();

}
