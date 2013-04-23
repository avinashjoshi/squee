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
     * 
     * @param filename
     * @return 
     */
    public static String[] readFileAsStringArray(String dirfilename)
    {
       File dirfile;
       File[] listOfFiles;
       BufferedReader bufReader;

       
       StringBuilder sbldFilepath;
       String actualFilePath;
       
       String strLine;
       StringBuilder outStrBuild;
       
       
       dirfile = new File(dirfilename);
       if(!dirfile.exists())
           return null;
       
       if(dirfile.isDirectory())
       {
           int i = 0;
           listOfFiles = dirfile.listFiles();
           for(; i < listOfFiles.length; ++i)
           {
               if(listOfFiles[i].isFile())
               {
                   if(listOfFiles[i].getName().startsWith("part"))
                       break;
               }
           }// for i
           
           if(i == listOfFiles.length)
               return null;
           
           // construct the file name with dir path
           sbldFilepath = new StringBuilder(dirfilename);
           sbldFilepath.append('/');
           sbldFilepath.append(listOfFiles[i].getName());
           actualFilePath = sbldFilepath.toString();
       }
       else
           actualFilePath = dirfilename;
       
       try {
           bufReader = new BufferedReader(
                   new FileReader(actualFilePath));
           
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
           System.out.println("Output file " + actualFilePath + " not found!!");
       }
       catch(IOException e)
       {
           System.out.println("Error reading from " + actualFilePath);
       }
       
       return null;
       
    }// getOutputFromFile()
    
    
    /**
     * 
     * @param dir
     * @return 
     */
    public static boolean deleteDirectory(String dir)
    {
        File dirFile = new File(dir);
        
        return deleteDirRecursive(dirFile);
    }
    
    /**
     * 
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
        
        if(!f.delete())
            return false;
        return true;
    }// 
    
}
