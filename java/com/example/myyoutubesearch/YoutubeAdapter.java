package com.example.myyoutubesearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.VideoViewHolder> {

    private List<YoutubeItem> videoList;

    public YoutubeAdapter(List<YoutubeItem> videoList) {
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int viewType) {
        YoutubeItem item = videoList.get(viewType);
        if (item != null && item.getSnippet() != null) {
            Snippet snippet = item.getSnippet();

            holder.tvTitle.setText(snippet.getTitle());
            holder.tvChannelTitle.setText(snippet.getChannelTitle());
            holder.tvPublishTime.setText(snippet.getPublishedAt());
            holder.tvDescription.setText(snippet.getDescription());

            if (snippet.getThumbnails() != null && snippet.getThumbnails().getHigh() != null) {
                Glide.with(holder.itemView.getContext())
                        .load(snippet.getThumbnails().getHigh().getUrl())
                        .into(holder.ivThumbnail);
            }
        }
    }

    @Override
    public int getItemCount() {
        return videoList != null ? videoList.size() : 0;
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvTitle, tvChannelTitle, tvPublishTime, tvDescription;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvChannelTitle = itemView.findViewById(R.id.tvChannelTitle);
            tvPublishTime = itemView.findViewById(R.id.tvPublishTime);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}