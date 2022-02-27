package com.company;

import java.sql.Date;
import java.sql.Time;


public class PodCast {
    private Song song;
    private Date publishedOn;


    public PodCast(Song song, Date publishedOn) {
        this.song = song;
        this.publishedOn = publishedOn;
    }


    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Date getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(Date publishedOn) {
        this.publishedOn = publishedOn;
    }
}