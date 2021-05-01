package com.example.videoplayer.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VideoRepo {
  private VideosDao videosDao;

  public VideoRepo(Application application) {
    VideoDatabase db = VideoDatabase.getDatabase(application);
    videosDao = db.dao();
  }

  public void insertVideo(Video vid) {
    VideoDatabase.databaseWriteExecutor.execute(new Runnable() {
      @Override
      public void run() {
        videosDao.insert(vid);
      }
    });
  }

  public void deleteVideo(Video vid) {
    VideoDatabase.databaseWriteExecutor.execute(new Runnable() {
      @Override
      public void run() {
        videosDao.delete(vid);
      }
    });
  }

  public LiveData<List<Video>> getAllVideos() {
    return this.videosDao.getAllVideos();
  }

  public void updateVideo(Video vid) {
    VideoDatabase.databaseWriteExecutor.execute(new Runnable() {
      @Override
      public void run() {
        videosDao.updateVideo(vid);
      }
    });
  }
}
