package com.example.kien.testmusic.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.kien.testmusic.DataItemMusicCallback;
import com.example.kien.testmusic.Model.Music;
import com.example.kien.testmusic.Model.Musics;
import com.example.kien.testmusic.R;
import com.example.kien.testmusic.Utilities.MediaUtil;
import com.example.kien.testmusic.service.MusicService;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class PlaySongActivity extends AppCompatActivity {

    protected ImageView img_play;
    protected ImageView img_pause;
    private  ImageView img_next, img_previous;
    protected TextView tv_current;
    protected TextView tv_duration;
    protected SeekBar sb_status;
    protected MusicService mService;
    protected ServiceConnection mConnection;
    protected Handler mHandler;
    protected boolean isBound;
    protected int mDuration;
    private Music mMusic;
    private String mPath;
    private TextView tv_1,tv_2;

    final String MEDIA_PATH = new String("/sdcard/");
    private ArrayList<String> so = new ArrayList<String>();
    Runnable runnable;


    private String path2 = "/storage/emulated/0/Music/Anh Đã Bị Lừa_3 Chú Bộ Đội_-1075762802.mp3";
    private Musics mMusicAll;
    private ArrayList<Music> mMusicList =  new ArrayList<Music>();
    private int mCurrentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        Intent intent = this.getIntent();
        mMusic = (Music) intent.getSerializableExtra("music");
        mMusicAll = (Musics)intent.getSerializableExtra("musicall");
        mCurrentIndex = intent.getIntExtra("currentindex",9999999);
        mMusicList =  mMusicAll.getmMusicAll();
        initView();
        String tv1 = mMusic.getmSong();
        String tv2 = mMusic.getmArtist();
        mPath = mMusic.getmPath();
        tv_1.setText(tv1);
        tv_2.setText(tv2);
        initServiceConnection();
        initView();
        initHandler();
    }
    protected void initServiceConnection(){
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                MusicService.MusicBinder musicBinder = (MusicService.MusicBinder)binder;
                mService = musicBinder.getService();
                isBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBound = false;
                mHandler = null;

            }
        };

        Intent intent = new Intent(this,MusicService.class);
        intent.putExtra("path",mPath);
        intent.putExtra("dataall",mMusicAll);
        intent.putExtra("currentindex",mCurrentIndex);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    protected void initView(){
        img_pause = (ImageView)findViewById(R.id.img_pause);
        img_play = (ImageView)findViewById(R.id.img_play);
        img_next = (ImageView)findViewById(R.id.img_next);
        img_previous = (ImageView)findViewById(R.id.img_previous);
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAction();
            }
        });
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousAction();
            }
        });
        tv_1 = (TextView)findViewById(R.id.tv_1);
        tv_2 = (TextView)findViewById(R.id.tv_2);
        tv_current = (TextView)findViewById(R.id.tv_current);
        tv_duration = (TextView)findViewById(R.id.tv_duration);
        sb_status = (SeekBar)findViewById(R.id.sb_status);
        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAction();

            }
        });
        img_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAction();

            }
        });
        sb_status.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int duration = mService.getDuration();
                mDuration = duration;
                int miliseconds = MediaUtil.convertPercenttoMiliseconds(progress, mDuration);
                mService.seekTo(miliseconds);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void previousAction() {
        mService.previousSong(new DataItemMusicCallback() {
            @Override
            public void onOk(int position) {
                String tv1 =  mMusicList.get(position).getmSong();
                String tv2 =  mMusicList.get(position).getmArtist();
                tv_1.setText(tv1);
                tv_2.setText(tv2);
            }
        });

        sb_status.setProgress(0);
        sb_status.setMax(100);
        updateSeekbar();

    }

    private void nextAction() {
        mService.nextSong(new DataItemMusicCallback() {
            @Override
            public void onOk(int position) {
                String tv1 =  mMusicList.get(position).getmSong();
                String tv2 =  mMusicList.get(position).getmArtist();
                tv_1.setText(tv1);
                tv_2.setText(tv2);
            }
        });

        sb_status.setProgress(0);
        sb_status.setMax(100);
        updateSeekbar();
    }

    protected void initHandler(){
        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
                mHandler.postDelayed(this,1000);

            }
        };
        if(this instanceof PlaySongActivity){
            mHandler.postDelayed(runnable,1000);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    protected void updateSeekbar(){
        if(isBound){
            int current = mService.getCurrentPosition();
            int duration = mService.getDuration();
            mDuration = duration;
            String s_current = MediaUtil.convertMilisecondstoTime(current);
            tv_current.setText(s_current);
            String s_duration = MediaUtil.convertMilisecondstoTime(duration);
            tv_duration.setText(s_duration);
            int percent = MediaUtil.caculatePercent(current,duration);
            sb_status.setMax(100);
            sb_status.setProgress(percent);
        }

    }


    protected void playAction(){
        mService.playMusic();
    }
    protected void pauseAction(){
        mService.pauseMusic();
    }
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }
}