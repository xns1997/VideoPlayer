package com.example.videoplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.data.Video;
import com.example.videoplayer.data.searchUtils.SearchAdapter;
import com.example.videoplayer.data.searchUtils.SearchObj;
import com.example.videoplayer.data.searchUtils.SearchResult;
import com.example.videoplayer.data.searchUtils.SearchViewModel;

public class SearchActivity extends AppCompatActivity
        implements SearchAdapter.OnSearchResultClickListener{

  private Button searchBtn;
  private EditText searchfield;
  private RecyclerView resultsRV;
  private SearchAdapter searchAdapter;

  private SearchViewModel searchViewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.search_activity);

    this.searchBtn = findViewById(R.id.search_btn);
    this.searchfield = findViewById(R.id.et_search_box);

    this.resultsRV = findViewById(R.id.searchResultsRV);
    this.resultsRV.setLayoutManager(new LinearLayoutManager(this));

    this.searchAdapter = new SearchAdapter((SearchAdapter.OnSearchResultClickListener) this);
    this.resultsRV.setAdapter(searchAdapter);

    this.searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

    searchBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String query = searchfield.getText().toString();
        if (!TextUtils.isEmpty(query)) {
          searchViewModel.loadSearchResults(query);
        }
      }
    });

    this.searchViewModel.getSearchObjects().observe(
            this,
            new Observer<SearchObj>() {
              @Override
              public void onChanged(SearchObj searchObj) {
                System.out.println("Search objects updated: ");
                if (searchObj == null) {
                  System.out.println("searchobj is null");
                } else {
                  searchAdapter.updateSearchResults(searchObj.getSearchResults());
                }
              }
            }
    );
  }

  @Override
  public void onSearchResultClicked(SearchResult sr) {
    System.out.println("Item clicked: " + sr.getName());
    Intent returnIntent = new Intent();
    returnIntent.putExtra("vidinfo", sr);
    returnIntent.putExtra("HAS_EXTRA", true);
    setResult(12, returnIntent);
    finish();
  }

  @Override
  public boolean onSupportNavigateUp() {
    Intent returnIntent = new Intent();
    returnIntent.putExtra("HAS_EXTRA", false);
    setResult(12, returnIntent);
    finish();
    return true;
  }

  @Override
  public void onBackPressed() {
    Intent returnIntent = new Intent();
    returnIntent.putExtra("HAS_EXTRA", false);
    setResult(12, returnIntent);
    finish();
  }
}
