package com.example.news_app;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news_app.parameter.Articles;
import com.example.news_app.parameter.Headlines;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    final  String API_KEY="82db3e64627e423eac40b56a1c77999a";
    Button button;
    TextView countries;
//    ImageButton floatingActionButton;
    List<Articles> articles=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        button=findViewById(R.id.refreshButton);
        countries=findViewById(R.id.Country);
        final String country=getCountry();
        countries.setText(country);
//        floatingActionButton=(ImageButton)findViewById(R.id.floating);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(MainActivity.this,NewsInDetails.class);
//                startActivity(intent);
//            }
//        });

        retrieveJson(country,API_KEY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveJson(country,API_KEY);
            }
        });
    }
    public  void retrieveJson(String country,String apiKey)
    {
        Call<Headlines> call=Client.getInstance().getApi().getHeadlines(country,apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles()!=null){
                    articles.clear();
                    articles=response.body().getArticles();
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setHasFixedSize(true);
                    adapter =new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {

                Toast.makeText(MainActivity.this,"There is An Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getCountry()
    {
        Locale locale=Locale.getDefault();
        String country=locale.getCountry();
        return country.toLowerCase();
    }
}