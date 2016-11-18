package com.abitcreative.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abitcreative.popularmovies.R;
import com.abitcreative.popularmovies.webapi.Review;

import java.util.List;

/**
 * Created by daber on 18/11/16.
 */

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {

    private final List<Review> reviewList;

    public ReviewRecyclerViewAdapter(List<Review> reviewList){
        this.reviewList = reviewList;
    }
    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review,parent,false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        final Review review = reviewList.get(position);
        holder.reviewTextView.setText(review.content);
        holder.authorTextView.setText(review.author);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final TextView authorTextView;
        private final TextView reviewTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            authorTextView = (TextView) itemView.findViewById(R.id.author);
            reviewTextView = (TextView) itemView.findViewById(R.id.review_text);
        }
    }
}
