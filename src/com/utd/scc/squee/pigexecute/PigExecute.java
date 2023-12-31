package com.utd.scc.squee.pigexecute;

import java.io.*;
import java.util.Map;

import org.apache.pig.PigServer;
import org.apache.pig.backend.executionengine.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import com.utd.scc.squee.helper.FileHelper;

/**
 * Pig Execution Class file
 *
 * @author Arun Agarwal <axa103521@utdallas.edu>
 * @author Avinash Joshi <axj107420@utdallas.edu>
 * @author Shishir Krishnaprasad <sxk116430@utdallas.edu>
 */
public class PigExecute {

    private PigServer pigServer = null;
    private String lastAlias = null;
    private String outputFile = null;
    private String strHDFSXml = null;
    private String strCoreXml = null;
    private final String queryFilepathBegin = "queries/q";
    private final String outputFilepathBegin = "/output/o";

    /**
     * Constructor
     *
     * @param execTypeString
     * @param outputFile
     * @throws IOException
     */
    public PigExecute(String execTypeString) throws IOException {
        pigServer = new PigServer(execTypeString);
        Map<String, String> env = System.getenv();
        String strHadoopHome = env.get("HADOOP_HOME");
        if (strHadoopHome == null) {
            throw new IOException("HADOOP_HOME env var not found!");
        }
        strHDFSXml = strHadoopHome.concat("/conf/hdfs-site.xml");
        strCoreXml = strHadoopHome.concat("/conf/core-site.xml");
    }

    /**
     * Executes a pig query from a file specified by fileIndex.
     *
     * @param fileIndex
     * @return String array containing the output.
     */
    public String[] execQueryFromFile(int fileIndex) {

        String qFilepath;
        String[] queriesFromFile;

        qFilepath = getFilepathFromIndex(fileIndex);
        if (qFilepath == null) {
            return null;
        }

        queriesFromFile = FileHelper.readFileAsStringArray(qFilepath);
        outputFile = outputFilepathBegin.concat(new Integer(fileIndex).toString());

        if (!FileHelper.deleteHDFSDirectory(outputFile,
                this.strCoreXml, this.strHDFSXml)) {
            System.out.println("Unable to delete existing output directory!!");
            return null;
        }

        try {
            if (!addQueries(queriesFromFile)) {
                return null;
            }

            return execStoredQueries();
        } catch (IOException e) {
            System.out.println("execQueryFromFile(): IOException: " + e.getMessage());
        } finally {
            lastAlias = null;
            outputFile = null;
            pigServer = null;
        }

        return null;

    }// execQueryFromFile()

    /**
     * A unit test function to execute the given query specified using the query
     * file index.
     *
     * @param queryIndex Zero-based index into the queries directory.
     */
    public static void testPigExecute(int queryIndex) {
        PigExecute pigExec;
        String[] output;

        try {
            pigExec = new PigExecute("local");
            output = pigExec.execQueryFromFile(queryIndex);

            if (output == null) {
                System.out.println("Error!!!");
            } else {
                System.out.println("Output...");
                for (int i = 0; i < output.length; ++i) {
                    System.out.println(output[i]);
                }
                System.out.println("END Output...");
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }// testPigExecute()

    /**
     * Add queries supplied as string array into the current PigServer instance.
     * Also determines and stores the 'alias' of the last statement in the query
     * list.
     *
     * @param pigQueries
     * @return false if PigServer has not been instantiated or if the number of
     * queries is less than or equal to zero, true otherwise.
     * @throws IOException
     */
    private boolean addQueries(String[] pigQueries) throws IOException {
        if (pigServer == null) {
            return false;
        }

        if (pigQueries.length <= 0) {
            return false;
        }

        for (int i = 0; i < pigQueries.length; ++i) {
            pigServer.registerQuery(pigQueries[i]);
        }

        // determine last alias and store it
        this.lastAlias = pigQueries[pigQueries.length - 1].substring(0, 1);

        return true;
    }

    /**
     * Executes the pig latin script whose queries are stored into the current
     * PigServer instance.
     *
     * @return If execution succeeds, a reference to a string array that holds
     * the output of the query. If execution fails or if any private member
     * variables are invalid then null is returned.
     * @throws IOException
     */
    private String[] execStoredQueries() throws IOException {

        ExecJob jobExec;

        if (pigServer == null || lastAlias == null
                || outputFile == null) {
            return null;
        }

        System.out.println("Executing: store " + this.lastAlias + " into "
                + this.outputFile);

        jobExec = pigServer.store(this.lastAlias, this.outputFile);

        if (jobExec.hasCompleted()) {
            if (jobExec.getStatus() == ExecJob.JOB_STATUS.COMPLETED) {
                System.out.println("Success executing script...");

                // read output from file and return as a String object
                Path outputFilepath = this.getOutputFilepath(outputFile);
                if (outputFilepath == null) {
                    System.out.println("Unable to construct output file path!!");
                    return null;
                }
                return FileHelper.readHDFSFileAsStringArray(outputFilepath,
                        this.strCoreXml, this.strHDFSXml);
            }

            System.out.println("Failed executing script...\n");
            return null;
        }

        System.out.println("ERROR executing script...\n");
        return null;

    }// execStoredQueries()

    /**
     * Helper to construct query file path given the file index and check
     * whether that file exists or not.
     *
     * @param fileIndex Zero-based index into the queries directory
     * @return null if invalid fileIndex or if query file doesn't exist.
     * Otherwise a reference to the string containing the fullpath to the query
     * file is returned.
     */
    private String getFilepathFromIndex(int fileIndex) {
        if (fileIndex < 0) {
            return null;
        }

        File queryFile;

        //String filePath = this.queryFilepathBegin;
        String fileFullPath = this.queryFilepathBegin.concat(new Integer(fileIndex).toString());

        queryFile = new File(fileFullPath);
        if (!queryFile.exists()) {
            return null;
        }

        return fileFullPath;

    }// getFilenameFromIndex()

    /**
     * Returns a Path instance that points to the output directory on the HDFS.
     *
     * @param outputDir String specifying the relative path of the output
     * directory
     * @return
     */
    private Path getOutputFilepath(String outputDir) {
        int i = 0;

        Configuration conf;
        FileSystem fs;

        Path fpath;
        Path tempPath = null;

        FileStatus[] listFiles;

        conf = new Configuration();
        conf.addResource(new Path(strCoreXml));
        conf.addResource(new Path(strHDFSXml));

        try {
            fpath = new Path(outputDir);
            fs = FileSystem.get(conf);
            if (!fs.exists(fpath)) {
                return null;
            }
            if (!fs.isDirectory(fpath)) {
                return null;
            }

            listFiles = fs.listStatus(fpath);
            for (; i < listFiles.length; ++i) {
                tempPath = listFiles[i].getPath();
                if (fs.isFile(tempPath)) {
                    if (tempPath.getName().startsWith("part")) {
                        break;
                    }
                }
            }// for i

            if (i == listFiles.length) {
                return null;
            }

            return tempPath;

        } catch (IOException e) {
            System.out.println("getOutputFilepath(): IOException " + e.getMessage());
        }

        return null;

    }// getOutputFilepath()
}// class PigExec
