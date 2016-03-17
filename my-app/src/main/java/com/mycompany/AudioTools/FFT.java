package com.mycompany.AudioTools;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * A class for calcuating the Fast Fourier Transform of given audio samples
 *
 */

public class FFT{

   protected DoubleFFT_1D fft;
   
   protected double[] realOutput;
   protected double[] imagOutput; 
  
   protected double[] outputAngle;

   protected double[] outputMagnitude;
   protected double[] outputPower;

   protected double[] outputFrequencyAmplitude;

   public FFT(double[] samples){

        fft = new DoubleFFT_1D(samples.length);
        realOutput = new double[samples.length];
        imagOutput = new double[samples.length];


        double[] fftData = new double[samples.length * 2];

        for(int i=0; i<samples.length; ++i){

            fftData[2 * i] = samples[i];
            fftData[2 * i + 1] = 0;

        }

        fft.complexForward(fftData);

        for(int i = 0; i<samples.length; ++i){
            realOutput[i] = fftData[2 * i];
            imagOutput[i] = fftData[2 * i + 1];
        }

        // Set the angle, power, frequency amplitude and magnitude to null originally
        outputAngle = null;
        outputMagnitude = null;
        outputPower = null;
        outputFrequencyAmplitude = null;
        
   }

   /**
    * Returns the real output obtained by FFT of audio samples
    */

   public double[] getRealOutput(){
         return realOutput;
   }

   /**
    * Returns the imaginary output obtained by FFt of audio samples
    */

   public double[] getImagOutput(){
         return imagOutput;
   }

   /**
    * Returns the magnitude spectrum. Only the left side of the spectrum is returned, as the folded portion of the spectrum is redundant for the purpose
    * of the magnitude spectrum. This means that the bins only go up to half of the sampling rate.
    */

   public double[] getMagnitudeSpectrum(){
          
        //Only calculate the magnitudes if they have not yet been calculated
        if(outputMagnitude == null){

            int numberUnfoldedBins = imagOutput.length/2;
            outputMagnitude = new double[numberUnfoldedBins];

            for(int i=0; i<outputMagnitude.length; ++i)
                outputMagnitude[i] = (Math.sqrt(realOutput[i] * realOutput[i] + imagOutput[i] * imagOutput[i] ))/realOutput.length;

        }

        return outputMagnitude;

   }


   /**
    * Returns the power spectrum.Only the left side of the spectrum is returned.
    *
    */

   public double[] getPowerSpectrum(){
 
        if(outputPower == null){
 
            int numberUnfoldedBins = imagOutput.length/2;
            outputPower = new double[numberUnfoldedBins];

            for(int i=0; i<outputPower.length; ++i)
                outputPower[i] = (realOutput[i] * realOutput[i] + imagOutput[i] * imagOutput[i])/realOutput.length;

        }

        return outputPower;

   }

   /**
    * Returns the Frequency amplitudes of the spectrum
    *
    */

   public double[] getFrequencyAmplitudeSpectrum(){

        if(outputFrequencyAmplitude== null){

                int numberUnfoldedBins = imagOutput.length;
                outputFrequencyAmplitude = new double[numberUnfoldedBins];

                for(int i=0; i<outputFrequencyAmplitude.length; ++i)
                     outputFrequencyAmplitude[i] = (realOutput[i] * realOutput[i] + imagOutput[i] * imagOutput[i]);
                
        }

        return outputFrequencyAmplitude;
        
   }

   /**
    * Returns the phase angle for each frequency bin. Only the left side of the spectrum is returned.
    */

   public double[] getPhaseAngle(){

        if(outputAngle == null){

            int numberUnfoldedBins = imagOutput.length/2;
            outputAngle = new double[numberUnfoldedBins];

            for(int i=0; i<outputAngle.length; ++i){

               if(imagOutput[i] == 0.0 && realOutput[i] == 0.0)
                    outputAngle[i] = 0.0;
               else 
                    outputAngle[i] = Math.atan(imagOutput[i] / realOutput[i]) * 180.0 / Math.PI;

               if(realOutput[i] < 0.0 && imagOutput[i] == 0.0)
                    outputAngle[i] = 180.0;
               else if(realOutput[i] < 0.0 && imagOutput[i] == -0.0)
                    outputAngle[i] = -180.0;
               else if(realOutput[i] < 0.0 && imagOutput[i] > 0.0)
                    outputAngle[i] += 180.0;
               else if(realOutput[i] < 0.0 && imagOutput[i] < 0.0)
                    outputAngle[i] -= 180.0;
              
            }
        }

        return outputAngle;
   }

}
