package com.dev.kunal.newsapp;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<NewsItem[]> {

    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new NewsAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.newsList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
}
