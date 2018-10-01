package com.example.harshit.musicplayer;

public class Song {
    private String id;
    private String title;
    private String artist;


    public Song(String songTitle, String songArtist,String songID)

    {
        id = songID;
        title = songTitle;
        artist = songArtist;
    }

    public String getID()
    {
        return id;
    }
    public String getTitle()
    {
        return title;
    }
    public String getArtist()
    {
        return artist;
    }


}