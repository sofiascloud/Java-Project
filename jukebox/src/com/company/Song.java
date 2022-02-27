package com.company;

import java.sql.Time;

public class Song {

    private int songId;
    private String songName;
    private String album;
    private String artist;
    private String genre;
    private Time duration;


    public Song(int songId, String songName, String album, String artist, String genre, Time duration) {
        this.songId =songId;
        this.songName = songName;
        this.album = album;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;

    }


    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }


}
