package com.mycompany.app;

import com.mycompany.AudioTools.*;
import com.mycompany.AudioFeatures.*;
import java.io.*;
import java.util.*;

public class App {
    public static void main (String args[])
    {

     try{

        File fileName = new File("src/resources/wish.mp3");
        
        AudioSample audioSample = new AudioSample(fileName);
        double[] samples = audioSample.getAudioSamples();

        Intensity intensity = new Intensity(samples);
        System.out.println(intensity.getIntensityFeatures());

        MFCC mfcc = new MFCC(samples);
        double[] mfccFeatures = mfcc.getMFCCFeatures();
        System.out.println(Arrays.toString(mfccFeatures));

     }catch(Exception e){
         System.out.println(e);
     }
    
    }
}
