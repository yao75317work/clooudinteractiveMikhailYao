package com.kjyao.clooudinteractive.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CloudInteractiveService {
    String END_POINT = " /photos";

    @GET(END_POINT)
    Call<ResponseBody> getData();
}
