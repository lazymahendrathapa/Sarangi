package com.mycompany.app;

import com.mycompany.AudioTools.*;
import com.mycompany.AudioFeatures.*;
import java.io.*;

public class App {
    public static void main (String args[])
    {

     try{

        File fileName = new File("/home/idea/Sarangi/my-app/src/resources/abc.wav");
        
        AudioSample audioSample = new AudioSample(fileName);
        double[] samples = audioSample.getAudioSamples();

        Intensity intensity = new Intensity(samples);
        System.out.println(intensity.getIntensityFeatures());

     }catch(Exception e){
         System.out.println(e);
     }
    
    }
}
