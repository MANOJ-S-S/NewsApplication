package com.test.newsapplication.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.newsapplication.model.Article;
import com.test.newsapplication.repository.NewsRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Article>> mArticleList;
    private NewsRepository newsRepository;
    private LiveData<Boolean> progressValue;


    public void init(Context context){
        newsRepository= NewsRepository.getInstance(context);
        if(mArticleList!=null){
            return;
        }
        mArticleList =newsRepository.getNewsData(1,5);
    }

    public LiveData<Boolean> getProgressValue(){
        if(progressValue==null){
            progressValue= newsRepository.getProgressValue();
            return progressValue;
        }
        else {
            return progressValue;
        }
    }

    public LiveData<ArrayList<Article>> getAllNews(){
        return mArticleList;
    }

    public void updateNews(int page, Context context){

        Toast.makeText(context, "Calling API for "+page, Toast.LENGTH_SHORT).show();
        ArrayList<Article> articles = newsRepository.getNewsData(page, 5).getValue();

        //set
        Set<Article> set = new HashSet<>(articles);
        articles.clear();
        articles.addAll(set);
        Log.i("ArticlesSize: ", String.valueOf(articles.size()));
        //List<Article> fetchedArticles = new ArrayList<>(articles.subList(5 * page - 1, 5));
        //Log.i("SizeFetched", String.valueOf(fetchedArticles.size()));
        Log.i("SizePage", String.valueOf(page));

        Log.i("Size", String.valueOf(articles.size()));
        mArticleList.setValue(articles);

    }




}
