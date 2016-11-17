package com.example.kien.testmusic.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kien on 11/16/2016.
 */

public class Musics implements Serializable {
    private ArrayList<Music> mMusicAll =  new ArrayList<Music>();

    public Musics(ArrayList<Music> mMusicAll) {
        this.mMusicAll = mMusicAll;
    }

    public ArrayList<Music> getmMusicAll() {
        return mMusicAll;
    }

    public void setmMusicAll(ArrayList<Music> mMusicAll) {
        this.mMusicAll = mMusicAll;
    }
}
