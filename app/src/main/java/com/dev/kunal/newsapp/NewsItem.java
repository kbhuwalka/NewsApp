package com.dev.kunal.newsapp;

/**
 * Created by Kunal on 10/11/2017.
 */

public class NewsItem {
    private String title;
    private String byline;
    private String date;
    private String section;
    private String webUrl;

    public NewsItem(String title, String byline, String date, String section, String webUrl) {
        this.title = title;
        this.byline = byline;
        this.date = date;
        this.section = section;
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getByline() {
        return byline;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
