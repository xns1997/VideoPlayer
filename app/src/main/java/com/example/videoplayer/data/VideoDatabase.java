package com.example.videoplayer.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Video.class}, version = 7)
public abstract class VideoDatabase extends RoomDatabase {

  private static volatile VideoDatabase INSTANCE;
  private static final int NUM_THREADS = 4;
  static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

  public abstract VideosDao dao();

  static VideoDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (VideoDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VideoDatabase.class, "video_repo.db").fallbackToDestructiveMigration().build();
        }
      }
    }
    return INSTANCE;
  }
}
