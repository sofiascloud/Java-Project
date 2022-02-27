package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;


public class PlaylistDriver {

    public static List<Playlist> readAndDisplayAllPlaylists(String query) throws Exception {

        //Creating Database Connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "Newsky7#");

        //Create the statement
        Statement st = con.createStatement();
        ResultSet rsPlayList = st.executeQuery(query);
        List<Playlist> allPlayLists = new ArrayList();
        //inserting values into allplaylist
            while (rsPlayList.next()) {
                //creating an obj reference for insertion purpose
                Song songDetails = new Song(rsPlayList.getInt(1),rsPlayList.getString(2), rsPlayList.getString(3), rsPlayList.getString(4),rsPlayList.getString(5),rsPlayList.getTime(7));
                PodCast pcObj = new PodCast(songDetails,rsPlayList.getDate(6));
                allPlayLists.add(new Playlist(pcObj, rsPlayList.getString(8), rsPlayList.getString(9)));
            }
        //To display all the songs
            displayAllPlaylists(allPlayLists);
            return allPlayLists;
    }

    //method to create a playlist
    public static void createPlaylist(List<Playlist> playLists) throws Exception {
        String userOption = "";
        List <Playlist> latestList = new ArrayList<>();
        System.out.println("Enter a Name for Playlist");
        //Creating Database Connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "Newsky7#");

        //get user input to create playlist
        Scanner listDetails = new Scanner(System.in);
        // to get user i/p in runtime
        PreparedStatement plCreation = con.prepareStatement("insert into playlistNames(playlistName) values(?)");
        String playlistName = listDetails.next();

        // updating the sql table
        plCreation.setString(1, playlistName);
        plCreation.executeUpdate();
        System.out.println("Playlist " + playlistName + " Created Successfully");
        do {
            System.out.println("Do you want to add your selections in it? (y/n)");
            userOption = listDetails.next();
            if (userOption.equalsIgnoreCase("y")) {
                insertIntoPlayList(playlistName, playLists);

                String query = "select * from allPlaylists";
                readAndDisplayAllPlaylists(query);
            }
        } while (!userOption.equalsIgnoreCase("n"));
    }

    public static void insertIntoPlayList(String playlistName, List<Playlist> playLists) throws Exception {

        int playlistId = 0;
        int songId = 0;
        int podId = 0;

        //Get the category of input from user
        String userChoice = "";

        Scanner insertDetails = new Scanner(System.in);
        //Creating Database Connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "Newsky7#");
        //Create statement
        Statement st = con.createStatement();


        //get the playlistid
        String listIdQuery = "select playlistid from playlistnames where playlistname = '" + playlistName + "'";
        ResultSet idResult = st.executeQuery(listIdQuery);
        //fetch the playlist id from table
            while (idResult.next()) {
                playlistId = idResult.getInt(1);
            }
             System.out.println("What to do you want to add in " + playlistName.toUpperCase() + " ? (Song/Podcast/Album)");
                userChoice = insertDetails.nextLine();

                //to display details based on userhcoice
                //if song
                if (userChoice.equalsIgnoreCase("Song")) {

                    //display all audios
                    SongDriver sd = new SongDriver();
                    String songQuery = "select * from songlist";
                    sd.readAndDisplaySongs(songQuery);
                    //get user input of song id
                    System.out.println("Enter TrackId of Song: ");
                    songId = insertDetails.nextInt();

                    //insert the details in the playlists table
                    PreparedStatement psInsert = con.prepareStatement("insert into playlists(playlistid, songid) values(?,?)");
                    psInsert.setInt(1, playlistId);
                    psInsert.setInt(2, songId);
                    psInsert.executeUpdate();
                    System.out.println("Song Added\n");
                    //Display the playlist after adding the track
                    String query = "select * from allPlaylists";
                    readAndDisplayAllPlaylists(query);
                }
                //display all podcast
                if (userChoice.equalsIgnoreCase("Podcast")) {
                    PodCastDriver pcd = new PodCastDriver();
                    String podcastListQuery = "select * from podCastList";
                    pcd.readAndDisplayPodCast(podcastListQuery);
                    System.out.println("Enter Track id of Podcast: ");
                    podId = insertDetails.nextInt();
                    PreparedStatement psInsert = con.prepareStatement("insert into playlists(playlistid, songid) values(?,?)");
                    psInsert.setInt(1, playlistId);
                    psInsert.setInt(2, podId);
                    psInsert.executeUpdate();
                    System.out.println("PODCAST ADDED\n\n");
                    String query = "select * from allPlaylists";
                    readAndDisplayAllPlaylists(query);
                }
                if (userChoice.equalsIgnoreCase("Album")) {
                    //display all audios
                    SongDriver sd = new SongDriver();
                    //Query to fetch detail
                    String songQuery = "select * from songlist";
                    sd.readAndDisplaySongs(songQuery);

                    System.out.println("Enter Album name: ");
                    String album = insertDetails.nextLine();
                    System.out.println("You have entered " + album);
                    //fetching the user given album name
                    String albumQuery = "Select songid from songs where album = '" + album + "'";
                    ResultSet rsAlbumSongs = st.executeQuery(albumQuery);

                    while (rsAlbumSongs.next()) {
                        PreparedStatement psInsert = con.prepareStatement("insert into playlists(playlistid, songid) values(?,?)");
                        songId = rsAlbumSongs.getInt(1);
                        psInsert.setInt(1, playlistId);
                        psInsert.setInt(2, songId);
                        psInsert.executeUpdate();
                    }
                    System.out.println("Album inserted Successfully");
                    String query = "select * from allPlaylists";
                    readAndDisplayAllPlaylists(query);

                }
        }


      //Display playlist details
        public static void displayAllPlaylists(List<Playlist> playlist) {
            System.out.format("%-10s %-20s %-25s %-22s %-30s %-15s %-15s \n", "Trackid","Playlist Name", "Audio", "Album", "Artist", "Duration", "Song/Podcast");
            System.out.println("<------------------------------------------------------------------------------------------------------------------------------------------------------->");

            Consumer<Playlist> displayAllPlaylists = (dlist) -> System.out.format("%-10s %-20s %-25s %-22s %-30s %-15s %-15s \n", dlist.getAllAudios().getSong().getSongId(),dlist.getPlaylistName(), dlist.getAllAudios().getSong().getSongName(),dlist.getAllAudios().getSong().getAlbum(),dlist.getAllAudios().getSong().getArtist(),dlist.getAllAudios().getSong().getDuration(),dlist.getAudioType());
            playlist.forEach(displayAllPlaylists);
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------");
         }

}
