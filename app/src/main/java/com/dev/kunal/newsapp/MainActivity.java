package com.dev.kunal.newsapp;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<NewsItem[]>, NewsAdapter.AdapterCallbacks {

    private NewsAdapter mAdapter;

    private TextView emptyView;
    private RecyclerView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = (RecyclerView) findViewById(R.id.newsList);
        emptyView = (TextView) findViewById(R.id.empty_view);

        showEmpty();

        mAdapter = new NewsAdapter(this);
        newsList.setAdapter(mAdapter);
        newsList.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(0, null, this).forceLoad();

    }

    @Override
    public Loader<NewsItem[]> onCreateLoader(int id, Bundle args) {
        return new NewsTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<NewsItem[]> loader, NewsItem[] data) {
        mAdapter.swapData(data);
    }

    @Override
    public void onLoaderReset(Loader<NewsItem[]> loader) {
        mAdapter.swapData(null);
    }

    @Override
    public void showList() {
        emptyView.setVisibility(View.GONE);
        newsList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        emptyView.setVisibility(View.VISIBLE);
        newsList.setVisibility(View.VISIBLE);
    }
}
