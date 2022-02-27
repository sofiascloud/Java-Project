package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SongDriver {

    public static List<Song> readAndDisplaySongs(String query) throws Exception {

        //Creating Database Connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "Newsky7#");

        //Create the statement
        Statement st = con.createStatement();
        ResultSet rsSongList = st.executeQuery(query);

        //Create an arrayList to store the songs
        List<Song> songList = new ArrayList();
        while (rsSongList.next()) {
            songList.add(new Song(rsSongList.getInt(1),rsSongList.getString(2), rsSongList.getString(3), rsSongList.getString(4),rsSongList.getString(5), rsSongList.getTime(6)));
        }
        //To display all the songs
        displaySongs(songList);
        return songList;
    }

    //find the user's choice and call the relevant method to find the song
    public static void searchSong(int choice, List<Song> songs) {
        //import scanner class to get the user input
        Scanner searchScanner = new Scanner(System.in);
        //get user input to search
        System.out.println("Type your search here...");
        String search = searchScanner.next();

        if (choice == 1) {
            searchSongByName(search, songs);
        }
        if (choice == 2) {
            searchSongByAlbum(search, songs);
        }
        if (choice == 3) {
            searchSongByArtist(search, songs);
        }
        if (choice == 4) {
            searchSongByGenre(search, songs);
        }

    }

    //method to search a song by song name
    public static int searchSongByName(String songName, List<Song>songs) {
        // filter the list as per song name
        Predicate<Song> nameFilter = (nf)->nf.getSongName().toLowerCase().startsWith(songName.toLowerCase());
        List<Song> songList = songs.stream().filter(nameFilter).collect(Collectors.toList());
        //display the songs
        if(songList.size()!=0) {
            displaySongs(songList);
            return 1;
        }
        else {
            System.out.println("\n\n\nSorry we dont have this track, Try again");
            return 0;
        }
    }

    //method to search a song by album name
    public static int searchSongByAlbum(String album, List<Song>songs) {
        // filter the list as per album name
        Predicate<Song> albumFilter = (af)->af.getAlbum().toLowerCase().startsWith(album.toLowerCase());
        List<Song> songList = songs.stream().filter(albumFilter).collect(Collectors.toList());
        //display the songs
        if(songList.size()!=0) {
            displaySongs(songList);
            return 1;
        }
        else {
            System.out.println("\n\n\nSorry we dont have " + album + " with us. Try again");
            return 0;
        }
    }

    //method to search a song by artist name
    public static int searchSongByArtist(String artist, List<Song>songs) {
        // filter the list as per artist name
        Predicate<Song> artistFilter = (filterArtist)->filterArtist.getArtist().toLowerCase().startsWith(artist.toLowerCase());
        List<Song> songList = songs.stream().filter(artistFilter).collect(Collectors.toList());
        //display the songs
        if(songList.size()!=0) {
            displaySongs(songList);
            return 1;
        }
        else {
            System.out.println("\n\n\nSorry we dont have a track by " + artist + ". Try again");
            return 0;
        }
    }

    //method to search a song by genre
    public static int searchSongByGenre(String genre, List<Song>songs) {
        // filter the list as per genre
        List<Song> songList = new ArrayList<>();
        Predicate<Song> genreFilter = (gf)->gf.getGenre().toLowerCase().startsWith(genre.toLowerCase());
        songList = songs.stream().filter(genreFilter).collect(Collectors.toList());
        //display the songs
        if(songList.size()!=0) {
            displaySongs(songList);
            return 1;
        }
        else {
            System.out.println("\n\n\nSorry we dont have " +genre + ". Try again");
            return 0;
        }
    }


    //Display Song details
    public static void displaySongs(List<Song> songs) {
        System.out.format("%-15s %-25s %-25s %-35s %-15s %-15s \n", "Trackid","Song Name", "Album", "Artist", "Genre", "Duration");
        System.out.println("<----------------------------------------------------------------------------------------------------------------------------------->");

        Consumer<Song> displaySongs = (dSongs) -> System.out.format("%-15s %-25s %-25s %-35s %-15s %-15s \n", dSongs.getSongId(),dSongs.getSongName(), dSongs.getAlbum(), dSongs.getArtist(), dSongs.getGenre(), dSongs.getDuration());
        songs.forEach(displaySongs);
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");
    }

}
