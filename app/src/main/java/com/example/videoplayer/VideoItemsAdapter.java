package com.example.videoplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.data.Video;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class VideoItemsAdapter extends RecyclerView.Adapter<VideoItemsAdapter.VideoViewHolder> {

  private List<Video> videolist;
  private OnVideoClickListener resultClickListener;


  interface OnVideoClickListener {
    void onVideoClicked(Video video);
  }

  public VideoItemsAdapter(OnVideoClickListener listener) {
    this.resultClickListener = listener;
  }


  @NonNull
  @Override
  public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(R.layout.video_item, parent, false);
    return new VideoViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
    Log.d("Adapter: " , String.valueOf(position));
    holder.bind(this.videolist.get(position));
  }

  public void updateVideoList(List<Video> vids) {
    this.videolist = vids;
    notifyDataSetChanged();
  }

  public void addVideo(Video vid) {
    videolist.add(vid);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    if (this.videolist != null) {
      return videolist.size();
    } else {
      return 0;
    }
  }

  class VideoViewHolder extends RecyclerView.ViewHolder {

    private TextView videoTextView;
    private TextView videoDescView;
    private ImageView image;

    public VideoViewHolder(@NonNull View itemView) {
      super(itemView);
      videoTextView = itemView.findViewById(R.id.videoname_tv);
      videoDescView = itemView.findViewById(R.id.video_desc_tv);
      image = itemView.findViewById(R.id.video_pic_iv);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          resultClickListener.onVideoClicked(
                  videolist.get(getAdapterPosition())
          );
        }
      });
    }

    void bind(Video vid) {
      Log.d("Adapter: " ,vid.getFilename());
      videoTextView.setText(vid.getFilename());
      videoDescView.setText(vid.getTitle());

      System.out.println("Poster: " + vid.getPoster());

      if (!vid.getPoster().equals("none")) {
        String path = "https://www.themoviedb.org/t/p/w440_and_h660_face" + vid.getPoster();
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
