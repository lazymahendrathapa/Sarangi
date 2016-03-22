package com.mycompany.app;

import com.mycompany.app.*;

import java.io.*;
import java.util.*;

import com.mycompany.kmeans.*;

public class App {

    public static void main (String args[]) 
            throws FileNotFoundException, IOException {

            try{

                    FeatureExtractor featureExtractor = new FeatureExtractor();

            }catch(Exception e){
                System.out.println(e);
            }

                String filename = "src/resources/SongFeatures/Features.txt";

                if (args.length == 1) {
                        filename = args[0];
                }

                Kmeans kMeansRunner = new Kmeans(5);

                kMeansRunner.readAllSongs(filename);

                kMeansRunner.initialize();

                //kMeansRunner.showRandomDistances();
                List<SongCluster> results = kMeansRunner.run();


                List<String> labels = new ArrayList<String>();
                labels.add("Rock");
                labels.add("Classical");
                labels.add("Pop");
                labels.add("Hiphop");
                labels.add("Jazz");


                Analyzer analyzer = new Analyzer(results,labels);
                analyzer.assignBestLabels();
                analyzer.display();
                analyzer.displayConfusionMatrix();
    
    }
}
