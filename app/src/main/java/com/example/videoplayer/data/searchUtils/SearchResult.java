package com.example.videoplayer.data.searchUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.Serializable;
import java.lang.reflect.Type;

public class SearchResult implements Serializable {

  private String poster;
  private int id;
  private String name;
  private String overview;
  private String releasedate;
  private double voteavg;

  public SearchResult(String poster, int id, String name, String overview, String releasedate, double voteavg) {
    this.poster = poster;
    this.id = id;
    this.name = name;
    this.overview = overview;
    this.releasedate = releasedate;
    this.voteavg = voteavg;
  }

  public SearchResult() {
    this.poster = null;
    this.id = -1;
    this.name = null;
    this.overview = null;
    this.releasedate = null;
    this.voteavg = -1;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleasedate() {
    return releasedate;
  }

  public void setReleasedate(String releasedate) {
    this.releasedate = releasedate;
  }

  public double getVoteavg() {
    return voteavg;
  }

  public void setVoteavg(double voteavg) {
    this.voteavg = voteavg;
  }

  public static class JsonDeserializer implements com.google.gson.JsonDeserializer<SearchResult> {

    @Override
    public SearchResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      JsonObject resultsObj = json.getAsJsonObject();

      SearchResult sr = new SearchResult();

      try {
        sr.setPoster(resultsObj.getAsJsonPrimitive("poster_path").getAsString());
      } catch (Exception e) {
        System.out.println("Null poster value");
        sr.setPoster("none");
      }

      sr.setId(resultsObj.getAsJsonPrimitive("id").getAsInt());
      sr.setName(resultsObj.getAsJsonPrimitive("title").getAsString());
      sr.setOverview(resultsObj.getAsJsonPrimitive("overview").getAsString());
      sr.setReleasedate(resultsObj.getAsJsonPrimitive("release_date").getAsString());
      sr.setVoteavg(resultsObj.getAsJsonPrimitive("vote_average").getAsDouble());

      return sr;
    }
  }
}
