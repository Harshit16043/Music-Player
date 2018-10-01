package com.example.harshit.musicplayer;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Song> songList;
    private ListView List;

    static MediaPlayer mp = new MediaPlayer();
    Button down;
    DownloadManager downloadManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List = (ListView)findViewById(R.id.List);
        songList = new ArrayList<Song>();



        getSongList();



        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        final SongAdapter songAdt = new SongAdapter(this, songList);
        List.setAdapter(songAdt);


        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String PATH_TO_FILE = songList.get(position).getID();
                /*Context context = getApplicationContext();
                CharSequence text = PATH_TO_FILE;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();*/
                mp.reset();
                try {

                    mp.setDataSource(PATH_TO_FILE);
                    mp.prepare();
                    mp.start();


                } catch (IOException e) {
                    e.printStackTrace();
                }



            }
        });



        android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.fragment1, new Buttons());
        tx.commit();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);





        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

             // boolean isAirplaneModeOn = intent.getBooleanExtra("state", false);

              if(intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED))
              {


              if(intent.getBooleanExtra("state",false))

              {
                  context = getApplicationContext();
                  CharSequence text = "AEROPLANE MODE ON";
                  int duration = Toast.LENGTH_SHORT;

                  Toast toast = Toast.makeText(context, text, duration);
                  toast.show();

              }
              else
              {
                  context = getApplicationContext();
                  CharSequence text = "AEROPLANE MODE OFF";
                  int duration = Toast.LENGTH_SHORT;

                  Toast toast = Toast.makeText(context, text, duration);
                  toast.show();


              }




              }



                if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
                {
                    context = getApplicationContext();
                    CharSequence text = "BOOT COMPLETED";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

                if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED))
                {
                    context = getApplicationContext();
                    CharSequence text = "Power Connected";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

                if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED))
                {
                    context = getApplicationContext();
                    CharSequence text = "Power disconnected";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                }


            }


        };
        this.registerReceiver(receiver, intentFilter);

checknet();

            down=(Button)findViewById(R.id.download);
            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    downloadManager= (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse("http://faculty.iiitd.ac.in/~mukulika/s1.mp3 ");
                    DownloadManager.Request request= new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir("/Download", "s1.mp3");

                    Long reference = downloadManager.enqueue(request);
                }
            });



    }

    public void checknet()
    {
        ConnectivityManager net = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo in = net.getActiveNetworkInfo();
        if(in!= null && in.isConnected())
        {
            Context context = getApplicationContext();
            CharSequence text = "NETWORK CONNECTED";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {

            Context context = getApplicationContext();
            CharSequence text = "NETWORK is not CONNECTED";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }


    public ArrayList<Song> getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection= MediaStore.Audio.Media.IS_MUSIC + "!=0";

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns

            do {
                String name = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                String artist = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String id = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                //add songs to list

                Song s = new Song(name, artist, id);
                songList.add(s);


            }  while (musicCursor.moveToNext());
        }

        musicCursor.close();
        return null;
    }




}
