package com.mycompany.kmeans;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.structures.Song;

/**
 *
 * Represents the data points (songs)
 * 
 * @author Bijay Gurung
 *
 */

public class SarangiSong {

        private String name;
        private double intensity;
        private List<Double> mfcc;

        private int clusterID = 0;

        // Default Constructor
        public SarangiSong() {}

        /**
         *
         * Constructor that accepts the individual members
         * 
         * @param intensity The intensity of the Sarangi Song
         * @param mfcc The List of Doubles representing the MFCC
         *
         */

        public SarangiSong(String name, double intensity, List<Double> mfcc) {

                this.name = name;
                this.intensity = intensity;

                this.mfcc = new ArrayList<Double>();

                for (Double item: mfcc) {
                        this.mfcc.add(item);
                }

        }

        /**
         *
         * Copy Constructor
         * 
         * @param sarangiSong A SarangiSong object
         *
         */

        public SarangiSong(SarangiSong sarangiSong) {

                this.name = sarangiSong.name;
                this.intensity = sarangiSong.intensity;
                this.mfcc = new ArrayList<Double>();

                for (int i=0; i<sarangiSong.mfcc.size(); i++) {
                        this.mfcc.add(sarangiSong.mfcc.get(i));
                }

        }
        
        /**
         *
         * Constructor that uses a Song object
         * 
         * @param song A song data structure object
         *
         */

        public SarangiSong(Song song) {

                this.name = song.songName;
                this.intensity = song.intensity;
                this.mfcc = new ArrayList<Double>();

                for (int i=0; i<song.mfcc.length; i++) {
                        this.mfcc.add(song.mfcc[i]);
                }

        }
        
        /**
         *
         * Method to check if two SarangiSong objects are equal.
         * 
         * @param obj Object to be compared 
         *
         * @return boolean denoting equality
         *
         *
         */

        @Override
        public boolean equals(Object obj) {

                if (this == obj) 
                        return true;
                if (obj == null)
                        return false;
                if (!(obj instanceof SarangiSong))
                        return false;

                SarangiSong other = (SarangiSong) obj;

                if (other.intensity != this.intensity) 
                        return false;

                if (other.mfcc.size() != this.mfcc.size())
                        return false;

                int len = this.mfcc.size();

                for (int i=0; i<len; i++) {
                        if (!other.mfcc.get(i).equals(this.mfcc.get(i)))
                                return false;
                }

                return true;

        }

        /**
         *
         * Generate a hashCode for the class
         * 
         * TODO: Optimize
         * 
         * Equal objects have same hashCode but same hashCode doesn't mean 
         * the objects are equal
         * 
         * @return The hashCode
         *
         */

        @Override
        public int hashCode() {

                // Start with a non-zero prime 
                int result = 37; 

                // 64 bits (double) » 64-bit (long) » 32-bit (int)
                long intensityFieldBits = Double.doubleToLongBits(this.intensity);     
                result = 31 * result + 
                        (int)(intensityFieldBits ^ (intensityFieldBits >>> 32));

                int len = this.mfcc.size();

                for (int i=0; i<len; i++) {
                    long mfccFieldBits = 
                            Double.doubleToLongBits(this.mfcc.get(i));     
                    result = 31 * result + 
                            (int)(mfccFieldBits ^ (mfccFieldBits >>> 32));
                }

                return result;

        }

        public String getName() {
                return this.name;
        }

        /**
         *
         * Clear the song related data
         *
         */

        public void clearData() {
                this.intensity = 0.0d;
                this.mfcc.clear();
        }

        /**
         *
         * (Re)Set the song data 
         *
         * @param song a Song data structure object
         *
         */

        public void setData(Song song) {

                this.intensity = song.intensity;
                this.mfcc = new ArrayList<Double>();

                for (int i=0; i<song.mfcc.length; i++) {
                        this.mfcc.add(song.mfcc[i]);
                }
        }

        /**
         *
         * Set the Cluster ID of the SarangiSong
         *
         * @param newClusterID The new cluster ID
         *
         */

        public void setClusterID(int newClusterID) {
                this.clusterID = newClusterID;
        }

        /**
         *
         * Get the Cluster ID of the SarangiSong
         *
         * @return the cluster ID
         *
         */

        public int getClusterID() {
                return this.clusterID;
        }

        /**
         *
         * Find the result when the SarangiSong object is divided by a double
         *
         * @param d the double divisor value
         * @return The result of the division
         *
         */

        public SarangiSong divide(double d) {

                double newIntensity = this.intensity/d;
                List<Double> newMFCC = new ArrayList<Double>();

                for (Double value: this.mfcc) {
                        newMFCC.add(value/d);
                }

                return new SarangiSong(this.name,newIntensity,newMFCC);
                
        }

        /**
         *
         * Divide the SarangiSong object by the given double
         *
         * @param d the double divisor value
         *
         */

        public void divideToThis(double d) {

                this.intensity = this.intensity/d;
                int len = this.mfcc.size();
                for (int i=0; i<len; i++) {
                        //this.mfcc.get(i) = this.mfcc.get(i)/d;
                        this.mfcc.set(i,this.mfcc.get(i)/d);
                }

        }

        /**
         *
         * Calculate the result when adding a SarangiSong to this object.
         *
         * @param s2 the SarangiSong object
         * @return the result of the addition
         *
         */

        public SarangiSong add(SarangiSong s2) {

                double newIntensity = this.intensity + s2.intensity;

                int len = this.mfcc.size();
                int len2 = s2.mfcc.size();

                // The greater of the two lengths is the new length
                int newLen = (len > len2)? len: len2;

                List<Double> newMFCC = new ArrayList<Double>();

                for (int i=0; i<newLen; i++) {
                        double val1 = 0.0d;
                        double val2 = 0.0d;

                        if (len < newLen) {
                                val1 = this.mfcc.get(i);
                        }

                        if (len2 < newLen) {
                                val2 = s2.mfcc.get(i);
                        }

                        newMFCC.add(val1+val2);
                }

                return new SarangiSong(this.name,newIntensity,newMFCC);
        }

        /**
         *
         * Add the given SarangiSong object to this object 
         *
         * @param s2 the given SarangiSong object
         *
         */

        public void addToThis(SarangiSong s2) {

                SarangiSong sum = this.add(s2);

                this.intensity = sum.intensity;

                this.mfcc = new ArrayList<Double>();

                for (Double item: sum.mfcc) {
                        this.mfcc.add(item);
                }

        }

        /**
         *
         * Calculate the distance between two SarangiSong instances
         *
         * @param s1 First SarangiSong
         * @param s2 Second SarangiSong
         *
         * @return the distance between the two SarangiSongs as double
         *
         */

        public static double distance(SarangiSong s1, SarangiSong s2) {

                double weightIntensity = 1.0d;
                double weightMFCC = 1.0d;

                double sum = 0.0d;

                sum = weightIntensity * Math.pow((s1.intensity - s2.intensity),2);

                int len = s1.mfcc.size();

                double mfccSum = 0.0d;

                for (int i=0; i<len; i++) {

                        mfccSum += Math.pow((s1.mfcc.get(i) - s2.mfcc.get(i)),2);

                }

                sum += weightMFCC * mfccSum;

                return Math.sqrt(sum);

        }

}
