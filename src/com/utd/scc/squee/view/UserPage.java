package com.utd.scc.squee.view;

import com.utd.scc.squee.pigexecute.PigExecute;
import com.utd.scc.squee.policy.AdminFunctions;
import com.utd.scc.squee.policy.XPDP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * User page
 *
 * @author Arun Agarwal <axa103521@utdallas.edu>
 * @author Avinash Joshi <axj107420@utdallas.edu>
 * @author Shishir Krishnaprasad <sxk116430@utdallas.edu>
 */
public class UserPage extends javax.swing.JFrame {

    private HashMap<String, ArrayList<String>> queryResourceMapping = new HashMap<String, ArrayList<String>>();
    private HashMap<Integer, ArrayList<String>> queryIndexResourceMapping = new HashMap<Integer, ArrayList<String>>();
    private String queryFileName = "etc/queries.txt";
    private final String queryFilepathBegin = "queries/q";
    private final String resourceFilepathBegin = "hdfs://localhost:54310/data/";
    private XPDP policyEvaluator;
    private String[] policyFiles;
    private AdminFunctions adminFunction = null;
    private String userName;
    LoginForm lForm;

    /**
     * Creates new form UserPage
     */
    public UserPage(String userName, LoginForm lForm) {
        initComponents();
        this.lForm = lForm;
        this.userName = userName;
        String loggedInString = "Logged in as: " + userName;
        loggedUser.setText(loggedInString);
        adminFunction = new AdminFunctions();
        readQueryFile();
    }

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

    /*
     * @param - none
     * @return - void
     * @Function - read the queryFile .
     * 
     */
    public final void readQueryFile() {
        ArrayList<String> queries = new ArrayList<String>();
        int qIndex = 0;
        FileReader reader = null;
        BufferedReader bufferReader = null;
        File file;
        try {
            file = new File(queryFileName);
            reader = new FileReader(file);
            bufferReader = new BufferedReader(reader);
            String line;
            while ((line = bufferReader.readLine()) != null) {
                queries.add(line);
                queryIndexResourceMapping.put(qIndex, getResourcesFromQueryFile(qIndex));
                qIndex++;
            }

            updateQueryList(queries);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserPage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(UserPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private ArrayList<String> getResourcesFromQueryFile(int qIndex) {
        FileReader reader = null;
        BufferedReader bufferReader = null;
        ArrayList<String> resourceFilesForQuery = new ArrayList<String>();

        File file;
        try {

            file = new File(getFilepathFromIndex(qIndex));
            reader = new FileReader(file);
            bufferReader = new BufferedReader(reader);
            String line;

            while ((line = bufferReader.readLine()) != null) {

                String[] actQuery = line.split("=");
                String[] sepQuery = actQuery[1].trim().split(" ");
                String command = sepQuery[0].trim();
                String resourceLong = sepQuery[1].trim();

                if (command.equalsIgnoreCase("load")) {
                    String resource = resourceLong
                            .replace("'", "");
                    resourceFilesForQuery.add(resource);
                }

            }
            return resourceFilesForQuery;
        } catch (IOException ex) {
            Logger.getLogger(UserPage.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(UserPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*
     * @Param - List of queries read from the text File
     * @Return - void
     * @Function - updates the JComboBox with the available 
     * user excutable queries
     * 
     */
    public void updateQueryList(ArrayList<String> queries) {
        ArrayList<String> resourceFilesForQuery;
        String queryToBeShown;
        for (String query : queries) {
            String[] sepQuery = query.split(";");
            queryToBeShown = sepQuery[0];
            resourceFilesForQuery = new ArrayList<String>();
            for (int i = 1; i < sepQuery.length; i++) {
                resourceFilesForQuery.add(sepQuery[i].trim());
            }
            queryResourceMapping.put(queryToBeShown, resourceFilesForQuery);
            queriesList.addItem(queryToBeShown);

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        queriesList = new javax.swing.JComboBox();
        submit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        queryResult = new javax.swing.JTextArea();
        loggedUser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Squee | User UI");
        setMinimumSize(new java.awt.Dimension(500, 420));
        setName("User Area"); // NOI18N
        setPreferredSize(new java.awt.Dimension(500, 420));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setText("User UI");

        logout.setText("Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        queriesList.setToolTipText("Select Query To Execute");

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        jScrollPane1.setEnabled(false);

        queryResult.setEditable(false);
        queryResult.setColumns(20);
        queryResult.setRows(5);
        queryResult.setBorder(null);
        jScrollPane1.setViewportView(queryResult);

        loggedUser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loggedUser.setText("Logged as:");
        loggedUser.setMaximumSize(null);
        loggedUser.setMinimumSize(null);
        loggedUser.setPreferredSize(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(loggedUser, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(queriesList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(submit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(logout)
                        .addComponent(loggedUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(queriesList, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submit))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed

        // TODO add your handling code here:
        queryResult.setText("");
        String query = (String) queriesList.getSelectedItem();
        int index = queriesList.getSelectedIndex();
        //ArrayList<String> listOfResourceForQuery = queryResourceMapping.get(query);
        ArrayList<String> listOfResourceForQuery = queryIndexResourceMapping.get(index);
        boolean result = false;
        adminFunction.updatePolicyFiles();
        result = adminFunction.checkPermissions("etc/role_user_map.xml", this.userName, listOfResourceForQuery);
        if (result) {
            try {
                // call the pig execute
                //PigExecute pigExec = new PigExecute("local");
                PigExecute pigExec = new PigExecute("mapreduce");
                String[] resultOfQuery = pigExec.execQueryFromFile(index);
                if (resultOfQuery != null) {
                    showResults(resultOfQuery);
                } else {
                    showResults(new String[]{"Error", "In Executing Script"});
                }
                System.out.println("Execute pig script ");
            } catch (IOException ex) {
                Logger.getLogger(UserPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Insufficient Permissions",
                    "You dont have the required permission to access the query",
                    JOptionPane.ERROR_MESSAGE);
        }
        // call the pig script fot the query

    }//GEN-LAST:event_submitActionPerformed

    private void showResults(String[] result) {
        for (String line : result) {
            queryResult.append(line + "\n");
        }
    }

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        lForm.setVisible(true);
        lForm.setErrorMessage("User " + userName + " logged out !!");
    }//GEN-LAST:event_logoutActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel loggedUser;
    private javax.swing.JButton logout;
    private javax.swing.JComboBox queriesList;
    private javax.swing.JTextArea queryResult;
    private javax.swing.JButton submit;
    // End of variables declaration//GEN-END:variables
}
