package com.example.pbp_androidservice;

import android.media.MediaPlayer;

public class MyMediaPlayer {
    static MediaPlayer instance;
    static int lastPosition;

    public static MediaPlayer createInstance(){
        if(instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static void setLastPosition(int pos) {
        lastPosition = pos;
    }

    public static int currentIndex = -1;
    public static int previousIndex = -1;
}