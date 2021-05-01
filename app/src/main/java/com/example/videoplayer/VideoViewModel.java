package com.example.videoplayer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.videoplayer.data.Video;
import com.example.videoplayer.data.VideoRepo;

import java.util.ArrayList;
import java.util.List;

public class VideoViewModel extends AndroidViewModel {

  private ArrayList<Video> vidList;
  private VideoRepo videoRepo;

  public VideoViewModel(@NonNull Application application) {
    super(application);
    this.videoRepo = new VideoRepo(application);
    this.vidList = new ArrayList<>();
  }

  public void insertVideo(Video vid) {
    this.videoRepo.insertVideo(vid);
  }

  public void deleteVideo(Video vid) {
    this.videoRepo.deleteVideo(vid);
  }

  public LiveData<List<Video>> getAllVideos() {
    return this.videoRepo.getAllVideos();
  }

  public void updateVideo(Video vid) { this.videoRepo.updateVideo(vid); }

//  public LiveData<List<Video>> getVideosLive() {
//    LiveData<ArrayList<Video>> vids = vidList;
//  }
}
