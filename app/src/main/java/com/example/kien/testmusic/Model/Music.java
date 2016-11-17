package com.example.kien.testmusic.Model;

import java.io.Serializable;

/**
 * Created by Kien on 11/15/2016.
 */
public class Music implements Serializable {
    private String mSong;
    private String mArtist;
    private String mAlbum;
    private String mPath;

    public Music(String mSong) {
        this.mSong = mSong;
    }

    public Music(String mSong, String mArtist) {
        this.mSong = mSong;
        this.mArtist = mArtist;
    }

    public Music(String mSong, String mAlbum, String mArtist, String mPath) {
        this.mSong = mSong;
        this.mAlbum = mAlbum;
        this.mArtist = mArtist;
        this.mPath = mPath;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmAlbum() {
        return mAlbum;
    }

    public void setmAlbum(String mAlbum) {
        this.mAlbum = mAlbum;
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
