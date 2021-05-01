package com.example.videoplayer.data.searchUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchRepository {
  private static final String BASE_URL = "https://api.themoviedb.org/3/search/";
  private final static String KEY = "048577b6371137c8d62fb8b425a127e9";
  private final static String LANGUAGE_PREF = "en-US";
  private final static String ADULT_CONTENT_PREF = "false";

  private MutableLiveData<SearchObj> searchObj;

  private TMDBService tmdbService;

  public SearchRepository() {
    this.searchObj = new MutableLiveData<>();
    this.searchObj.setValue(null);

    Gson gson = new GsonBuilder().registerTypeAdapter(SearchResult.class, new SearchResult.JsonDeserializer()).create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    this.tmdbService = retrofit.create(TMDBService.class);
  }

  public LiveData<SearchObj> getSearchObjects() { return this.searchObj; }

  public void doSearch(String query) {
    Call<SearchObj> req = this.tmdbService.fetchMovies(KEY, LANGUAGE_PREF, ADULT_CONTENT_PREF, query);
    System.out.println("Requesting: " + req.request().url().toString());

    req.enqueue(new Callback<SearchObj>() {
      @Override
      public void onResponse(Call<SearchObj> call, Response<SearchObj> response) {
        if (response.code() == 200) {
          System.out.println("Response code 200 received");
          searchObj.setValue(response.body());
        } else {
          System.out.println("API request ERROR with code " + response.code() + "\n" + response.toString());
        }
      }

      @Override
      public void onFailure(Call<SearchObj> call, Throwable t) {
        System.out.println("onFailuire(), printing stacktrace: ");
        t.printStackTrace();
      }
    });
  }
}
