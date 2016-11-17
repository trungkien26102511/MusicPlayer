package com.example.kien.testmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kien.testmusic.Model.Album;
import com.example.kien.testmusic.Model.Artist;
import com.example.kien.testmusic.Model.Music;
import com.example.kien.testmusic.Model.Musics;
import com.example.kien.testmusic.Model.Song;
import com.example.kien.testmusic.R;
import com.example.kien.testmusic.activity.PlaySongActivity;

import java.util.ArrayList;

/**
 * Created by Kien on 11/9/2016.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{
    Context context;
    ArrayList<Music> mMucsicList = new ArrayList<Music>();
    ArrayList<Song> mSongList = new ArrayList<Song>();
    ArrayList<Artist> mArtistList = new ArrayList<Artist>();
    ArrayList<Album> mAlbumList =  new ArrayList<Album>();
    Musics mMusicAll;
    int mType;

    public MusicAdapter(Context context, ArrayList<Music> list, int type){
        this.context = context;
        this.mMucsicList = list;
        this.mType = type;
        parseFromMusic();
        mMusicAll = new Musics(mMucsicList);
    }

    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song_layout,parent,false);

        return new ViewHolder(view);
    }
//        @Override
//    public int getItemViewType(int position) {
//        return mMucsicList.get(position);
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mType== 1){
            holder.tv_1.setText(mSongList.get(position).getmSong());
            holder.tv_2.setText(mSongList.get(position).getmArtist());
        }else if(mType == 2){
            holder.tv_1.setText(mArtistList.get(position).getmSong());
            holder.tv_2.setText(mArtistList.get(position).getmArtist());
        }else if(mType == 3){
            holder.tv_1.setText(mAlbumList.get(position).getmSong());
            holder.tv_2.setText(mAlbumList.get(position).getmAlbum());
        }
    }


    @Override
    public int getItemCount() {
        return mMucsicList != null ? mMucsicList.size() : 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_1;
        TextView tv_2;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_1 = (TextView)itemView.findViewById(R.id.tv_1);
            tv_2 = (TextView)itemView.findViewById(R.id.tv_2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(context, PlaySongActivity.class);
                    Music music = (Music) mMucsicList.get(getAdapterPosition());
                    intent.putExtra("music",music);
                    intent.putExtra("musicall",mMusicAll);
                    intent.putExtra("currentindex",getAdapterPosition());
                    context.startActivity(intent);
//                    context.startActivity(new Intent(context, PlaySongActivity.class));
                }
            });
        }
    }
    public void parseFromMusic(){
        for(int i = 0; i< mMucsicList.size(); i++){
            Song s = new Song(mMucsicList.get(i).getmSong(),mMucsicList.get(i).getmArtist());
            mSongList.add(s);
            Artist a = new Artist(mMucsicList.get(i).getmSong(),mMucsicList.get(i).getmArtist());
            mArtistList.add(a);
            Album al = new Album(mMucsicList.get(i).getmSong(),mMucsicList.get(i).getmAlbum());
            mAlbumList.add(al);
        }

    }
}
