package com.example.kien.testmusic;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.kien.testmusic.Model.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kien on 11/17/2016.
 */

public class Data {
    private static ArrayList<Music> sMusicList =  new ArrayList<Music>();

    private static List<String> sSongs = new ArrayList<String>();
    static ContentResolver cr;
    public static ArrayList<Music> loadData(){
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION
        };
//
//        Cursor cursor = this.managedQuery(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                selection,
//                null,
//                null);
        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);
        int i = 0;

        while(cursor.moveToNext()) {
            sSongs.add(cursor.getString(0) + "||"
                    + cursor.getString(1) + "||"
                    + cursor.getString(2) + "||"
                    + cursor.getString(3) + "||"
                    + cursor.getString(4) + "||"
                    + cursor.getString(5));
            Music o = new Music(cursor.getString(2), "Album " + i, cursor.getString(1),cursor.getString(3));
            i++;
            sMusicList.add(o);
        }
        return sMusicList;
    }
}
