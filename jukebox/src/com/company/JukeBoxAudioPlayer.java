package com.company;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Class to play music
public class JukeBoxAudioPlayer {

    // declaring static variables to have access in all the static methods
    private static long currentFrame;
    private static AudioInputStream audioInputStream;
    private static String filePath;
    private static String trackName;
    private static String playListName;
    private static List<Playlist> availableList;

    // to display all the playlist
    public static void displayPlaylist() throws Exception {
        PlaylistDriver pl = new PlaylistDriver();
        System.out.println("\n\n************************************************** Displaying the Available Playlists **************************************************\n");
        String query = "select * from allPlaylists";
        availableList = pl.readAndDisplayAllPlaylists(query);
        //calling the method to get userinput of audio to be played
        getAudioToPlay(availableList);
    }

    //method to get userinput of song
    public static void getAudioToPlay(List<Playlist> playlistList) throws Exception {
        //get user input to get the path of the song from db
        Scanner audioChoice = new Scanner(System.in);
        System.out.println("Enter the PLAYLIST NAME you wish to Play: ");
        playListName = audioChoice.nextLine();
        //filter and display the playlist as per playlist name
        filterAsPerPlaylistName(playListName,playlistList);

        getTrackIdAndPlaySong(playListName, playlistList);
    }

    //method to get trackid and play song
    public static void getTrackIdAndPlaySong(String playListName,List<Playlist> playlistList) throws Exception {
        //get user input to get the path of the song from db
        Scanner audioChoice = new Scanner(System.in);
        //get the user input
        System.out.println("Enter the TRACK_ID of the Audio : ");
        int audioId = audioChoice.nextInt();


        //Creating Database Connection to fetch audio details from db
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "Newlife7#");

        //Create statement
        Statement st = con.createStatement();

        //query to fetch the path from db
        String pathQuery = "Select path, songName from songs where songid = '" + audioId + "'";
        ResultSet rsAlbumSongs = st.executeQuery(pathQuery);

        while (rsAlbumSongs.next()) {
            filePath = rsAlbumSongs.getString(1);
            trackName = rsAlbumSongs.getString(2);
        }
        System.out.println("You have selected \n" + "*"+trackName +"*\n");
        System.out.println("Playing Now...\n ");
        //call the method to play the file
        displayPlayerMenuAndPlay(filePath);

    }

    //method to filter the playlists as per the name of the playlist
    public static void filterAsPerPlaylistName(String playListName,List<Playlist> playlistList) {
        PlaylistDriver playlistDriver = new PlaylistDriver();
        // filter the list as per name of the playlist
        Predicate<Playlist> listFilter = (lf)->lf.getPlaylistName().toLowerCase().startsWith(playListName.toLowerCase());
        List<Playlist> filteredPlayList = playlistList.stream().filter(listFilter).collect(Collectors.toList());
        //Check if the list is empty and if yes inform the user to enter correct id
        if(filteredPlayList.size()==0){
            System.out.println("Playlist Not available, Enter the name from the above list");
            try {
                getAudioToPlay(availableList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //if the list is not empty, the display method is called to display the playlist details
        else{
            playlistDriver.displayAllPlaylists(filteredPlayList);
        }
    }

    //to display player menu
    public static void displayPlayerMenuAndPlay(String filePath) throws IOException, LineUnavailableException {
        //Create a clip interface reference
        Clip audioClip;
        int choice = 0;
        String playMusic = "";
        //Loading scanner class to take inout from user
        Scanner audioMenu = new Scanner(System.in);
        try {
            //get the absolute file obj
            File audioFile = new File(filePath).getAbsoluteFile();
            audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();

            //Open the audio clip
            audioClip.open(audioInputStream);

            //The song autoplays once the track id is given
            audioClip.start();

            //Option for Music Player
            do {
                System.out.format("%-10s %-15s %-15s %-15s %-15s %-15s \n", "1. play", "2. pause", "3. restart", "4. previous", "5. forward", "6. Stop");

                choice = audioMenu.nextInt();
                if (choice == 1) {
                    playAudio(audioClip);
                }
                if (choice == 2) {
                    pauseAudio(audioClip);
                }
                if (choice == 3) {
                    restartAudio(audioClip);
                }
                if (choice == 4) {
                    reverseAudio(audioClip);
                }
                if (choice == 5) {
                    forwardAudio(audioClip);
                }
                if (choice == 6) {
                    stopAudio(audioClip);
                }
                //will exit the play menu
            } while (choice != 6);
            //find if the user wants to play another song from the same playlist
            System.out.println("Song has been stopped...\n\nDo you want to play another song from " + playListName+ " ? (y/n)");
            //gets user option
            playMusic = audioMenu.next();
            //if yes  will enter the condition ...
            if(playMusic.equalsIgnoreCase("y")) {
                //...and call the below method to display the same playlist
                filterAsPerPlaylistName(playListName,availableList);
            }
            //If not exit the playlist
            else{
                System.out.println("***************************************************************** EXITING " +playListName.toUpperCase() + " *****************************************************************\n\n");
               }
        } catch (UnsupportedAudioFileException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //method to play
    public static void playAudio(Clip clip) {
        clip.start();
    }

    //method to pause
    public static void pauseAudio(Clip clip) {
        currentFrame = clip.getMicrosecondPosition();
        clip.stop();
    }

    //method to resume
    public static void restartAudio(Clip clip) {
        clip.stop();
        currentFrame = 0;
        clip.setMicrosecondPosition(currentFrame);
        clip.start();
    }

    //method to reverse
    public static void reverseAudio(Clip clip) {
    //fetching the current position in microseconds and converting to secs
        currentFrame = clip.getMicrosecondPosition()/1_000_000;
        clip.stop();

        //reducing the timeFrame to 5 secs
        long backward = currentFrame-5;
        // setting the position 5secs reverse
        System.out.println("Reverses 10secs from: " +currentFrame + " secs ================= Playing from " + backward + " sec\n\n" );
        // setting the position 5secs reverse in microseconds
        clip.setMicrosecondPosition(backward*1_000_000);
        playAudio(clip);
    }

    //method to forward
    public static void forwardAudio(Clip clip) {
        //fetching the current position in microseconds and converting to secs
        currentFrame = clip.getMicrosecondPosition()/1_000_000;
        //stop the clip
        clip.stop();

        //display at which the player has stopped
        long fwd = currentFrame+10;

        // setting the position 10secs fwd in microseconds
        clip.setMicrosecondPosition(fwd*1_000_000);
        System.out.println("Forwarded 10secs from: " +currentFrame + " secs ================= Playing from " + fwd + " sec\n\n" );
        playAudio(clip);
    }
    //method to forward
    public static void stopAudio(Clip clip) {
        currentFrame = 0;
        clip.stop();
    }
}





