package com.kjyao.clooudinteractive.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private RetrofitFactory() {
    }

    public static <T> T createRetrofit(final Class<T> service) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(defineOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }

    public static OkHttpClient defineOkHttpClient() {
        return MyOkHttpClient.getInstance().newBuilder().build();
    }

    static class MyOkHttpClient {
        private static volatile OkHttpClient instance = null;

        static OkHttpClient getInstance() {
            if (instance == null) {
                synchronized (MyOkHttpClient.class) {
                    if (instance == null) {
                        instance = new OkHttpClient();
                    }
                }
            }
            return instance;
        }
    }
}
