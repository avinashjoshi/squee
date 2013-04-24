/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utd.scc.squee;

import com.utd.itc.godse.helper.UIHelper;
import com.utd.scc.squee.view.LoginForm;
import com.utd.scc.squee.pigexecute.PigExecute;

/**
 *
 * @author avinash
 */
public class Squee {

    public static void main(String[] args) {
        
        /*
         * Pass the index of the query to be executed.
         * Reads from queries/ folder.
         * Output stored in output/ and dumped to stdout
         */
        //PigExecute.testPigExecute(1);
        
        
        UIHelper.setTitle("GODSe");
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }// main()
}
