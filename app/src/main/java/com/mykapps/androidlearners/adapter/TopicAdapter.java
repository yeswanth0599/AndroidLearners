package com.mykapps.androidlearners.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.mykapps.androidlearners.R;
import com.mykapps.androidlearners.activity.ProgramDetailDisplayActvitity;
import com.mykapps.androidlearners.data.JsonDataList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private List<JsonDataList> jsonDataListsItems;
    private Context mContext;

    public TopicAdapter(List<JsonDataList> jsonDataLists, Context context) {
        this.jsonDataListsItems = jsonDataLists;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * inflating the MovieList.xml in MainActivity
         */
        View viewHolderRefrence = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.json_data_list, parent, false);
        /**
         * Return ViewHolder Object
         */
        return new ViewHolder(viewHolderRefrence);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final JsonDataList jsonDataListBind = jsonDataListsItems.get(position);
        /*Picasso.with(mContext)
                .load(jsonDataListBind.getTopicImage())
                .placeholder(R.color.imageThumbnailPlaceholder)
                .into(holder.thumbnailImageView);*/
        if (jsonDataListBind.getPlaceholderString().equals("java")) {
            Glide.with(mContext)
                    .load(jsonDataListBind.getTopicImage())
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .crossFade()
                    .into(holder.thumbnailImageView);
            holder.titleTexViewDisp.setText(jsonDataListBind.getTopicName());
        } else {
            Glide.with(mContext)
                    .load(jsonDataListBind.getTopicImage())
                    .placeholder(R.drawable.kotlin_placeholder)
                    .centerCrop()
                    .crossFade()
                    .into(holder.thumbnailImageView);
            holder.titleTexViewDisp.setText(jsonDataListBind.getTopicName());
        }
    }

    @Override
    public int getItemCount() {
        return jsonDataListsItems.size();
    }

    /**
     * Creating ViewHolder(InnerClass of MovieAdapter) class
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView thumbnailImageView;
        public TextView titleTexViewDisp;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            thumbnailImageView = itemView.findViewById(R.id.topicImageView);
            titleTexViewDisp = itemView.findViewById(R.id.trainingTopicName);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            JsonDataList jsonList = jsonDataListsItems.get(position);

            Intent intent = new Intent(mContext, ProgramDetailDisplayActvitity.class);
            intent.putExtra("topicID", jsonList.getTopicID());
            intent.putExtra("topicTitle", jsonList.getTopicName());
            intent.putExtra("topicImage", jsonList.getTopicImage());
            intent.putExtra("topicVideo", jsonList.getVideoUrl());
            intent.putExtra("topicLikesCount", jsonList.getLikesCount());
            intent.putExtra("topicCommentsCount", jsonList.getCommentsCount());
            intent.putExtra("topicPlaceholderString", jsonList.getPlaceholderString());
            mContext.startActivity(intent);
        }


    }

}