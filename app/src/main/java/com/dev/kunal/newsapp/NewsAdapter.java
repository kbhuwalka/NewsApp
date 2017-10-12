package com.dev.kunal.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Kunal on 10/12/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private NewsItem[] mData;

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsAdapter.ViewHolder holder, int position) {
        final NewsItem newsItem = mData[position];

        holder.title.setText(newsItem.getTitle());
        holder.byline.setText(newsItem.getByline());
        holder.section.setText(newsItem.getSection());
        holder.date.setText(newsItem.getDate());

        final Context context = holder.rootView.getContext();
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.getWebUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mData == null) return 0;
        return mData.length;
    }

    public void swapData(NewsItem[] data) {
        mData = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView title;
        public TextView byline;
        public TextView date;
        public TextView section;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            title = view.findViewById(R.id.title);
            byline = view.findViewById(R.id.byline);
            date = view.findViewById(R.id.date);
            section = view.findViewById(R.id.section);
        }
    }
}
