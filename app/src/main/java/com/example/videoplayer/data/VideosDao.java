package com.example.videoplayer.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VideosDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(Video vid);

  @Delete
  void delete(Video vid);

  @Query("SELECT * FROM videos")
  LiveData<List<Video>> getAllVideos();

  @Update
  void updateVideo(Video vid);
}
