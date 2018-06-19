package com.mykapps.androidlearners.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mykapps.androidlearners.R;
import com.mykapps.androidlearners.activity.ProgramDetailDisplayActvitity;
import com.mykapps.androidlearners.data.Contract;
import com.mykapps.androidlearners.data.JsonDataList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private Cursor mCursor;
    private Context context;

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.json_data_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        int placeHolderStringIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_PLACEHOLDER_STRING);
        String placeHolderString = mCursor.getString(placeHolderStringIndex);
        int topicImageIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_IMAGE);
        String topicImageDisp = mCursor.getString(topicImageIndex);
        int topicTitleIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_TITLE);
        String topicTitle = mCursor.getString(topicTitleIndex);
        holder.titleTexViewDisp.setText(topicTitle);
        if (placeHolderString.equals("java")) {
            Glide.with(context)
                    .load(topicImageDisp)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .crossFade()
                    .into(holder.thumbnailImageView);

        } else {
            Glide.with(context)
                    .load(topicImageDisp)
                    .placeholder(R.drawable.kotlin_placeholder)
                    .centerCrop()
                    .crossFade()
                    .into(holder.thumbnailImageView);

        }

    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

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
        public void onClick(View v) {
            int position = getAdapterPosition();
            mCursor.moveToPosition(position);

            int topicIdIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_ID);
            int topicTitleIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_TITLE);
            int topicImageIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_IMAGE);
            int topicVideoURLIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_VIDEO_URL);
            int topicLikesCountIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_LIKES_COUNT);
            int topicCommentsCountIndex = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_COMMENTS_COUNT);
            int topicColumnPlaceHolderString = mCursor.getColumnIndex(Contract.EntryInfo.COLUMN_PLACEHOLDER_STRING);
            String topicID = String.valueOf(mCursor.getInt(topicIdIndex));
            String topicTitle = mCursor.getString(topicTitleIndex);
            String topicImage = mCursor.getString(topicImageIndex);
            String topicVideoURL = mCursor.getString(topicVideoURLIndex);
            String topicLikesCount = mCursor.getString(topicLikesCountIndex);
            String topicCommentsCount = mCursor.getString(topicCommentsCountIndex);
            String topicColumnPlaceGolder = mCursor.getString(topicColumnPlaceHolderString);

            Intent intent = new Intent(context, ProgramDetailDisplayActvitity.class);
            intent.putExtra("topicID", topicID);
            intent.putExtra("topicTitle", topicTitle);
            intent.putExtra("topicImage", topicImage);
            intent.putExtra("topicVideo", topicVideoURL);
            intent.putExtra("topicLikesCount", topicLikesCount);
            intent.putExtra("topicCommentsCount", topicCommentsCount);
            intent.putExtra("topicPlaceholderString", topicColumnPlaceHolderString);
            context.startActivity(intent);
        }
    }
}
