package com.mycompany.app;

import com.mycompany.app.*;

import java.io.*;
import java.util.*;

import com.mycompany.kmeans.*;

public class App {

   public static void main (String args[]) {
    //protected static Song[] song;

    public static void main (String args[]) 
            throws FileNotFoundException, IOException {

                String filename = "src/resources/SongFeatures/Features.txt";

                if (args.length == 1) {
                        filename = args[0];
                }

                Kmeans kMeansRunner = new Kmeans(5);

                kMeansRunner.readAllSongs(filename);

                kMeansRunner.initialize();

                List<SongCluster> results = kMeansRunner.run();

                for (SongCluster cluster: results) {
                        cluster.display();
                }
                /*
     try{

             FeatureExtractor featureExtractor = new FeatureExtractor();

     }catch(Exception e){
         System.out.println(e);
     }
     */
    
    }
}
