package com.test.newsapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.test.newsapplication.CenterZoomLayoutManager;
import com.test.newsapplication.MyRecyclerAdapter;
import com.test.newsapplication.R;
import com.test.newsapplication.model.Article;
import com.test.newsapplication.model.ResponseModel;
import com.test.newsapplication.retrofit.IRetrofit;
import com.test.newsapplication.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private   MyRecyclerAdapter adapter;
    CenterZoomLayoutManager layoutManager;
    Boolean isScrolling = false;
    ProgressBar progressBar;
    //Total number of articles to be appeared
    private static int TOTAL_ITEMS= 20;
    int currentItems, totalItems, scrolledOutItems;
    private MainActivityViewModel mainActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.news_recycle);
        progressBar= findViewById(R.id.progressBar);


        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init(getApplicationContext());
        mainActivityViewModel.getAllNews().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                adapter.notifyDataSetChanged();
            }
        });

        mainActivityViewModel.getProgressValue().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else progressBar.setVisibility(View.GONE);
            }
        });


        adapter=new MyRecyclerAdapter(getApplicationContext(),mainActivityViewModel.getAllNews().getValue());
        layoutManager= new CenterZoomLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    Log.i("newStateVal",String.valueOf(newState));

                    isScrolling=true;
                    Log.i("isScrolling", isScrolling.toString());
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems= layoutManager.getChildCount();
                Log.i("CHILDCOUNT",String.valueOf(currentItems));
                totalItems= layoutManager.getItemCount();
                Log.i("TOTAL_iTEMS",String.valueOf(totalItems));
                scrolledOutItems=layoutManager.findFirstVisibleItemPosition();
                Log.i("scrolledOut",String.valueOf(scrolledOutItems));

                if(isScrolling && currentItems+scrolledOutItems==totalItems){
                    isScrolling =false;
                    Log.i("condition1", String.valueOf(TOTAL_ITEMS));
                    Log.i("condition2", String.valueOf(totalItems));
                   // if (totalItems < TOTAL_ITEMS) {
                        Log.i("Invoked", String.valueOf(TOTAL_ITEMS));
                        Log.i("Invoked1",String.valueOf(totalItems));
                        mainActivityViewModel.updateNews(totalItems/5+1,getApplicationContext());

                //    }
                }
            }
        });
    }

}
