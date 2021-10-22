package com.example.newzapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    //NEWS API KEY : 08fcf623d28a417294b1ffa58b3251d8
    RecyclerView RVCategories, RVNews;
    ProgressBar progressBar;

    ArrayList<Articles> articlesArrayList;
    ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RVCategories = findViewById(R.id.RVCategories);
        RVNews = findViewById(R.id.RVNews);
        progressBar = findViewById(R.id.progressBar);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();

        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this, this);

        RVNews.setLayoutManager(new LinearLayoutManager(this));
        RVNews.setAdapter(newsRVAdapter);

        //already set layoutmanager inside the mainActivity file so we can skip setLayoutManager(..)
        RVCategories.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    //get categories
    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://media.istockphoto.com/photos/multiple-television-screens-in-blue-tones-picture-id171588907?b=1&k=20&m=171588907&s=170667a&w=0&h=yzJufmRV5KvD4W9BVPGJvo1skGV5e1uNXumVATMr1mg="));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1518770660439-4636190af475?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8dGVjaG5vbG9neXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://images.unsplash.com/photo-1567427018141-0584cfcbf1b8?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8c2NpZW5jZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://images.unsplash.com/photo-1531415074968-036ba1b575da?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8Y3JpY2tldHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://media.istockphoto.com/photos/large-group-of-business-people-in-convention-centre-picture-id1281724535?b=1&k=20&m=1281724535&s=170667a&w=0&h=RV0k68y2VPMDnP6QlW_7kErXhbLcqjYVgNmwc3kMMLo="));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://media.istockphoto.com/photos/popcorn-and-clapperboard-picture-id1191001701?b=1&k=20&m=1191001701&s=170667a&w=0&h=uVqDpnXNtnfbhB-F4sWac_t3oL_YSrDuHeCKdaJGS3U="));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://media.istockphoto.com/photos/caregiver-assist-senior-woman-at-home-picture-id1296176562?b=1&k=20&m=1296176562&s=170667a&w=0&h=gHya2Ee_SnnfngzSvzRra93uSlb7xG76apicnILHvIw="));

        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        progressBar.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apikey=08fcf623d28a417294b1ffa58b3251d8";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=08fcf623d28a417294b1ffa58b3251d8";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("All")){
           call = retrofitAPI.getAllNews(url);
        }
        else
        {
            call = retrofitAPI.getAllNews(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                progressBar.setVisibility(View.GONE);

                ArrayList<Articles> articles = newsModal.getArticles();

                for(int i =0;i<articles.size();i++){
                    Articles a = new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent());
                    articlesArrayList.add(a);
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryclick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);

    }
}