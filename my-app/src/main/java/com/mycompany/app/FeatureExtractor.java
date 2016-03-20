package com.mycompany.app;

import com.mycompany.AudioTools.*;
import com.mycompany.AudioFeatures.*;
import com.mycompany.Structures.*;
import com.mycompany.JSON.*;

import java.io.*;
import java.util.*;

public class FeatureExtractor{

    protected ArrayList<Song> song = new ArrayList<Song>();
    protected JSONFormat  jsonFormat= new JSONFormat();

    public FeatureExtractor()
    {

     try{

        ArrayList<Song> tempSong = jsonFormat.convertJSONtoArray(new String("src/resources/SongFeatures/Features.txt"));

        File folder = new File("src/resources/Song");
        File[] listOfFiles = folder.listFiles();

               
        for(int i=0; i<listOfFiles.length; ++i){
   
            boolean flag = false;
            String songName = listOfFiles[i].getName();

            for(Song singleSong : tempSong)
                 if(singleSong.songName.equals(songName)){
                      song.add(singleSong);
                      flag = true;
                      tempSong.remove(singleSong);
                      break;
                 }

            if(!flag){
                    File fileName = new File("src/resources/Song/"+songName);

                    AudioSample audioSample = new AudioSample(fileName);
                    double[] samples = audioSample.getAudioSamples();
                        
                    Intensity intensity = new Intensity(samples);
                    double intensityFeatures = intensity.getIntensityFeatures();

                    MFCC mfcc = new MFCC(samples);
                    double[] mfccFeatures = mfcc.getMFCCFeatures();
         
                //    Rhythm rhythm = new Rhythm(samples);
                //    double[] rhythmicFeatures = rhythm.getRhythmicFeatures();

                      song.add(new Song(songName, intensityFeatures,mfccFeatures));
            }
       
        }

        jsonFormat.convertArrayToJSON(song); 

     }catch(Exception e){
         System.out.println(e);
     }
    
    }
}
