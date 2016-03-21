package com.mycompany.AudioFeatures;

import com.mycompany.AudioTools.*;
import java.util.*;

/**
 * A class for calculating the Rhythmic features of the given audio Signal
 *
 */

public class Rhythm{

    protected int sampleLength;
    protected int windowSize = 1024;
    protected int subBands = 32;
    protected double localEnergy[][]; 
    protected double rhythmFeatures[] = new double[subBands];
    protected int energyOverLappingSize = 43; 
    protected int C = 25;

    public Rhythm(double[] audioSamples){

        sampleLength = audioSamples.length;
        double tempAudioSamples[] = new double[windowSize];

        localEnergy = new double[(int)sampleLength/windowSize][subBands];
        int count = 0;

        for(int i=0; i < sampleLength; i +=windowSize){

            if(sampleLength - i > windowSize){
               System.arraycopy(audioSamples,i,tempAudioSamples,0,windowSize);
            }else{
                break; 
            }

         FFT fft = new FFT(tempAudioSamples);
         double[] frequencyAmplitude = fft.getFrequencyAmplitudeSpectrum();

         for(int j=0; j<subBands; ++j){
            
           double sum = 0.0;

           for(int k = j*subBands; k<(j+1)*subBands; ++k)
                sum +=  frequencyAmplitude[k];
           
           localEnergy[count][j] = subBands * sum / windowSize;

         }

            ++count;
        }


        for(int j = 0; j < count - energyOverLappingSize ; ++j){

           double averageEnergy[] = new double[subBands];

           for(int i = 0; i < subBands; ++i){

                for(int k = j; k< j + energyOverLappingSize; ++k){
                     averageEnergy[i] += localEnergy[k][i];
                }

               averageEnergy[i] /= energyOverLappingSize;
            }

            
             for(int i = 0; i < subBands; ++i){
                
                if(localEnergy[j+energyOverLappingSize][i] > C * averageEnergy[i]){
                    ++rhythmFeatures[i];
                }
            }

        }
    
    }

   public double[] getRhythmicFeatures(){
           return rhythmFeatures;
        }
}
