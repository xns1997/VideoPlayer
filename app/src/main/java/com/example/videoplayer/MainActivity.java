package com.example.videoplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.videoplayer.data.SettingsFragment;
import com.example.videoplayer.data.Video;
import com.example.videoplayer.data.searchUtils.SearchResult;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity
implements
        VideoItemsAdapter.OnVideoClickListener,
        NavigationView.OnNavigationItemSelectedListener
{

  Button openBtn;

  private RecyclerView filesRV;
  private VideoItemsAdapter videoItemsAdapter;
  private VideoViewModel videoViewModel;

  private DrawerLayout drawerLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    this.filesRV = findViewById(R.id.filesRV);
    this.filesRV.setLayoutManager(new LinearLayoutManager(this));
    this.filesRV.setHasFixedSize(true);

    this.videoItemsAdapter = new VideoItemsAdapter(this);
    this.filesRV.setAdapter(videoItemsAdapter);
    this.drawerLayout = findViewById(R.id.drawer_layout);
    ActionBar actionBar = getSupportActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);
    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

    NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
    navigationView.setNavigationItemSelectedListener(this);

    this.videoViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(VideoViewModel.class);

    openBtn = findViewById(R.id.filebtn);
    openBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openDirectory();
      }
    });

    this.videoViewModel.getAllVideos().observe(
            this,
            new Observer<List<Video>>() {
              @Override
              public void onChanged(List<Video> videos) {
                Log.d("Main:",String.valueOf(videos.size()));
                videoItemsAdapter.updateVideoList(videos);
              }
            }
    );

  }

  public void openDirectory() {
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.setType("*/*");
    startActivityForResult(intent, 10);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
      Uri uri = null;
      if (data != null) {
        uri = data.getData();
        Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();

        Video vid = new Video();
        System.out.println("NewVIDEO: " + vid.getPoster());
        vid.setFilename(returnCursor.getString(nameIndex));
        vid.setUri(uri.toString());
        this.videoViewModel.insertVideo(vid);

      }
    } else if (requestCode == 13) {
      System.out.println("Request code 13");
      if ((boolean)data.getExtras().getSerializable("HAS_EXTRA") == true) {
        SearchResult sr = (SearchResult)data.getExtras().getSerializable("RETURN_SR");
        Video updatedVid = (Video)data.getExtras().get("RETURN_VID");
        this.videoViewModel.updateVideo(updatedVid);
      }
    }
  }
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        if(!this.drawerLayout.isDrawerOpen(GravityCompat.START))
          this.drawerLayout.openDrawer(GravityCompat.START);
        else
          this.drawerLayout.closeDrawers();

      default:
        return super.onOptionsItemSelected(item);
    }
  }
  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    this.drawerLayout.closeDrawers();
    switch (item.getItemId()) {
      case R.id.nav_settings:
//        Intent settingsIntent = new Intent(this, SettingsActivity.class);
//        startActivity(settingsIntent);
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);


        return true;
      default:
        return false;
    }
  }
  @Override
  public void onVideoClicked(Video video) {
    Intent intent = new Intent(this, VideoDetailsActivity.class);
    intent.putExtra(VideoDetailsActivity.EXTRA_VIDEO_REPO, video);
    startActivityForResult(intent, 13);
  }
}