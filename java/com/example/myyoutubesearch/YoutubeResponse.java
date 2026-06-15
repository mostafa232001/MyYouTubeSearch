package com.example.myyoutubesearch;

import java.util.List;

public class YoutubeResponse {
    private List<YoutubeItem> items;

    public List<YoutubeItem> getItems() {
        return items;
    }

    public void setItems(List<YoutubeItem> items) {
        this.items = items;
    }
}