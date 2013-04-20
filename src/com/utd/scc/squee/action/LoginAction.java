/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utd.scc.squee.action;

import com.utd.scc.squee.crypto.SHA;
import com.utd.scc.squee.view.AdminForm;
import com.utd.scc.squee.view.LoginForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author avinash
 */
public class LoginAction implements ActionListener, Runnable {

    public LoginForm loginForm;

    public LoginAction(LoginForm lgForm) {
        this.loginForm = lgForm;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String loginError;
        loginError = loginForm.validateInput();
        if ("".equals(loginError)) {
            loginForm.clearErrorMessage();
            new Thread(this).start();
        } else {
            loginForm.setErrorMessage(loginError);
        }
    }

    @Override
    public void run() {
        try {
            String userName = this.loginForm.getUserNameField();
            String password = this.loginForm.getPasswordField();
            String passwordHash;

            if (userName.equals("admin") && password.equals("admin")) {
                AdminForm aForm;
                aForm = new AdminForm(this.loginForm);
                this.loginForm.setVisible(false);
                aForm.setVisible(true);
            } else if (((passwordHash = loginForm.getLoadedPassword(userName)) != null)
                    && passwordHash.equals(SHA.SHA512String(password))) {
                loginForm.setErrorMessage("User " + userName + " authenticated");
                // Call the User form here
            } else {
                loginForm.setErrorMessage("Invalid username or password");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
