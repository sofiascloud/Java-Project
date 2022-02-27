package com.company;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PodCastDriver {


    public static List<PodCast> readAndDisplayPodCast(String query) throws Exception {
        //Creating Obj reference

        //Creating Database Connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukeboxdb", "root", "Newsky7#");

        //Create the statement
        Statement st = con.createStatement();
        ResultSet rsPodCastList = st.executeQuery(query);
        //Create an arrayList to store the podcasts
        List<PodCast> podCastList = new ArrayList();
        while (rsPodCastList.next()) {
            Song songDetail = new Song(rsPodCastList.getInt(1),rsPodCastList.getString(2), rsPodCastList.getString(3), rsPodCastList.getString(4),rsPodCastList.getString(5),rsPodCastList.getTime(7));
            podCastList.add(new PodCast(songDetail,rsPodCastList.getDate(6)));
        }

        //To display all the PodCasts
        displayPodCast(podCastList);
        return podCastList;
    }


    //find the user's choice and call the relevant method to find the podcasts
    public static void searchPodcast(int choice, List<PodCast> podCasts) throws ParseException {
        String searchCategory;
        //import scanner class to get the user input
        Scanner searchScanner = new Scanner(System.in);
        if (choice == 1) {
            //get user input to search
            System.out.println("Type the Celebrity Name: ");
            searchCategory = searchScanner.nextLine();
            searchByCelebrity(searchCategory, podCasts);
        }
        if (choice == 2) {
            String publishedDate;
            Date searchDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //get user input to search
            System.out.println("Type the Year(yyyy-MM-dd): ");
                try {
                    //take user input of year
                    publishedDate = searchScanner.next();
                    searchDate= sdf.parse(publishedDate);
                    if (!publishedDate.equals(searchDate)){
                        throw new InvalidArgumentException("Not Audio found on this date");
                    }
                }catch(InvalidArgumentException ex){
                    System.out.println(ex.getMessage());
                }
            searchByPublishedDate(searchDate, podCasts);
        }
    }

    //method to search a podcast by celebrity name
    public static int searchByCelebrity(String celebrity, List<PodCast>podCasts) {
        // filter the list as per celebrity
        Predicate<PodCast> celebrityFilter = (cf)->cf.getSong().getArtist().toLowerCase().startsWith(celebrity.toLowerCase());
        List<PodCast> celebrityPCList = podCasts.stream().filter(celebrityFilter).collect(Collectors.toList());
        //display the songs
        if(celebrityPCList.size()!=0) {
            displayPodCast(celebrityPCList);
            return 1;
        }
        else return 0;
    }

    //method to search a podcast by published date
    public static int searchByPublishedDate(Date publishedOn, List<PodCast>podCasts) {
        // filter the list as per published date

        Predicate<PodCast> publishedFilter = (pf)-> (pf.getPublishedOn().compareTo(publishedOn)==0);
        List<PodCast> publishedList = podCasts.stream().filter(publishedFilter).collect(Collectors.toList());
        //display the songs
        if(publishedList.size()!=0) {
            displayPodCast(publishedList);
            return 1;
        }
        else return 0;
    }

    //Display PodCast details
    public static void displayPodCast(List<PodCast> podCasts) {
        System.out.format("%-10s %-30s %-22s %-20s %-20s %-20s %-15s \n", "Trackid","PodCast Title", "Topic", "Celebrity", "Genre", "Published On", "Duration");
        System.out.println("<---------------------------------------------------------------------------------------------------------------------------------------------->");

        Consumer<PodCast> displayPodCast = (dPodCast) -> System.out.format("%-10s %-30s %-22s %-20s %-20s %-20s %-15s \n", dPodCast.getSong().getSongId(),dPodCast.getSong().getSongName(), dPodCast.getSong().getAlbum(), dPodCast.getSong().getArtist(), dPodCast.getSong().getGenre(), dPodCast.getPublishedOn(), dPodCast.getSong().getDuration());
        podCasts.forEach(displayPodCast);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
    }

}
