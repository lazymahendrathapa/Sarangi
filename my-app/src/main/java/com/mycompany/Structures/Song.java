package com.mycompany.structures;

/**
 * Simple Data Structure to hold information related to a song.
 *
 */

public class Song{

   public String songName;
   public double intensity;
   public double[] mfcc;

   public Song(String songName, double intensity, double[] mfcc){

        this.songName = songName;
        this.intensity = intensity;
        this.mfcc = mfcc.clone();

   }
   
}
