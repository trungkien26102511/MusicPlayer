package com.example.kien.testmusic.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kien.testmusic.Adapter.MusicAdapter;
import com.example.kien.testmusic.Model.Music;
import com.example.kien.testmusic.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private int mType;
    private ArrayList<Music> mMusicList =  new ArrayList<Music>();

    //
    private ArrayList<Music> mMusicListSave =  new ArrayList<Music>();
    private int mTypeSave;
    MusicAdapter adapter;
    //    private static ContentFragment instance;
//    public static ContentFragment getInstance(){
//        if(instance == null){
//            instance =  new ContentFragment();
//        }
//        return instance;
//    }
    public ContentFragment(){

    }
    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public ArrayList<Music> getmMusicList() {
        return mMusicList;
    }

    public void setmMusicList(ArrayList<Music> mMusicList) {
        this.mMusicList = mMusicList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mMusicListSave.addAll(mMusicList);
//        mTypeSave = mType;


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMusicListSave.addAll(mMusicList);
        mTypeSave = mType;
        outState.putInt("type",mType);
    }

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        mMusicList.clear();
//        mMusicList.addAll(mMusicListSave);
//        mTypeSave = savedInstanceState.getInt("type");
//        mType = mTypeSave;
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rcv);
        if (savedInstanceState != null){
            mType = savedInstanceState.getInt("type");
        }
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        adapter = new MusicAdapter(getContext(),mMusicList,mType);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(adapter);
        return view;

    }

}

