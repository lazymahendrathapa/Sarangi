package com.mycompany.AudioFeatures;

/**
 * A class for calculating the intensity of the given audio samples
 *
 */
public class Intensity{

    protected double rms = 0.0;

    public Intensity(double[] audioSamples){

        for(int i=0; i<audioSamples.length; ++i){
              rms += audioSamples[i] * audioSamples[i];
        }

        rms = 10.0 * Math.log10(rms);
    }

  /**
   * Returns the rms value
   */

    public double getIntensityFeatures(){
        return rms;
    }

}

