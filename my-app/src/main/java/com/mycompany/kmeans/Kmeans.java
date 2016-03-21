package com.mycompany.kmeans;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

import java.lang.reflect.Type;

import com.mycompany.kmeans.*;

import com.mycompany.structures.Song;

public class Kmeans {

        List<SongCluster> clusters;
        List<SarangiSong> allSongs;
        int k = 5;
        final int MAX_ITERATION = 100000;

        public Kmeans() {
                clusters = new ArrayList<SongCluster>();
                allSongs = new ArrayList<SarangiSong>();
        }

        public Kmeans(int k) {
                this.k = k;
                clusters = new ArrayList<SongCluster>();
                allSongs = new ArrayList<SarangiSong>();
        }

        public void readAllSongs(String filename) 
                throws FileNotFoundException, IOException {

                 Gson gson = new Gson();
                 JsonReader json = new JsonReader(new FileReader(filename));
 
                 Type listType = new TypeToken<List<Song>>(){}.getType();
                 List<Song> songs = gson.fromJson(json,listType);
 
                 for (Song item: songs) {
                         allSongs.add(new SarangiSong(item));
                 }

        }


        public void initialize() {

                ArrayList<Integer> list = new ArrayList<Integer>();

                int size = allSongs.size();
                for (int i=0; i<size; i++) {
                        list.add(new Integer(i));
                }

                Collections.shuffle(list);

                // Use the first k numbers from the randomly shuffled list as index 
                for (int i=0; i<k; i++) {
                        int index = list.get(i);

                        // i is the id for the cluster
                        SongCluster newCluster = new SongCluster(i);

                        // Set the song with the index as the centroid
                        newCluster.setCentroid(allSongs.get(index));
                        clusters.add(newCluster);
                }
        }

        public List<SongCluster> run() {

                int numOfSongs = allSongs.size();
                int iteration = 0;

                while (true) {

                        for (int j=0; j<k; j++) {
                                clusters.get(j).clearCluster();
                        }

                        // For each song
                        for (int i=0; i<numOfSongs; i++) {

                                SarangiSong song = allSongs.get(i);

                                int closestClusterIndex = 0;
                                /*
                                double minDistance = clusters.get(0)
                                        .distanceToCentroid(song);
                                        */
                                double minDistance = 100000000000.0d;

                                for (int j=0; j<k; j++) {

                                        double dist = clusters.get(j)
                                                .distanceToCentroid(song);

                                        if (dist<minDistance) {
                                                minDistance = dist;
                                                closestClusterIndex = j;
                                        }

                                }

                                // Assign to closest cluster
                                clusters.get(closestClusterIndex).addSong(song);
                        }

                        boolean sameCentroid = true;

                        for (int j=0; j<k; j++) {
                                boolean sameCentroidTemp = clusters.get(j).computeCentroid();
                                sameCentroid = sameCentroid && sameCentroidTemp;
                        }

                        iteration++;

                        if (iteration > MAX_ITERATION) {
                                System.out.println("Iteration limit reached... Exiting...");
                                break;
                        }

                        if (sameCentroid) {
                                break;
                        }

                        /*
                        if ((sameCentroid) || (iteration > MAX_ITERATION)) {
                                break;
                        }
                        */

                }

                System.out.println("Iterations: "+iteration);

                return clusters;
        }


}
