package com.example.pbp_androidservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    ArrayList<MusicModel> songsList = new ArrayList<>();

    RecyclerView recyclerView;
    TextView noMusicTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        noMusicTextView = findViewById(R.id.tv_no_songs);

        // Read file in raw folder
        Field[] fields = R.raw.class.getFields();

        for (int i = 0; i < fields.length; i++) {
            String path = null;
            try {
                path = "android.resource://" + getPackageName() + "/" + fields[i].getInt(fields[i]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // Read metadata
            Uri mediaPath = Uri.parse(path);
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(this, mediaPath);

            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            MusicModel music = new MusicModel(path, title, duration);
            songsList.add(music);
        }

        if (songsList.size() == 0) {
            noMusicTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noMusicTextView.setVisibility(View.GONE);
            recyclerView.setAdapter(new MusicListAdapter(songsList,getApplicationContext()));
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }

        // Testing print out all music data
//        for (int j = 0; j < songsList.size(); j++) {
//            System.out.println("Title: " + songsList.get(j).getTitle());
//            System.out.println("Duration: " + songsList.get(j).getDuration());
//        }

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
    protected void onRestart() {
        super.onRestart();
        // Stop music from playing if user clicked back button
        if (MyMediaPlayer.instance != null) {
            if (MyMediaPlayer.instance.isPlaying()) {
                MyMediaPlayer.instance.stop();
            }
        }
    }

    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "Please allow permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
}