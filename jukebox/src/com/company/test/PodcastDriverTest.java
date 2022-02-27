package com.company.test;

import com.company.PodCast;
import com.company.PodCastDriver;
import com.company.Song;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PodcastDriverTest {

    //Obj refence of podcastdriver class.
    PodCastDriver podCastDriver;
    List<PodCast> podCastList= new ArrayList<>();

    //19 - 24//declaring and initializing for published date
    String date1="2021-10-01";
    Date publisheDate1 =Date.valueOf(date1);

    String date2="2021-10-03";
    Date publisheDate2 =Date.valueOf(date2);


    @BeforeEach
    public void setUp()  {

        //insertion of value 1
        Song pod1 = new Song(109,"Design your Thinking","The Inspiring Talk","Dr.Pavan Soni", "Education", null);
        PodCast podCast1 = new PodCast(pod1,publisheDate1);

        //insertion of value 2
        Song pod2 = new Song(112,"Be Number 1 to Yourself","1 Minute Motivation","Rahul Dravid", "Health & Wellness", null);
        PodCast podCast2 = new PodCast(pod2,publisheDate2);

        //adding the value to the list
        podCastList.add(podCast1);
        podCastList.add(podCast2);

    }
    //given song name test for output
    @Test
    public void givenCelebrityNameDisplayAudio() {

        assertEquals(1, podCastDriver.searchByCelebrity("Rahul Dravid",podCastList));
        assertEquals(1, podCastDriver.searchByCelebrity("Rahul Dravid",podCastList));
    }
    //given album name test for output
    @Test
    public void givenPublishedDateDisplayAudio() {
        assertEquals(1, podCastDriver.searchByPublishedDate(publisheDate1, podCastList));
        assertEquals(1, podCastDriver.searchByPublishedDate(publisheDate2, podCastList));

    }
    @AfterEach
    public void tearDown() {
        podCastDriver = null;
    }

}
