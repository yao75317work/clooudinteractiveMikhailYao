package com.kjyao.clooudinteractive.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kjyao.clooudinteractive.api.CloudInteractiveService;
import com.kjyao.clooudinteractive.api.RetrofitFactory;
import com.kjyao.clooudinteractive.bean.RespondData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataViewModel extends AndroidViewModel {
    protected MutableLiveData<ArrayList<RespondData>> mDataResponse;

    public DataViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<RespondData>> getDataResponse() {
        if (mDataResponse == null) {
            mDataResponse = new MutableLiveData<>();
        }
        return mDataResponse;
    }

    public void getData() {
        CloudInteractiveService service = RetrofitFactory.createRetrofit(CloudInteractiveService.class);

        Call<ResponseBody> call = service.getData();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                final ResponseBody body = response.body();
                ArrayList<RespondData> result = new ArrayList<>();
                if (body != null) {
                    try {
                        RespondData data;
                        JSONArray jsonArray = new JSONArray(body.string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            data = new RespondData(jsonObject);
                            result.add(data);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                mDataResponse.setValue(result);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mDataResponse.setValue(null);
            }
        });
    }
}
