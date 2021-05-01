package com.example.videoplayer.data.searchUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBService {
  @GET("movie")
  Call<SearchObj> fetchMovies(
          @Query("api_key") String APIKey,
          @Query("language") String lang,
          @Query("include_adult") String adult,
          @Query("query") String query
  );
}
