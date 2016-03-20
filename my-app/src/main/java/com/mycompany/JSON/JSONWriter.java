package com.mycompany.JSON;

import com.mycompany.Structures.*;
import com.google.gson.Gson;

import java.io.*;
import java.util.*;

/**
 * A class to convert the array to json format
 *
 */

public class JSONWriter{

    public JSONWriter(Song[] song){

            Gson gson = new Gson();
            String json = gson.toJson(song);

         try{
            
            FileWriter fileWriter = new FileWriter("src/resources/SongFeatures/Features.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(json);

            bufferedWriter.close();
         }catch(IOException ex){
               System.out.println("Error writing to file");
         }

    }
}
