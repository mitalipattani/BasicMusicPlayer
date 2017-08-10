package com.ciccc_cirac.basicmusicplayer;

/**
 * Created by CICCC-CIRAC on 2017-08-10.
 */

public class Song {
    private long id;
    private String title;
    private String artist;
    public Song(long songID, String songTitle, String songArtist) {
        id=songID;
        title=songTitle;
        artist=songArtist;
    }
    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
}
