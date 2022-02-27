/*
JukeBox Project
 */

package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JukeBox {

    //main method of Juke Box
    public static void main(String[] args) throws Exception {

        System.out.println();
        System.out.println("******************************************************************** \uD83C\uDFB5 JukeBox Project \uD83C\uDFB5 **************************************************************************");

        //import Scanner class to take user input
        Scanner scanner = new Scanner(System.in);
        List<Song> songList= new ArrayList<>();
        List<PodCast> podCastList = new ArrayList<>();
        String userChoice= "";
        int searchChoice = 0;
        int menuChoice=0;
        do {

            System.out.println("Hi!!! Welcome....\n\nPick a Letter from below:");
            //get user input to work on songs, podcasts and playlists
            System.out.format("%-5s %-10s %-5s %-10s %-5s %-10s %-5s %-10s \n", "Songs", "--> S  \t||\t", "Podcast", "--> P  \t||\t","Playlists", "--> PL  \t||\t", "Exit", "--> E");

            //Custom Exceptions
            try{
                userChoice = scanner.next();
                if((!userChoice.equalsIgnoreCase("s"))&&(!userChoice.equalsIgnoreCase("p"))&&(!userChoice.equalsIgnoreCase("pl"))&&(!userChoice.equalsIgnoreCase("e"))){
                    throw new IllegalArgumentException("Invalid Selection");
                }
            }catch (IllegalArgumentException ex){
                //to find the error details
                ex.printStackTrace();
            }

            //Menu for songs
            if (userChoice.equalsIgnoreCase("S")) {
                System.out.println("\nDISPLAYING ALL SONGS...");
                //Creating Obj reference
                SongDriver sd = new SongDriver();
                String songListQuery = "select * from songlist";
                songList = sd.readAndDisplaySongs(songListQuery);

                //subMenu after selecting 'SONG'
                //do while loop to display the song menus till exit value is given by the user.
                do {
                    //search a song
                    System.out.println("\nSEARCH A SONG \n1.Name\t 2.Album\t 3.Artist\t 4.Genre\t 0.Go back to Main Menu  \n (Enter the number to choose your option of search)");
                    searchChoice = scanner.nextInt();
                    if ((searchChoice == 1) || (searchChoice == 2) || (searchChoice == 3) || (searchChoice == 4)) {
                        sd.searchSong(searchChoice, songList);
                    } else {
                        if (searchChoice != 0)
                            System.out.println("Please enter a valid option\n\n");
                    }
                } while (searchChoice != 0);
            }
            //Menu for Podcast
            else if (userChoice.equalsIgnoreCase("P")) {
                    System.out.println("\nDISPLAYING ALL PODCASTS...\n");
                    //Creating Obj reference
                    PodCastDriver pcd = new PodCastDriver();

                    String podcastListQuery = "select * from podCastList";
                    podCastList = pcd.readAndDisplayPodCast(podcastListQuery);

                //do while loop to display the podcast menus till exit value is given by the user.
                    do {
                        //SubMenu after selecting 'Podcast'
                        // search a Podcast
                        System.out.println("\nSEARCH A PODCAST \n1.Celebrity\t 2.Published Date\t 0.Go back to Main Menu\n (Enter the number to choose your option)");
                        searchChoice = scanner.nextInt();
                        if (searchChoice == 1 || searchChoice == 2) {
                            pcd.searchPodcast(searchChoice, podCastList);
                        } else {
                            if (searchChoice != 0)
                                System.out.println("Please enter a valid option");
                        }
                    } while (searchChoice != 0);
                }

            //Menu for Playlist
            else{
                if(userChoice.equalsIgnoreCase("pl")){

                    PlaylistDriver pd = new PlaylistDriver();
                    System.out.println("\nDISPLAYING ALL THE PLAYLISTS...");

                    String playlistListQuery = "select * from allPlaylists";
                    List<Playlist> playLists = pd.readAndDisplayAllPlaylists(playlistListQuery);


                    //do while loop to display the Playlist menus till exit value is given by the user.
                    do{
                        //Display PlayList Sub Menus
                        System.out.println("\n\nPLAYLIST MENUS: \n1.Create New Playlist\t 2.Add Audio to an Existing Playlists\t3.Play Songs from Playlist\t 0.Go back to Main Menu\n (Enter the number to Choose your option)");
                        try{
                            menuChoice = scanner.nextInt();
                            if((menuChoice!=1)&&(menuChoice!=2)&&(menuChoice!=3)&&(menuChoice!=0)){
                                throw new IllegalArgumentException("Sorry, that's not in our Option, Please Enter a Correct Playlist Option \n");
                            }
                        }catch (IllegalArgumentException ex){
                            //to find the error details
                            ex.printStackTrace();
                        }
                        if(menuChoice ==1){
                            pd.createPlaylist(playLists);
                        }
                        else if (menuChoice == 2) {
                                String playListName = "";
                                System.out.println("Enter Playlist Name:");
                                scanner.nextLine();
                                playListName = scanner.nextLine();
                                pd.insertIntoPlayList(playListName, playLists);
                            }
                        else {
                            if (menuChoice == 3) {
                                JukeBoxAudioPlayer player = new JukeBoxAudioPlayer();
                                //displays all the available playlists
                                player.displayPlaylist();
                            }
                        }

                    }while(menuChoice!=0);
                }
            }
        } while(!userChoice.equalsIgnoreCase("E"));
        System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><>< SEE YOU SOON !!! BYE!!! ><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><><> ");
    }
}
