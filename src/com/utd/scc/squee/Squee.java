/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utd.scc.squee;

import com.utd.itc.godse.helper.UIHelper;
import com.utd.scc.squee.view.LoginForm;

/**
 *
 * @author avinash
 */
public class Squee {

    public static void main(String[] args) {
        UIHelper.setTitle("GODSe");
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
}
