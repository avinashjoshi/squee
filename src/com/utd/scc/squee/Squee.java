/* 
 * This project is a Java based 'desktop application' that has 
 * admin page where you can add user, roles and map user to roles to resources.
 * The basic functionality is to check that only qualifying user has access to 
 * resources based on the policy file. 
 * 
 * The engine also takes as input a Pig script (pre-defined) and 
 * executes it on data stored in HDFS, only if the querying user 
 * is allowed to access all input files specified in the script.
 * 
 * Collaborators:
 * Arun Agarwal <axa103521@utdallas.edu>
 * Avinash Joshi <axj107420@utdallas.edu>
 * Shishir Krishnaprasad <sxk116430@utdallas.edu>
 * 
 * (c) 2013 Squee
 */
package com.utd.scc.squee;

import com.utd.scc.squee.helper.UIHelper;
import com.utd.scc.squee.view.LoginForm;

public class Squee {

    public static void main(String[] args) {

        UIHelper.setTitle("Squee");
        
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);

    }// main()
}
