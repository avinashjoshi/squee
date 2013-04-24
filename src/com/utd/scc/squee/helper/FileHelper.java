/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utd.scc.squee.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author hduser
 */
public class FileHelper {
    
    /**
     * Reads a text file specified using its path name and returns the 
     * file contents as a string array.
     * @param filePath Path to file
     * @return String array of file contents. If error occurs, null is returned.
     */
    public static String[] readFileAsStringArray(String filePath)
    {
       BufferedReader bufReader;
       
       String strLine;
       StringBuilder outStrBuild;
       
       try {
           bufReader = new BufferedReader(
                   new FileReader(filePath));
           
           outStrBuild = new StringBuilder();
           while((strLine = bufReader.readLine()) != null)
           {
               outStrBuild.append(strLine);
               outStrBuild.append('\n');
           }
           
           return outStrBuild.toString().split("[\n]");
       }
       catch(FileNotFoundException e)
       {
           System.out.println("Output file " + filePath + " not found!!");
       }
       catch(IOException e)
       {
           System.out.println("Error reading from " + filePath);
       }
       
       return null;
       
    }// readFileAsStringArray()
    
    
    /**
     * Recursive directory deletion.
     * @param dir Path to directory to be deleted.
     * @return true/false indicating success or failure to delete the directory.
     */
    public static boolean deleteDirectory(String dir)
    {
        File dirFile = new File(dir);
        
        return deleteDirRecursive(dirFile);
    }
    
    /**
     * Recursive directory delete worker function.
     * @param f
     * @return 
     */
    private static boolean deleteDirRecursive(File f)
    {   
        if(!f.exists())
            return true;
        
        if(f.isDirectory())
        {
            for(File f1: f.listFiles())
                deleteDirRecursive(f1);
        }
        
        return f.delete();
    }// 
    
}// FileHelper
