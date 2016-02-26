package com.mycompany.AudioFeatures;

import com.mycompany.AudioTools.*;

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_1D;
import java.util.*;

/**
 * A class for calculating the MFCC features of the given audio samples
 *
 */

public class MFCC{
 
     protected DoubleDCT_1D dct;

     protected int samplerate = 44100;
     protected int position = 0;
     protected int frameSize = 1024;
     protected int overLappingSize = 512;
     protected int lowerFrequency = 100;
     protected int upperFrequency = 16000;
     protected int numFilterBank = 40;


      protected double[] melScale;
      protected double[] frequencyScale;
      protected double[] frequencyBin; 

      protected double[][] filterBanks;

      protected  double[] averageFilterBankEnergies;
      
      /**
       * Constructors
       */

      public MFCC(double[] audioSamples){

      int sampleLength = audioSamples.length;

      double[] tempAudioSamples = new double[frameSize]; 

      melScale = getMelScale();

      frequencyScale = getFrequencyScale();
      frequencyBin = getFrequencyBins();
      filterBanks = getFilterBanks();

       int count = 0;
       
       averageFilterBankEnergies = new double[numFilterBank];

        for(int i=0; i< sampleLength; i+=overLappingSize){
      
        double[] filterBankEnergies = new double[numFilterBank];
       
        if(sampleLength - i > 1024){
            System.arraycopy(audioSamples,i,tempAudioSamples,0,frameSize);
         }else{
                break;
         }

          for(int k = 0; k < tempAudioSamples.length; ++k){
                double hanning = 0.5 - 0.5 * Math.cos(2 * Math.PI * i / tempAudioSamples.length);
                tempAudioSamples[k] *= hanning;
          }

          FFT fft = new FFT(tempAudioSamples);
          double[] tempPowerSpectrum = fft.getPowerSpectrum();
          
          for(int k=0; k<numFilterBank; ++k){
             for(int j=0; j<tempPowerSpectrum.length; ++j)
                 filterBankEnergies[k] += filterBanks[k+1][j] * tempPowerSpectrum[j];
          }

          for(int k=0; k<filterBankEnergies.length; ++k){
         
                if(filterBankEnergies[k] != 0)
                   filterBankEnergies[k] = Math.log10(filterBankEnergies[k]);
                else
                   filterBankEnergies[k] = Math.log10(0.00001); 
          }


          dct = new DoubleDCT_1D(filterBankEnergies.length);
          dct.forward(filterBankEnergies,true);
         

          for(int k=0; k<filterBankEnergies.length; ++k)
                averageFilterBankEnergies[k] +=filterBankEnergies[k];

           ++count;
      }

      for(int k=0; k<averageFilterBankEnergies.length; ++k)
            averageFilterBankEnergies[k] = averageFilterBankEnergies[k]/count;
   
       Arrays.sort(averageFilterBankEnergies);
      
      }


      public double[] getMFCCFeatures(){
              return averageFilterBankEnergies;
      }

     /** Returns the Mel Scale
      *
      */

      private double[] getMelScale(){
     
      double[] tempMelScale =  new double[numFilterBank + 2];

      tempMelScale[0] = convertFreqToMelScale(lowerFrequency);
      tempMelScale[numFilterBank + 1] = convertFreqToMelScale(upperFrequency);
      
      double melScaleGap = (tempMelScale[numFilterBank + 1] - tempMelScale[0])/(numFilterBank + 1);
      
      //Computing the Mel scale 
      for(int i=1; i<=numFilterBank; ++i){

           tempMelScale[i] = tempMelScale[i-1] + melScaleGap;
      }

      return tempMelScale;

      }

      /** 
       * Returns the Frequency Scale
       */

      private double[] getFrequencyScale(){

            double[] tempFreqScale =  new double[numFilterBank + 2];

            for(int i=0; i<melScale.length; ++i){
               tempFreqScale[i] = convertMelScaleToFreq(melScale[i]);
      
            }
 
           return tempFreqScale;

      }

     /**
      * Returns the Frequency Bins
      */

      private double[] getFrequencyBins(){

      double[] tempFrequencyBin = new double[numFilterBank + 2]; 
      
      for(int i=0; i<frequencyScale.length; ++i){
          tempFrequencyBin[i] =   Math.floor((frameSize + 1) * frequencyScale[i]/samplerate);
      
         }
           return tempFrequencyBin;
      }

    /**
     * Converting from frequency to Mel Scale
     */

    private double convertFreqToMelScale(int frequency){
        return 1125 * Math.log(1 + frequency/700); 
    }

    /** 
     * Converting Mel Scale to Frequency
     */

    private double convertMelScaleToFreq(double melScale){
          return 700 * (Math.exp(melScale/1124) - 1);
    }


   /**
    * Return the filterBanks
    */

    private double[][] getFilterBanks(){
      double[][] tempFilterBanks  = new double[numFilterBank + 2][frameSize/2];

      for(int m=1; m<=numFilterBank; ++m)
            for(int k=0; k<frameSize/2; ++k){
                if( k < frequencyBin[m - 1])
                   tempFilterBanks[m][k] = 0.0;
                else if( frequencyBin[m-1] <= k && k <= frequencyBin[m])
                    tempFilterBanks[m][k] = (k - frequencyBin[m-1])/(frequencyBin[m] - frequencyBin[m - 1]);
                 else if(frequencyBin[m] <= k && k <= frequencyBin[m+1])
                    tempFilterBanks[m][k] = (frequencyBin[m+1] - k)/(frequencyBin[m+1] - frequencyBin[m]);
                else if ( k > frequencyBin[m+1])
                   tempFilterBanks[m][k] = 0.0;
            }

      return tempFilterBanks;
     }
 
}


