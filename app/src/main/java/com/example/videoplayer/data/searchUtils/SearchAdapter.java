package com.example.videoplayer.data.searchUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.R;
import com.example.videoplayer.VideoItemsAdapter;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

  private List<SearchResult> searchResultList;
  private OnSearchResultClickListener searchClickListener;

  public interface OnSearchResultClickListener {
    void onSearchResultClicked(SearchResult sr);
  }

  public SearchAdapter(OnSearchResultClickListener listener) {
    this.searchClickListener = listener;
  }

  public void updateSearchResults(List<SearchResult> results) {
    this.searchResultList = results;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.search_result_item, parent, false);
    return new SearchViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
    holder.bind(this.searchResultList.get(position));
  }

  @Override
  public int getItemCount() {
    if (this.searchResultList != null) {
      return searchResultList.size();
    } else {
      return 0;
    }
  }

  class SearchViewHolder extends RecyclerView.ViewHolder{

    private TextView videoname;
    private TextView desc;
    private ImageView image;

    public SearchViewHolder(@NonNull View itemView) {
      super(itemView);
      this.videoname = itemView.findViewById(R.id.result_title_tv);
      this.desc = itemView.findViewById(R.id.result_desc_tv);
      this.image = itemView.findViewById(R.id.result_poster);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          searchClickListener.onSearchResultClicked(searchResultList.get(getAdapterPosition()));
        }
      });
    }

    void bind(SearchResult sr) {
      this.videoname.setText(sr.getName());
      this.desc.setText(sr.getOverview());


      if (!sr.getPoster().equals("none")) {
        String path = "https://www.themoviedb.org/t/p/w440_and_h660_face" + sr.getPoster();
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
            image.setImageBitmap(bitmap[0]);
          }
        }
      }
    }
  }
}
