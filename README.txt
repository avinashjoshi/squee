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
     
How to run the application :
----------------------------
Note: Build the entire project under java version 1.6 or higher and has dependency on sunxacml.jar
We need to have Hadoop installed in any of the modes and also PIG installed.

Use the following command to run SQUEE 

java Squee 

The project has the following dependencies :

1. Role_to_user_map.xml = Either we need the default mapping of the user or when u run the project pass in the
    text file from which role user mapping is read . (Should be present in etc/ folder of the project)
2. Resources.txt = List of all the resources in the HDFS (Should be present in etc/ folder of the project)
3. passwd file = This files stores the password of all the users . (Should be present in etc/ folder of the project)
4. queries.txt =  this contains a pre-defines list of Queries provided to users . The queries follow 
   an order corresponding to the files in the /Queries folder. (Should be present in etc/ folder of the project)

 All the resources which are used by the project are stored in the HDFS installation of hadoop .
