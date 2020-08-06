package com.example.news_app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.Retrofit.*;

public class Client {
    private static  final String BASE_URL="https://newsapi.org/v2/";
    private static Client client;
    private    static Retrofit retrofit;
    private  Client()
    {
        retrofit=new Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized Client getInstance()
    {
        if (client==null)
        {
            client=new Client();
        }
        return client;
    }
    public Interface getApi()
    {
        return retrofit.create(Interface.class);
    }
}
