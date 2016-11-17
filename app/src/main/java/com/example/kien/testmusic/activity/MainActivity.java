package com.example.kien.testmusic.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kien.testmusic.Fragment.ContentFragment;
import com.example.kien.testmusic.Model.Music;
import com.example.kien.testmusic.Model.Musics;
import com.example.kien.testmusic.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ViewPager tabContentPager;
    private TabsAdapter tabAdapter;
    private TabLayout tabLayout;
    private ArrayList<Music> mMusicList =  new ArrayList<Music>();

    private List<String> mSongs = new ArrayList<String>();

    public static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 7;
    private ContentResolver cr;

    private Musics mMusicAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG," onCreate()");

        cr = getContentResolver();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                try {
                    ((TextView) tab.getCustomView().findViewById(R.id.tv_tabs_layout_title)).setTextColor(getResources().getColor(R.color.colorAccent));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {
                    ((TextView) tab.getCustomView().findViewById(R.id.tv_tabs_layout_title)).setTextColor(Color.parseColor("#000000"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabContentPager = (ViewPager) findViewById(R.id.view_pager);
        initContentPage();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_MEDIA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        initData();

//        makeFakeMusicList();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG," onRequestPermissionsResult()");
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_MEDIA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }


    private void initData() {
        Log.d(TAG," initData()");

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
            mSongs.add(cursor.getString(0) + "||"
                    + cursor.getString(1) + "||"
                    + cursor.getString(2) + "||"
                    + cursor.getString(3) + "||"
                    + cursor.getString(4) + "||"
                    + cursor.getString(5));
            Music o = new Music(cursor.getString(2), "Album " + i, cursor.getString(1),cursor.getString(3));
            i++;
            mMusicList.add(o);
        }
        mMusicAll =  new Musics(mMusicList);
        //==============================================
    }


//    private void makeFakeMusicList() {
//        for(int i = 0; i <songs.size(); i++){
//            Music o = new Music("Song " + i, "Album " + i, "Artist " + i,songs.get(i));
//            mMusicList.add(o);
//        }
//    }

    private void initContentPage() {
        Log.d(TAG," initContentPage()");
        tabAdapter = new TabsAdapter(getSupportFragmentManager());
        tabContentPager.setAdapter(tabAdapter);
        tabContentPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(tabContentPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(tabAdapter.getTabView(i));
            TextView mTitle = (TextView) tab.getCustomView().findViewById(R.id.tv_tabs_layout_title);
            if (i == 0 && mTitle != null) {
                mTitle.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        }
    }

    class TabsAdapter extends FragmentPagerAdapter {
        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
//                    ContentFragment f1 = ContentFragment();.getInstance();
                    ContentFragment f1 = new ContentFragment();
                    f1.setmType(1);
                    f1.setmMusicList(mMusicList);
                    return f1;
                case 1:
                    ContentFragment f2 = new ContentFragment();
//                    ContentFragment f2 = ContentFragment.getInstance();
                    f2.setmType(2);
                    f2.setmMusicList(mMusicList);
                    return f2;
                case 2:
                    ContentFragment f3 = new ContentFragment();
//                    ContentFragment f3 = ContentFragment.getInstance();
                    f3.setmType(3);
                    f3.setmMusicList(mMusicList);
                    return f3;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Songs";
                case 1:
                    return "Artirst";
                case 2:
                    return "Actor";

            }
            return "";
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_tab_custom, null);
            TextView tvMenuItem = (TextView) v.findViewById(R.id.tv_tabs_layout_title);
            ImageView img = (ImageView)v.findViewById(R.id.img);

            if (position == 0) {
                tvMenuItem.setText(getPageTitle(position));
            } else if (position == 1) {

                tvMenuItem.setText(getPageTitle(position));
            } else if (position == 2) {
                tvMenuItem.setText(getPageTitle(position));
            }
            return v;
        }
    }


}