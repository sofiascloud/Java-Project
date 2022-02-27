package com.company.test;

import com.company.Song;
import com.company.SongDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongDriverTest {

    SongDriver songDriver;
    List<Song> songList = new ArrayList<>();


    @BeforeEach
    public void setUp() throws Exception {


        Song song1 = new Song(135,"Thooriga","Navarasa","Karthik", "Romance",null);
        Song song2 = new Song(100,"Stay","Stay","The Kid LAROI,Justin Bieber", "Pop", null);
        songList.add(song1);
        songList.add(song2);

    }
    //given song name test for output
    @Test
    public void givenSongNameDisplayAudio() {

        assertEquals(1, songDriver.searchSongByName("Thooriga",songList));
        assertEquals(0, songDriver.searchSongByName("SunShine",songList));

        assertEquals(1, songDriver.searchSongByName("Stay",songList));

    }
    //given album name test for output
    @Test
    public void givenAlbumNameDisplayAudio() {

          assertEquals(1, songDriver.searchSongByAlbum("Navarasa", songList));
        // wrong album name
        assertEquals(0, songDriver.searchSongByAlbum("BackStreet", songList));
        assertEquals(1, songDriver.searchSongByAlbum("Stay", songList));

    }



    //given genre name test for output
    @Test
    public void givenGenreDisplayAudio() {

        assertEquals(1, songDriver.searchSongByGenre("Romance", songList));
        //assertEquals(0, songDriver.searchSongByGenre("Carnatic", songList));
        //assertEquals(1, songDriver.searchSongByGenre("Pop", songList));
    }

    @AfterEach
    public void tearDown()  {
        songDriver = null;
    }

}
