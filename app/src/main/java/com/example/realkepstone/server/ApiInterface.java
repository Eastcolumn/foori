package com.example.realkepstone.server;

import com.example.realkepstone.data.FoodAfter;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    // base_url + "api/login" 으로 POST 통신
    @POST("users/login/")
    Call<ResLoginData> requestPostLogin(@Body ReqLoginData reqLoginData );   // @Body : request 파라미터


    @Multipart
    @POST("imageupload/")
    Call<FoodAfter> postImage(@Part MultipartBody.Part image, @Part("name") RequestBody name);

    @POST("users/signup/")
    Call<JoinData> requestJoin(@Body JoinData joinData );   // @Body : request 파라미터

    @POST("users/help/id/")
    Call<FindIdData> requestId(@Body FindIdData findIdData );   // @Body : request 파라미터

    @POST("users/help/pw/")
    Call<FindpwData> requestPw(@Body FindpwData findpwData );   // @Body : request 파라미터


    @POST("users/taste/")
    Call<TagData> requestTag(@Body TagData tagData );   // @Body : request 파라미터


}