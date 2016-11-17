package com.example.kien.testmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.kien.testmusic.DataItemMusicCallback;
import com.example.kien.testmusic.Model.Music;
import com.example.kien.testmusic.Model.Musics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener{
    private String mPath;
    private boolean isRepeat = false;
    private boolean isShuffle = false;

    public class MusicBinder extends Binder {
       public MusicService getService(){
            return MusicService.this;
        }
    }
    protected MediaPlayer mPlayer;
    protected MusicBinder mBinder;
    private ArrayList<Music> mMusicList =  new ArrayList<Music>();
    private int currentSong = 0;
//    private DataItemMusicCallback dataItemMusicCallback;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer =  new MediaPlayer();
//        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bakeconnghe);
        mBinder = new MusicBinder();
    }
    public void playMusic(){
        mPlayer.start();
    }
    public void pauseMusic(){
        mPlayer.pause();
    }
    public void seekTo(int miliseconds){
        mPlayer.seekTo(miliseconds);
    }
    public int getDuration(){
        if(mPlayer != null){
            int i = mPlayer.getDuration();
            return i;
        }
        return 0;

    }
    public int getCurrentPosition(){
        if(mPlayer != null){
            int i = mPlayer.getCurrentPosition();
            return i;
        }
        return 0;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mPlayer.release();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mPlayer.release();
        mPlayer = null;


    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(isRepeat){
            playSong(currentSong);
        } else if(isShuffle){
            Random r = new Random();
            currentSong = r.nextInt((mMusicList.size() - 1) - 0 + 1) + 0;
            playSong(currentSong);
        } else{
            if(currentSong < (mMusicList.size() - 1)){
                playSong(currentSong + 1);
                currentSong = currentSong + 1;
            }else{
                playSong(0);
                currentSong = 0;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mPath = intent.getStringExtra("path");
        Musics m = (Musics)intent.getSerializableExtra("dataall");
        int p = intent.getIntExtra("currentindex",9999);
        currentSong = p;
        mMusicList =  m.getmMusicAll();
        try {
            mPlayer.setDataSource(mPath);// /storage/emulated/0/Music/Anh Mơ_Anh Khang_-1074080921.mp3
            //  /storage/emulated/0/Music/Anh Đã Bị Lừa_3 Chú Bộ Đội_-1075762802.mp3
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!mPlayer.isPlaying()) {
            int i = 0;
        }
        return mBinder;

    }
    public void nextSong(DataItemMusicCallback listener){
        if(currentSong < (mMusicList.size() - 1)){
            playSong(currentSong + 1);
            currentSong = currentSong + 1;
        }else{
            playSong(0);
            currentSong = 0;
        }
        listener.onOk(currentSong);
//        dataItemMusicCallback.onOk(currentSong);
    }


    public void previousSong(DataItemMusicCallback listener){
        if(currentSong > 0){
            playSong(currentSong - 1);
            currentSong = currentSong - 1;
        }else{
            playSong(mMusicList.size() - 1);
            currentSong = mMusicList.size() - 1;
        }
        listener.onOk(currentSong);
//        dataItemMusicCallback.onOk(currentSong);

    }
    public void playSong(int songIndex){
        try {
            String path = mMusicList.get(songIndex).getmPath();
            mPlayer.reset();
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

