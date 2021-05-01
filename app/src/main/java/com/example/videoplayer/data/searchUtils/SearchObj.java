package com.example.videoplayer.data.searchUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchObj {
  @SerializedName("results")
  private ArrayList<SearchResult> searchResults;

  public ArrayList<SearchResult> getSearchResults() { return searchResults; }
}
