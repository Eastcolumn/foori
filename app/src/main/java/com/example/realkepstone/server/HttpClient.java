package com.example.realkepstone.server;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static Retrofit retrofit;

    // Http 통신을 위한 Retrofit 객체반환
    public static Retrofit getRetrofit() {
        if( retrofit == null )
        {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS)
                    .build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl( "http://3.22.187.148/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create());  // 받아오는 Json 구조의 데이터를 객체 형태로 변환

            retrofit = builder.build();
        }
//https://http://18.220.29.13/
        return retrofit;
    }
}
