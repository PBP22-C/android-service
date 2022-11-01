package com.example.pbp_androidservice;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<MusicModel> allMusic = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read file in raw folder
        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length; i++) {
            String path = null;
            try {
                path = "android.resource://" + getPackageName() + "/" + fields[i].getInt(fields[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            System.out.println(path);
            // Read metadata
            Uri mediaPath = Uri.parse(path);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(this, mediaPath);

            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            MusicModel music = new MusicModel(path, title, duration);
            allMusic.add(music);
        }

        for (int j = 0; j < allMusic.size(); j++) {
            System.out.println("Title: " + allMusic.get(j).getTitle());
            System.out.println("Duration: " + allMusic.get(j).getDuration());
        }


        // Testing read metadata
//        Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.a_unisono_1_stanza);
//        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        mmr.setDataSource(this, mediaPath);
//
//        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
//        String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
//
//        TextView tv_music = findViewById(R.id.tv_music);
//        tv_music.setText(title + " " + artist);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Testing");
    }
}