package com.example.myyoutubesearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeApiService {
    @GET("youtube/v3/search")
    Call<YoutubeResponse> searchVideos(
            @Query("part") String part,
            @Query("type") String type,
            @Query("q") String query,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
}