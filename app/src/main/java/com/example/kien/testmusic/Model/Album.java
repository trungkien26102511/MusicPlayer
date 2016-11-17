package com.example.kien.testmusic.Model;

/**
 * Created by Kien on 11/15/2016.
 */
public class Album {
    private String mSong;
    private String mAlbum;



    public Album(String mSong, String mAlbum) {
        this.mSong = mSong;
        this.mAlbum = mAlbum;
    }

    public String getmSong() {
        return mSong;
    }

    public void setmSong(String mSong) {
        this.mSong = mSong;
    }


    public String getmAlbum() {
        return mAlbum;
    }

    public void setmAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
    }

}
