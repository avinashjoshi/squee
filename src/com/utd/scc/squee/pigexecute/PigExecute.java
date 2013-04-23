package com.utd.scc.squee.pigexecute;


import java.io.*;
import org.apache.pig.PigServer;
import org.apache.pig.backend.executionengine.*;

import com.utd.scc.squee.helper.FileHelper;


/**
 *
 * @author Shishir Prasad
 */
public class PigExecute {
    private PigServer pigServer = null;
    private String lastAlias = null;
    private String outputFile = null;
    
    private final String queryFilepathBegin = "queries/q";
    private final String outputFilepathBegin = "output/o";
    
    /**
     * Constructor
     * @param execTypeString
     * @param outputFile
     * @throws IOException 
     */
    public PigExecute(String execTypeString) throws IOException {
            pigServer = new PigServer(execTypeString);
    }
    
    
    /**
     * 
     * @param fileIndex
     * @return 
     */
    public String[] execQueryFromFile(int fileIndex)
    {
        
        String qFilepath;
        String[] queriesFromFile;
        
        qFilepath = getFilepathFromIndex(fileIndex);
        if(qFilepath == null)
            return null;
        
        queriesFromFile = FileHelper.readFileAsStringArray(qFilepath);
        outputFile = outputFilepathBegin.concat(new Integer(fileIndex).toString());
        
        if(!FileHelper.deleteDirectory(outputFile))
        {
            System.out.println("Unable to delete existing output directory!!");
            return null;
        }
        
        try
        {
            if(!addQueries(queriesFromFile))
                return null;
            
            return execStoredQueries();
        }
        catch(IOException e)
        {
            System.out.println("execQueryFromFile(): IOException: " + e.getMessage());
        }
        finally {
            lastAlias = null;
            outputFile = null;
            pigServer = null;
        }
        
        return null;
        
    }// execQueryFromFile()
    
    
    /**
     * 
     */
    public static void testPigExecute(int queryIndex)
    {
        PigExecute pigExec;
        String[] output;
        
        try {
            pigExec = new PigExecute("local");
            output = pigExec.execQueryFromFile(queryIndex);
            
            if(output == null)
                System.out.println("Error!!!");
            else {
                System.out.println("Output...");
                for(int i = 0; i < output.length; ++i)
                    System.out.println(output[i]);
                System.out.println("END Output...");
            }
        }
        catch(IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } 
    }
    
    
    /**
     * 
     * @param pigQueries
     * @return
     * @throws IOException 
     */
    private boolean addQueries(String[] pigQueries) throws IOException {
        if(pigServer == null) return false;
        
        if(pigQueries.length <= 0) return false;
        
        for(int i = 0; i < pigQueries.length; ++i)
            pigServer.registerQuery(pigQueries[i]);
        
        // determine last alias and store it
        this.lastAlias = pigQueries[pigQueries.length-1].substring(0, 1);
        
        return true;
    }

    
    /**
     * Executes the pig latin script given as an array of string elements.
     * 
     * @param pigScript Array of String elements that contain the pig script
     *          to be executed
     * @return 
     * @throws IOException 
     */
    private String[] execStoredQueries() throws IOException {
        
        ExecJob jobExec;
                
        if(pigServer == null || lastAlias == null ||
                outputFile == null) 
            return null;
        
        System.out.println("Executing: store " + this.lastAlias + " into " 
                + this.outputFile);
        
        jobExec = pigServer.store(this.lastAlias, this.outputFile);
        
        if(jobExec.hasCompleted())
        {
            if(jobExec.getStatus() == ExecJob.JOB_STATUS.COMPLETED)
            {
                System.out.println("Success executing script...");
                
                // read output from file and return as a String object
                String[] outStr;
                outStr = FileHelper.readFileAsStringArray(this.outputFile);

                return outStr;
            }
            
            System.out.println("Failed executing script...\n");
            return null;
        }

        System.out.println("ERROR executing script...\n");
        return null;
    
    }// execStoredQueries()
    
    /**
     * Helper to construct query file path given the file index
     * and check whether that file exists or not.
     * @param fileIndex
     * @return 
     */
    private String getFilepathFromIndex(int fileIndex)
    {
        if(fileIndex < 0)
            return null;
        
        File queryFile;
        
        //String filePath = this.queryFilepathBegin;
        String fileFullPath = this.queryFilepathBegin.concat(new Integer(fileIndex).toString());
        
        queryFile = new File(fileFullPath);
        if(!queryFile.exists())
            return null;
        
        return fileFullPath;
        
    }// getFilenameFromIndex()
    
    
}// class PigExec
