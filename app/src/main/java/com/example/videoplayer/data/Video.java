package com.example.videoplayer.data;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "videos")
public class Video implements Serializable {
  private String filename;
  private String title;

  @PrimaryKey
  @NonNull
  private String uri;

  private int length;
  private String date;
  private double rating;
  private int id;
  private String overview;
  private String poster;

  public Video() {
    this.filename = "none";
    this.title = "none";
    this.uri = "none";
    this.length = -1;
    this.date = "none";
    this.rating = -1;
    this.id = -1;
    this.overview = "none";
    this.poster = "none";
  }

  public Video(String fn, String title, String uri, int len, String date, double rating, int id,String overview, String poster) {
    this.filename = fn;
    this.title = title;
    this.uri = uri;
    this.length = len;
    this.date = date;
    this.rating = rating;
    this.id = id;
    this.overview = overview;
    this.poster = poster;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getRating() {
    return rating;
  }

  public void setRating(double rating) {
    this.rating = rating;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOverview() { return this.overview; }

  public void setOverview (String overview) { this.overview = overview; }

}
