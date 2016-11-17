package com.example.kien.testmusic.Model;

/**
 * Created by Kien on 11/15/2016.
 */
public class Artist {
    private String mSong;
    private String mArtist;


    public Artist(String mSong) {
        this.mSong = mSong;
    }

    public Artist(String mSong, String mArtist) {
        this.mSong = mSong;
        this.mArtist = mArtist;
    }


    public String getmSong() {
        return mSong;
    }

    public void setmSong(String mSong) {
        this.mSong = mSong;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }
}
