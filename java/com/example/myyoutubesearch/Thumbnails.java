package com.example.myyoutubesearch;

public class Thumbnails {
    private DefaultThumbnail high;

    public DefaultThumbnail getHigh() {
        return high;
    }

    public void setHigh(DefaultThumbnail high) {
        this.high = high;
    }

    public static class DefaultThumbnail {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}