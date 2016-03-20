package com.mycompany.JSON;

import com.mycompany.Structures.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.*;
import java.lang.reflect.Type;

import java.io.*;
import java.util.*;

/**
 * A class to convert the array to json format
 *
 */

public class JSONFormat{

    public void convertArrayToJSON(ArrayList<Song> song){

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

  public ArrayList<Song> convertJSONtoArray(String fileName){
        
          Gson gson = new Gson();

          try{
         
                JsonReader json = new JsonReader(new FileReader(fileName));

                Type listType = new TypeToken<ArrayList<Song>>(){}.getType();
                ArrayList<Song> allSongs = gson.fromJson(json,listType);

                return allSongs;
  
          }catch(Exception ex){
               System.out.println(ex);
               System.exit(0);
          }

          return null;
  }


}
