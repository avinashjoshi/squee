##############################
##                          ##
##          README          ##
##                          ##
##############################

Collaborators:
  Arun Agarwal <> 
  Avinash Joshi <axj107420@utdallas.edu>
  Shishir Krishnaprasad <sxk116430@utdallas.edu>
 
(c) 2012 Squee

Secure Cloud Computing

Squee = Secure Query Execution Engine
=====================================

Introduction:
-------------
This project is a Java based 'desktop application' that has admin page where you can add user, roles and map user to roles to resources. The basic functionality is to check that only qualifying user has access to resources based on the policy file. The engine also takes as input a Pig script (pre-defined) and executes it on data stored in HDFS, only if the querying user is allowed to access all input files specified in the script.

The modules:
------------
1. Hadoop Cluster
2. Pig Setup
3. The Engine: Squee
     - 
