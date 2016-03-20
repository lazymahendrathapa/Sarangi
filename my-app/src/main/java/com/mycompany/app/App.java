package com.mycompany.app;

import com.mycompany.AudioTools.*;
import com.mycompany.AudioFeatures.*;
import com.mycompany.Structures.*;
import com.mycompany.JSON.*;

import java.io.*;
import java.util.*;

public class App {

    protected static Song[] song;

    public static void main (String args[])
    {

     try{

        File folder = new File("src/resources/Song");
        File[] listOfFiles = folder.listFiles();

        song = new Song[listOfFiles.length];

        for(int i=0; i<listOfFiles.length; ++i){
     
            String songName = listOfFiles[i].getName();
            File fileName = new File("src/resources/Song/"+songName);

            AudioSample audioSample = new AudioSample(fileName);
            double[] samples = audioSample.getAudioSamples();
                
            Intensity intensity = new Intensity(samples);
            double intensityFeatures = intensity.getIntensityFeatures();

            MFCC mfcc = new MFCC(samples);
            double[] mfccFeatures = mfcc.getMFCCFeatures();
 
        //    Rhythm rhythm = new Rhythm(samples);
        //    double[] rhythmicFeatures = rhythm.getRhythmicFeatures();

            song[i] = new Song(songName, intensityFeatures,mfccFeatures);
       
        }

        JSONWriter jsonWriter = new JSONWriter(song);

     }catch(Exception e){
         System.out.println(e);
     }
    
    }
}
