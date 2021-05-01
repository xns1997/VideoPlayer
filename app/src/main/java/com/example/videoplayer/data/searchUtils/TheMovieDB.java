package com.example.videoplayer.data.searchUtils;

import android.net.Uri;

//https://api.themoviedb.org/3/search/movie?api_key=048577b6371137c8d62fb8b425a127e9&language=en-US&query=toy%20story&page=1&include_adult=false
public class TheMovieDB
{
  private final static String KEY = "048577b6371137c8d62fb8b425a127e9";
  private final static String READ_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNDg1NzdiNjM3MTEzN2M4ZDYyZmI4YjQyNWExMjdlOSIsInN1YiI6IjYwNGZkMWUzOGM3YjBmMDA1MzljZDI5YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cZ1N5AozLYfhnUDZrLnW95XWG-bwStSaJtLfSBUnS6Y";
  private final static String BASE_URL = "https://api.themoviedb.org/3/search/";

  // URL PARAMS
  private final static String API_KEY_PARAM = "api_key";
  private final static String API_LANG_PARAM = "language";
  private final static String API_INCLUDE_ADULT = "include_adult";
  private final static String API_QUERY_PARAM = "query";

  // Prefs
  private final static String LANGUAGE_PREF = "en-US";
  private final static String ADULT_CONTENT_PREF = "false";

  public static String buildQuery(String query)
  {
    return Uri.parse(BASE_URL).buildUpon()

            .appendQueryParameter(API_KEY_PARAM, KEY)
            .appendQueryParameter(API_LANG_PARAM, LANGUAGE_PREF)
            .appendQueryParameter(API_QUERY_PARAM, query)
            .appendQueryParameter(API_INCLUDE_ADULT, ADULT_CONTENT_PREF)
            .build().toString();
  }

}
