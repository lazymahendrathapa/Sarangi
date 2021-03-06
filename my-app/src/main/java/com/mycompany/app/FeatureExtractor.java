package com.mycompany.app;

import com.mycompany.AudioTools.*;
import com.mycompany.AudioFeatures.*;
import com.mycompany.structures.*;
import com.mycompany.JSON.*;

import java.io.*;
import java.util.*;

public class FeatureExtractor{

    protected ArrayList<Song> song = new ArrayList<Song>();
    protected JSONFormat  jsonFormat= new JSONFormat();

    public FeatureExtractor()
    {

     int songSampleLength = 13230000;

     try{

        ArrayList<Song> tempSong = jsonFormat.convertJSONtoArray(new String("src/resources/SongFeatures/Features.txt"));

        File folder = new File("src/resources/Song");
        File[] listOfFiles = folder.listFiles();

        int numOfFiles = listOfFiles.length;

               
        for(int i=0; i<numOfFiles; ++i){
   
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

                    System.out.println("Extracting: ("+i+" of "+numOfFiles+"): "+songName);

                    AudioSample audioSample = new AudioSample(fileName);

                    double[] samples;
                    double[] tempSamples = audioSample.getAudioSamples();

                    if(tempSamples.length > songSampleLength){
                         samples =new double[songSampleLength];
                         System.arraycopy(tempSamples,0,samples,0,songSampleLength);
                    }else{
                         samples = new double[tempSamples.length];
                         System.arraycopy(tempSamples,0,samples,0,tempSamples.length);
                    }

                    Intensity intensity = new Intensity(samples);
                    double intensityFeatures = intensity.getIntensityFeatures();

                    MFCC mfcc = new MFCC(samples);
                    double[] mfccFeatures = mfcc.getMFCCFeatures();
         
                    Rhythm rhythm = new Rhythm(samples);
                    double[] rhythmicFeatures = rhythm.getRhythmicFeatures();

                      song.add(new Song(songName, intensityFeatures,mfccFeatures, rhythmicFeatures));
            }
       
        }

        jsonFormat.convertArrayToJSON(song); 

     }catch(Exception e){
         System.out.println(e);
     }
    
    }
}
