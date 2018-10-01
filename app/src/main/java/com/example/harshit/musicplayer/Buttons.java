package com.example.harshit.musicplayer;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.harshit.musicplayer.MainActivity.mp;

import static com.example.harshit.musicplayer.MainActivity.songList;


public class Buttons extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buttons,container,false);
        Button b1 = (Button) view.findViewById(R.id.play);
        Button b2 = (Button) view.findViewById(R.id.pause);
        Button b4 = (Button) view.findViewById(R.id.stop);
        //Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
      b2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mp.pause();
          }
      });

      b1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mp.start();
          }
      });
      b4.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mp.stop();


          }
      });


        return view;


    }
}
