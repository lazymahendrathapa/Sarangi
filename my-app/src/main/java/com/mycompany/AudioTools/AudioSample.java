package com.mycompany.AudioTools;

import java.io.*;
import javax.sound.sampled.*;
import java.nio.*;
/**
 * A class for holding audio samples and associated audio formatting information.
 * Samples are stored as arrays of doubles
 */

public class AudioSample{

    protected double[] samples;
    protected double[][] channelSamples;
    protected AudioFormat audioFormat;

   
    public AudioSample(File audioFile) throws Exception{

        if(!audioFile.exists())
            throw new Exception("File " + audioFile.getName() + " does not exist.");
        if(audioFile.isDirectory())
            throw new Exception("File " + audioFile.getName() + " is a directory.");

        AudioInputStream audioInputStream = null;

        try{
            audioInputStream = AudioSystem.getAudioInputStream(audioFile); 
        } catch(UnsupportedAudioFileException ex){
            throw new Exception("File " + audioFile.getName() + " has an unsupported audio format.");
        } catch(IOException ex){
            throw new Exception("File " + audioFile.getName() + " is not readable.");
        }

        AudioFormat originalAudioFormat = audioInputStream.getFormat();
        audioFormat = getConvertedAudioFormat(originalAudioFormat);


        if(!audioFormat.matches(originalAudioFormat))
             audioInputStream = AudioSystem.getAudioInputStream(audioFormat,audioInputStream);

        channelSamples = extractSampleValues(audioInputStream);
        
        samples = getSamplesMixedDownIntoOneChannel(channelSamples);

        audioInputStream.close();

    }

    /**
     * Returns an AudioFormat with the same sampling rate and number of channels as the passed AudioFormat.
     * If the bit depth is something other than 8 or 16 bits, then it is converted to 16 bits. The returned
     * AudioFormat, also, will use big-endian signed linear PCM encoding, regardless of the passed format.
     *
     */

    public AudioFormat getConvertedAudioFormat(AudioFormat originalFormat){
         int bitDepth = originalFormat.getSampleSizeInBits();

         if(bitDepth != 8 && bitDepth != 16)
                bitDepth = 16;

         return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                                originalFormat.getSampleRate(),
                                bitDepth,
                                originalFormat.getChannels(),
                                originalFormat.getChannels()*(bitDepth/8),
                                originalFormat.getSampleRate(),
                                true);
    }

    /**
     * Returns an array of doubles representing the samples for each channel in the given AudioInputStream
     */

    public double[][] extractSampleValues(AudioInputStream audioInputStream) throws Exception{
            
        //Converts the contents of audioInputStream into an array of bytes

        byte[] audioBytes = getBytesFromAudioInputStream(audioInputStream);

        int numberBytes = audioBytes.length;

        AudioFormat thisAudioFormat = audioInputStream.getFormat();

        //Extract information from thisAudioFormat
        int numberOfChannels = thisAudioFormat.getChannels();
        int bitDepth = thisAudioFormat.getSampleSizeInBits();

        //Find the number of samples in the audio bytes
        int numberOfBytes = audioBytes.length;
        int bytesPerSample = bitDepth/8;
        int numberSamples = numberOfBytes / bytesPerSample / numberOfChannels;

        //Find the maximum possible value that a sample may have with the given bit depth
        double maxSampleValue = Math.pow(2,audioFormat.getSampleSizeInBits()-1);

        double[][] sampleValue = new double[numberOfChannels][numberSamples];

        //Convert the bytes to double samples
        ByteBuffer byteBuffer = ByteBuffer.wrap(audioBytes);

        if(bitDepth == 8){

            for (int samp = 0; samp < numberSamples; ++samp)
                for(int chan = 0; chan <numberOfChannels; ++chan)
                    sampleValue[chan][samp] = (double)byteBuffer.get()/maxSampleValue;

        }else if(bitDepth == 16){

            ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
         
            for (int samp = 0; samp < numberSamples; ++samp)
                for(int chan = 0; chan <numberOfChannels; ++chan)
                    sampleValue[chan][samp] = (double)shortBuffer.get()/maxSampleValue;
        }

        return sampleValue;
    }

    /**
     * Generates an array of audio bytes based on the contents of the given AudioInputStream
     */

    public byte[] getBytesFromAudioInputStream(AudioInputStream audioInputStream) throws Exception{

        //Calculate the buffer size to use
        float bufferDurationInSeconds = 0.25F;
        int bufferSize = getNumberBytesNeeded(bufferDurationInSeconds,audioInputStream.getFormat());

        byte rwBuffer[] = new byte[bufferSize + 2];

        //Read the bytes into rwBuffer and then into the ByteArrayOutputStream

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int position = audioInputStream.read(rwBuffer,0,rwBuffer.length);
        
        while(position > 0){

            byteArrayOutputStream.write(rwBuffer,0,position);
            position = audioInputStream.read(rwBuffer,0,rwBuffer.length);
        }

        byte[] results = byteArrayOutputStream.toByteArray();

        try{
              byteArrayOutputStream.close();
        }catch(IOException e){

            System.out.println(e);
            System.exit(0);
        }

        return results;
    }
 

    /**
     * Returns the number of bytes needed to store samples corresponding to audio of fixed duration
     */

    public int getNumberBytesNeeded(double durationInSeconds, AudioFormat audioFormat){

        int frameSizeInBytes = audioFormat.getFrameSize();
        float frameRate = audioFormat.getFrameRate();
        return (int) (frameSizeInBytes * frameRate * durationInSeconds);
    }

    /**
     * Returns the given set of samples as a set of samples mixed down into one channel.
     *
     */

    public double[] getSamplesMixedDownIntoOneChannel(double[][] audioSamples){

        if(audioSamples.length == 1)
            return audioSamples[0];

        double numberChannels = (double)audioSamples.length;
        int numberSamples = audioSamples[0].length;

        double[] samplesMixedDown = new double[numberSamples];

        for(int samp=0; samp < numberSamples; ++samp){
            double totalSoFar = 0.0;
            for(int chan = 0; chan < numberChannels; ++chan){
                  totalSoFar += audioSamples[chan][samp];
            }
            samplesMixedDown[samp] = totalSoFar / numberChannels;
        }

       return samplesMixedDown;

    }

    /**
     * Returns the AudioFormat associated with the stored samples
     */

    public AudioFormat getAudioFormat(){
        return audioFormat;
    }

    /**
     * Returns the audio samples
     */
    public double[] getAudioSamples(){
        return samples;
    }

  }

