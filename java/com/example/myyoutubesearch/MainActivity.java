package com.example.myyoutubesearch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "AIzaSyCmOoSqjzAVe5H6LAilHlFNsfk_06FvTkc";
    private final String BASE_URL = "https://www.googleapis.com/";

    private EditText etSearchQuery;
    private Button btnSearch;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewVideos;
    private YoutubeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearchQuery = findViewById(R.id.etSearchQuery);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewVideos = findViewById(R.id.recyclerViewVideos);

        recyclerViewVideos.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YoutubeApiService apiService = retrofit.create(YoutubeApiService.class);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearchQuery.getText().toString().trim();
                if (!query.isEmpty()) {
                    performSearch(apiService, query);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void performSearch(YoutubeApiService apiService, String query) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewVideos.setVisibility(View.GONE);

        apiService.searchVideos("snippet", "video", query, 25, API_KEY)
                .enqueue(new Callback<YoutubeResponse>() {
                    @Override
                    public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        recyclerViewVideos.setVisibility(View.VISIBLE);

                        if (response.isSuccessful() && response.body() != null) {
                            YoutubeResponse youtubeResponse = response.body();
                            if (youtubeResponse.getItems() != null && !youtubeResponse.getItems().isEmpty()) {
                                adapter = new YoutubeAdapter(youtubeResponse.getItems());
                                recyclerViewVideos.setAdapter(adapter);
                            } else {
                                Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Search failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        recyclerViewVideos.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}