package com.example.videoplayer.data.searchUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
  private SearchRepository searchRepo;
  private LiveData<SearchObj> searchObjs;

  public SearchViewModel() {
    this.searchRepo = new SearchRepository();
    this.searchObjs = searchRepo.getSearchObjects();
  }

  public LiveData<SearchObj> getSearchObjects() { return this.searchObjs; }

  public void loadSearchResults(String query) {
    this.searchRepo.doSearch(query);
  }
}
