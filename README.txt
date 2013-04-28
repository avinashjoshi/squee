##############################
##                          ##
##          README          ##
##                          ##
##############################

Collaborators:
  Arun Agarwal <axa103521@utdallas.edu> 
  Avinash Joshi <axj107420@utdallas.edu>
  Shishir Krishnaprasad <sxk116430@utdallas.edu>
 
(c) 2013 Squee

Secure Cloud Computing

Squee = Secure Query Execution Engine
=====================================

Introduction:
-------------
This project is a Java based 'desktop application' that has admin page where you 
can add user, roles and map user to roles to resources. The basic functionality is to 
check that only qualifying user has access to resources based on the policy file. The engine
also takes as input a Pig script (pre-defined) and executes it on data stored in HDFS, 
only if the querying user is allowed to access all input files specified in the script.

The modules:
------------
1. Hadoop Cluster
2. Pig Setup
3. The Engine: Squee
     - Login UI
     - Admin UI
     - Query evaluation
     
How to setup the hadoop cluster:
--------------------------------
Follow the steps mentioned in [1]. We have used hadoop version 1.0.4. Important points to be 
followed are given below:
- Ensure "<hadoop_home_dir>/bin" is present in your PATH variable.
- Ensure HADOOP_HOME environment variable is set to the root directory of your hadoop installation.
- Ensure the local HDFS file system has been formatted. Use "hadoop namenode -format" to format it. 
  Before doing this, stop all hadoop process by executing "<hadoop_home_dir>/bin/stop-all.sh".
  After formatting, execute "<hadoop_home_dir>/bin/start-all.sh" and see if all required processes
  are running using the "jps" command.
- Creating the resource files and output directory in HDFS.
  $ hadoop fs -mkdir /data	; creates directory "data" in the root of the HDFS.
  $ hadoop fs -mkdir /output
  $ hadoop fs -put <local_resource_file_path> /data/	; copies resource file from local FS to HDFS
- Ensure "<hadoop_home_dir>/hadoop-core-1.0.4.jar" and "<hadoop_home_dir>/conf" are present in the
  CLASSPATH.


How to setup pig and make it use HDFS:
--------------------------------------
Follow the steps mentioned in [2]. We have used pig version 0.11.1. Important points:
- Ensure the pig installation directory is present in PATH.
- Ensure "<pig_home_dir>/pig-0.11.1.jar" is present in your CLASSPATH.
- All pig scripts must reference every resource file as hdfs://localhost:54310/data/<resource_file>".
  The port number mentioned can be found in "<hadoop_home_dir>/conf/core-site.xml".
  If the port number is different, then squee.pigexecute.PigExecute.java file must be modified
  to have the correct port number.
- Results of the executed pig script will be stored in "hdfs://localhost:54310/output/<output_dir>".
  Results are also displayed in the User's GUI.


How to run the application :
----------------------------
Note: Build the entire project under java version 1.6 or higher and has dependency on sunxacml.jar
We need to have Hadoop installed in any of the modes and also Pig installed.

Use the following command to run SQUEE 

# Ensure sunxacml.jar, hadoop-core-1.0.4.jar, pig-0.11.1.jar and hadoop/conf
# are present in your CLASSPATH
java Squee

The project has the following dependencies :

1. Role_to_user_map.xml = Either we need the default mapping of the user or when u run the project pass in the
    text file from which role user mapping is read . (Should be present in etc/ folder of the project)
2. Resources.txt = List of all the resources in the HDFS (Should be present in etc/ folder of the project)
3. passwd file = This files stores the password of all the users . (Should be present in etc/ folder of the project)
4. queries.txt =  this contains a pre-defines list of Queries provided to users . The queries follow 
   an order corresponding to the files in the /Queries folder. (Should be present in etc/ folder of the project)

 All the resources which are used by the project are stored in the HDFS installation of hadoop .


References :
------------
[1] Running Hadoop on Ubuntu Linux (Single-Node Cluster)
http://www.michael-noll.com/tutorials/running-hadoop-on-ubuntu-linux-single-node-cluster

[2] Getting Started: Pig Setup
http://pig.apache.org/docs/r0.11.1/start.html#Pig+Setup
