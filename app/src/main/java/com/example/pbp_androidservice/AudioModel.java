package com.example.pbp_androidservice;

public class AudioModel {
    private String path;
    private String title;
    private String duration;

    public AudioModel(String title, String duration, String path) {
        this.title = title;
        this.duration = duration;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
