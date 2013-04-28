package com.utd.scc.squee.action;

import com.utd.scc.squee.crypto.SHA;
import com.utd.scc.squee.view.AdminForm;
import com.utd.scc.squee.view.LoginForm;
import com.utd.scc.squee.view.UserPage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Helper file for User Interface initialization.
 * 
 * @author Arun Agarwal <axa103521@utdallas.edu>
 * @author Avinash Joshi <axj107420@utdallas.edu>
 * @author Shishir Krishnaprasad <sxk116430@utdallas.edu>
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
            String rootPasswdHash = "ce5ca673d13b36118d54a7cf13aeb0ca012383bf771e713421b4d1fd841f539a";
            
            /*
             * the default Administrative password is toor
             */
            if (userName.equals("root") && SHA.SHA512String(password).equals(rootPasswdHash)) {
                AdminForm aForm;
                aForm = new AdminForm(this.loginForm);
                this.loginForm.setVisible(false);
                aForm.setVisible(true);
            } else if (((passwordHash = loginForm.getLoadedPassword(userName)) != null)
                    && passwordHash.equals(SHA.SHA512String(password))) {
                loginForm.setErrorMessage("User " + userName + " authenticated");
                UserPage uPage;
                uPage = new UserPage(userName, loginForm);
                this.loginForm.setVisible(false);
                uPage.setVisible(true);
                // Call the User form here
            } else {
                loginForm.setErrorMessage("Invalid username or password");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
