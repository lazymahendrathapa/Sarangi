

import java.io.*;
import java.io.IOException;
import java.lang.System;


import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class MyClass {
    public static void main(String args[])
    {
        //Get the audio file
        AudioInputStream audioInputStream=null;
        AudioFormat audioFormat=null;
        File audioFile= new File("output5.wav");
        try
        {
            //audioFileFormat.getFormat()
            //JS_MP3FileReader audiomp3 = new JS_MP3FileReader();
            audioInputStream =AudioSystem.getAudioInputStream(audioFile);
            audioFormat = audioInputStream.getFormat();
           // System.out.println(audioFormat.getFrameRate());
        }
        catch (UnsupportedAudioFileException exp){exp.printStackTrace();}
        catch (IOException exp){exp.printStackTrace();}

        long bSamples = getSampleCount(audioInputStream,audioFormat);
        long bBytes=bSamples*(audioFormat.getSampleSizeInBits()/8)*audioFormat.getChannels();
        byte[] inbuffer = new byte[(int)bBytes];
        try
        {
            audioInputStream.read(inbuffer,0,inbuffer.length);
        }
        catch (IOException exp){exp.printStackTrace();}
        double[] samples = new double[(int)bSamples];
        int SampleSizeinBytes = audioFormat.getSampleSizeInBits()/8;
        int[] sampleBytes=new int[SampleSizeinBytes];
        int k=0;
       // System.out.println(SampleSizeinBytes);
        for(int i=0;i<samples.length;++i)
        {
            if(audioFormat.isBigEndian())
            {
                for(int j=0;j<SampleSizeinBytes;++j)
                {
                    sampleBytes[j]=inbuffer[k++];
                }
            }
            else
            {
                for(int j=SampleSizeinBytes-1;j>=0;--j)
                {
                    sampleBytes[j]=inbuffer[k++];
                    if(sampleBytes[j]!=0) j+=0;
                }
            }
            int ival=0;
            for(int j=0;j<SampleSizeinBytes;++j)
            {
                ival+=sampleBytes[j];
                if(j<SampleSizeinBytes-1) ival<<=8;
            }
            double ratio = Math.pow(2,audioFormat.getSampleSizeInBits()-1);
            double val = ((double)ival)/ratio;
            samples[i]=val;
        }
        double rms = 0.0;
        for (int i = 0; i < samples.length; i++) {
            rms += samples[i] * samples[i];
        }
        System.out.println(10.0 * Math.log10(rms));
    }
    //Total samples in all channels
    private static long getSampleCount(AudioInputStream audioInputStream,AudioFormat audioFormat)
    {
        long totalSamples = (audioInputStream.getFrameLength()*audioFormat.getFrameSize()*8)/audioFormat.getSampleSizeInBits();
        return  totalSamples/audioFormat.getChannels();
    }


}
