package com.abitcreative.popularmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abitcreative.popularmovies.R;
import com.abitcreative.popularmovies.webapi.Video;

import java.util.List;

/**
 * Created by daber on 18/11/16.
 */

public class VideosRecyclerViewAdapter extends RecyclerView.Adapter<VideosRecyclerViewAdapter.VideoViewHolder> {
    private final List<Video> videoList;
    private final Context ctx;

    public VideosRecyclerViewAdapter(Context ctx, List<Video> videoList){
        this.videoList = videoList;
        this.ctx = ctx;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        public TextView trailerTitle;

        public VideoViewHolder(View itemView) {
            super(itemView);
             trailerTitle = (TextView) itemView.findViewById(R.id.trailer_title);
        }

    }
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        final Video video = videoList.get(position);
        holder.trailerTitle.setText( video.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://youtube.com/watch?v="+video.key;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


}
