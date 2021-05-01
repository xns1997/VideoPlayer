package com.example.videoplayer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.videoplayer.data.Video;
import com.example.videoplayer.data.searchUtils.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VideoDetailsActivity extends AppCompatActivity {
  private static final String TAG = VideoDetailsActivity.class.getSimpleName();
  public static final String EXTRA_VIDEO_REPO = "Video";

  private Video video;

  private TextView title;
  private TextView overview;
  private TextView date;
  private TextView rating;
  private ImageView imageView;

  private String videoUri;

  private Button addBtn;
  private Button playBtn;

  private SearchResult sr;

  private boolean videomodified;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.video_detail_activity);
    Intent intent = getIntent();

    title = findViewById(R.id.tv_detail_title);
    overview = findViewById(R.id.tv_detail_overview_text);
    date = findViewById(R.id.tv_detail_date);
    imageView = findViewById(R.id.imageView);
//    rating = findViewById()
    this.videomodified = false;

    if(intent!=null && intent.hasExtra(EXTRA_VIDEO_REPO)){
      this.video = (Video)intent.getSerializableExtra(EXTRA_VIDEO_REPO);
      videoUri = video.getUri();
      fillDetails(this.video);
    } else {
      finish();
    }

    addBtn = findViewById(R.id.bt_video_add);
    playBtn = findViewById(R.id.bt_video_play);


    addBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(VideoDetailsActivity.this, SearchActivity.class);
//        intent.putExtra(VideoDetailsActivity.EXTRA_VIDEO_REPO, video);
        startActivityForResult(intent, 12);


      }
    });

    // Add the listener for the play button
    playBtn.setOnClickListener(new View.OnClickListener()
    {

      @Override
      public void onClick(View v) {
        Context context = v.getContext();
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("movie_directory", videoUri);
        context.startActivity(intent);
      }
    });

  }

  @Override
  public boolean onSupportNavigateUp() {
    Intent returnIntent = new Intent();
    if (videomodified == true) {
      returnIntent.putExtra("RETURN_SR", this.sr);
      returnIntent.putExtra("RETURN_VID", this.video);
      returnIntent.putExtra("HAS_EXTRA", true);
    } else {
      returnIntent.putExtra("NO_CHANGE", sr);
      returnIntent.putExtra("HAS_EXTRA", false);
    }
    setResult(13, returnIntent);
    finish();
    return true;

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == 12) {
      if ((boolean)data.getExtras().get("HAS_EXTRA") == true) {
        this.sr = (SearchResult)data.getExtras().getSerializable("vidinfo");

        this.video.setTitle(sr.getName());
        this.video.setOverview(sr.getOverview());
        this.video.setDate(sr.getReleasedate());
        this.video.setRating(sr.getVoteavg());
        this.video.setPoster(sr.getPoster());

        this.videomodified = true;

        fillDetails(this.video);
      }

    }
  }

  private void fillDetails(Video video) {
    title.setText(video.getTitle());

    overview.setText(video.getOverview()!=null ? video.getOverview() : "<No Data>" );

    date.setText(video.getDate());

    if (!video.getPoster().equals("none")) {
      String path = "https://www.themoviedb.org/t/p/w440_and_h660_face" + video.getPoster();
      final Bitmap[] bitmap = new Bitmap[1];
      Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
          URL url;
          try {
            url = new URL(path);
            System.out.println("Requesting image " + url.toString());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            System.out.println(connection.getResponseCode());
            InputStream input = connection.getInputStream();
            bitmap[0] = BitmapFactory.decodeStream(input);
          } catch (MalformedURLException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
      t.start();
      while(t.isAlive()){
        if(bitmap[0] !=null){
          imageView.setImageBitmap(bitmap[0]);
        }
      }
    }

  }

  @Override
  public void onBackPressed() {
    Intent returnIntent = new Intent();
    returnIntent.putExtra("NO_CHANGE", sr);
    returnIntent.putExtra("HAS_EXTRA", false);
    setResult(13, returnIntent);
    finish();
//    super.onBackPressed();
  }
}


