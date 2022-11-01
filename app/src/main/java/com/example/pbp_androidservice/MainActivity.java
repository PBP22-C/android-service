package com.example.pbp_androidservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<MusicModel> allMusic = new ArrayList<>();

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
//        test read
        System.out.println("Title: " + allMusic.get(0).getTitle());
        if (allMusic.size() == 0) {
            noMusicTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noMusicTextView.setVisibility(View.GONE);
            recyclerView.setAdapter(new MusicListAdapter(allMusic,getApplicationContext()));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
//        System.out.println("Testing");



//        String[] projection = {
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.DURATION,
//                MediaStore.Audio.Media.DATA
//        };
//        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
//        while (cursor.moveToNext()) {
//            AudioModel songData = new AudioModel(cursor.getString(0), cursor.getString(1), cursor.getString(2));
//            songsList.add(songData);
//        }

    }
    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result== PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }
    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "Please allow permission in App Settings.", Toast.LENGTH_LONG).show();
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
}