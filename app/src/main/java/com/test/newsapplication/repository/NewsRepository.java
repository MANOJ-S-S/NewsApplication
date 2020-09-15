package com.test.newsapplication.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.newsapplication.model.Article;
import com.test.newsapplication.model.ResponseModel;
import com.test.newsapplication.retrofit.IRetrofit;
import com.test.newsapplication.view.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRepository {

    private static NewsRepository instance;
    private ArrayList<Article> newsDataset =new ArrayList<>();
    MutableLiveData<Boolean>  progressValue= new MutableLiveData<>();
    Context context;
    public IRetrofit retrofitAPI;

    public NewsRepository(Context context) {
        this.context = context;
    }

    public static NewsRepository getInstance(Context context){
        if(instance==null){
            instance= new NewsRepository(context);
        }
        return instance;
    }


    public LiveData<Boolean> getProgressValue() {
        return progressValue;
    }

    public MutableLiveData<ArrayList<Article>> getNewsData(int page, int pageSize){

        progressValue.setValue(true);
        getDatafromAPi(page, pageSize);
        if(newsDataset!=null){
            MutableLiveData<ArrayList<Article>>  data= new MutableLiveData<>();
            data.setValue(newsDataset);
            return data;
        }

        else
            return new MutableLiveData();

    }

    private void getDatafromAPi(int page, int pageSize) {

        retrofitAPI =  IRetrofit.Factory.getInstance(context);

        Map<String,String> mapParam = new HashMap<>();
     //   mapParam.put("q","bitcoin");
        mapParam.put("category","business");
        mapParam.put("country","us");
        mapParam.put("apiKey","72193b8a236b49cfbd025801adb4cab6");
        mapParam.put("page",String.valueOf(page));
        mapParam.put("pageSize",String.valueOf(pageSize));


        retrofitAPI.responseCall(mapParam).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                if(response.body()!=null&&response.isSuccessful()){

                    progressValue.setValue(false);

                    newsDataset.addAll(response.body().getArticles());

                     //   MainActivity.adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

                progressValue.setValue(false);


            }
        });



    }


}
