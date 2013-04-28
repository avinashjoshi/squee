package com.utd.scc.squee.helper;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Helper file for User Interface initialization.
 * 
 * @author Arun Agarwal <axa103521@utdallas.edu>
 * @author Avinash Joshi <axj107420@utdallas.edu>
 * @author Shishir Krishnaprasad <sxk116430@utdallas.edu>
 */
public class UIHelper {

    /**
     * Takes string as an argument and sets the title for a MAC machine
     * 
     * @param value 
     */
    public static void setTitle(String value) {
        try {
            // take the menu bar off the jframe
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            // set the name of the application menu item
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", value);

            // set the look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(UIHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
