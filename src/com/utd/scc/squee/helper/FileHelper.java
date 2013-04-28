package com.utd.scc.squee.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Helper class for operating on files
 *
 * @author Arun Agarwal <axa103521@utdallas.edu>
 * @author Avinash Joshi <axj107420@utdallas.edu>
 * @author Shishir Krishnaprasad <sxk116430@utdallas.edu>
 */
public class FileHelper {

    /**
     * Reads a text file specified via its path name and returns the file
     * contents as a string array.
     *
     * @param filePath Path to file
     * @return String array of file contents. If error occurs, null is returned.
     */
    public static String[] readFileAsStringArray(String filePath) {
        BufferedReader bufReader;

        String strLine;
        StringBuilder outStrBuild;

        try {
            bufReader = new BufferedReader(
                    new FileReader(filePath));

            outStrBuild = new StringBuilder();
            while ((strLine = bufReader.readLine()) != null) {
                outStrBuild.append(strLine);
                outStrBuild.append('\n');
            }

            return outStrBuild.toString().split("[\n]");
        } catch (FileNotFoundException e) {
            System.out.println("Output file " + filePath + " not found!!");
        } catch (IOException e) {
            System.out.println("Error reading from " + filePath);
        }

        return null;

    }// readFileAsStringArray()

    /**
     * Reads a file, in HDFS, specified via the filePath argument and returns
     * the file contents as a string array.
     *
     * @param filePath
     * @return String array containing the file contents.
     */
    public static String[] readHDFSFileAsStringArray(Path filePath,
            String coreXML, String hdfsXML) {
        BufferedReader bufReader;

        String strLine;
        StringBuilder outStrBuild;

        FileSystem fs;
        Configuration conf;

        conf = new Configuration();
        conf.addResource(new Path(coreXML));
        conf.addResource(new Path(hdfsXML));

        try {
            fs = FileSystem.get(conf);

            bufReader = new BufferedReader((new InputStreamReader(fs.open(filePath))));

            outStrBuild = new StringBuilder();
            while ((strLine = bufReader.readLine()) != null) {
                outStrBuild.append(strLine);
                outStrBuild.append('\n');
            }
            bufReader.close();

            return outStrBuild.toString().split("[\n]");

        } catch (FileNotFoundException e) {
            System.out.println("Output file " + filePath.getName() + " not found!!");
        } catch (IOException e) {
            System.out.println("Error reading from " + filePath.getName());
        }

        return null;

    }// readFileAsStringArray()

    /**
     * Recursive directory deletion.
     *
     * @param dir Path to directory to be deleted.
     * @return true/false indicating success or failure to delete the directory.
     */
    public static boolean deleteDirectory(String dir) {
        File dirFile = new File(dir);

        return deleteDirRecursive(dirFile);
    }

    /**
     * Deletes a directory present in the HDFS.
     *
     * @param dirString Relative path of the directory in HDFS.
     * @return true/false depending on whether the specified directory
     * was deleted or not.
     */
    public static boolean deleteHDFSDirectory(String dirString,
            String coreXML, String hdfsXML) {
        FileSystem fs;
        Path dirPath;
        Configuration conf;

        conf = new Configuration();
        conf.addResource(new Path(coreXML));
        conf.addResource(new Path(hdfsXML));

        try {
            fs = FileSystem.get(conf);
            dirPath = new Path(dirString);
            if (fs.exists(dirPath)) {
                return fs.delete(dirPath, true);
            }
            return true;
        } catch (IOException e) {
            System.out.println("IOException " + e.getMessage());
        }
        return false;

    }

    /**
     * Recursive directory delete worker function.
     *
     * @param f File to be deleted.
     * @return true/false depending on whether f was deleted or not.
     */
    private static boolean deleteDirRecursive(File f) {
        if (!f.exists()) {
            return true;
        }

        if (f.isDirectory()) {
            for (File f1 : f.listFiles()) {
                deleteDirRecursive(f1);
            }
        }

        return f.delete();
    }
    
}// FileHelper
