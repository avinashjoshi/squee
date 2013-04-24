/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utd.scc.squee.view;

import com.utd.scc.squee.crypto.SHA;
import com.utd.scc.squee.policy.AdminFunctions;
import com.utd.scc.squee.policy.Group;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author avinash
 */
public class AdminForm extends javax.swing.JFrame {

    /**
     * Creates new form AdminForm
     */
    LoginForm lForm;
    DefaultTableModel tableModel;
    AdminFunctions admin;
    File roleUserMap = new File("etc/role_user_map.xml");

    public AdminForm(LoginForm lForm) {
        initComponents();
        this.lForm = lForm;
        myInitComponents();
    }

    private void myInitComponents() {
        if ( !roleUserMap.exists() || (roleUserMap.length() <= 0) ) {
            // Just a hanging function.. its ok
            new AdminFunctions("etc/role_user_map.xml", "etc/default_role_user_map.txt");
        }
        //this is the main one... we need to pass null
        admin = new AdminFunctions("etc/role_user_map.xml", null);
        refreshUserRole();
        loadResources();
        tableModel = (DefaultTableModel) roleTable.getModel();
        loadRoles();

    }

    public void setAddErrorMessage(String message) {
        addErrMsg.setVisible(true);
        addErrMsg.setText("");
        addErrMsg.setText("* " + message);
    }

    public void clearErrorMessages() {
        addErrMsg.setText("");
        addErrMsg.setVisible(false);
    }

    public void setPermErrorMessage(String message) {
        permErrMsg.setVisible(true);
        permErrMsg.setText("");
        permErrMsg.setText("* " + message);
    }

    public void refreshUserRole() {
        userListArea.setText("");
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(lForm.getPasswdFD());
            BufferedReader bufFileReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufFileReader.readLine()) != null) {
                String[] splitLine = line.split(":");
                userListArea.append(splitLine[0] + "\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loadResources() {
        resources.removeAllItems();
        resources.addItem("--Select One--");
        FileReader fileReader;
        File resourceFd = new File("etc/resources.txt");
        if (!resourceFd.exists()) {
            setPermErrorMessage("etc/resources.txt file not found. Please add");
        } else {
            try {
                fileReader = new FileReader(resourceFd);
                BufferedReader bufFileReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufFileReader.readLine()) != null) {
                    resources.addItem(line);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loadRoles() {
        roleListArea.setText("");
        int i, j;
        for (i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }


        ArrayList<Group> listGroups = admin.getLoadDataXml().getGroups();

        for (i = 0; i < listGroups.size(); i++) {
            roleListArea.append(listGroups.get(i).getGroupID() + ": ");
            for(j = 0; j < listGroups.get(i).getMembers().size(); j++){
                roleListArea.append(listGroups.get(i).getMembers().get(j).getMember());
                if (j < listGroups.get(i).getMembers().size() - 1 ) {
                    roleListArea.append(", ");
                }
            }
            roleListArea.append("\n");
            tableModel.addRow(new Object[]{listGroups.get(i).getGroupID(), false});
        }
    }

    public void createPerm() {
        String selectedResource = (String) resources.getSelectedItem();
        ArrayList<String> selectedRoles = new ArrayList<String>();
        int i;
        Boolean flag = false;
        for (i = tableModel.getRowCount() - 1; i >= 0; i--) {
            if (((Boolean) tableModel.getValueAt(i, 1)) == true) {
                selectedRoles.add((String) tableModel.getValueAt(i, 0));
                flag = true;
            }
        }
        if (flag) {
            if (resources.getSelectedIndex() == 0) {
                setPermErrorMessage("Select at least one resource!");
            } else {
                admin.addPolicyFiles(selectedResource, selectedRoles);
            }
        } else {
            //At least one role must be selected
            setPermErrorMessage("Select at least one role!");
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

        jComboBox2 = new javax.swing.JComboBox();
        listTab = new javax.swing.JTabbedPane();
        listUsersPane = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userListArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        roleListArea = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        addUserPane = new javax.swing.JPanel();
        newUserName = new javax.swing.JTextField();
        addUser = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        newPassword = new javax.swing.JTextField();
        newRole = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        addErrMsg = new javax.swing.JLabel();
        permissionsPane = new javax.swing.JPanel();
        resources = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        createPerm = new javax.swing.JButton();
        permErrMsg = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        roleTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        refreshUserList = new javax.swing.JButton();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Squee | Administer");
        setMinimumSize(new java.awt.Dimension(501, 444));
        setPreferredSize(new java.awt.Dimension(500, 450));
        setResizable(false);

        listTab.setAutoscrolls(true);
        listTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listTabMouseClicked(evt);
            }
        });

        jLabel2.setText("All Users:");

        userListArea.setEditable(false);
        userListArea.setBackground(new java.awt.Color(255, 255, 255));
        userListArea.setColumns(15);
        userListArea.setFont(new java.awt.Font("Ubuntu Mono", 0, 15)); // NOI18N
        userListArea.setRows(3);
        jScrollPane1.setViewportView(userListArea);

        roleListArea.setEditable(false);
        roleListArea.setBackground(new java.awt.Color(255, 255, 255));
        roleListArea.setColumns(15);
        roleListArea.setFont(new java.awt.Font("Ubuntu Mono", 0, 15)); // NOI18N
        roleListArea.setRows(5);
        jScrollPane3.setViewportView(roleListArea);

        jLabel8.setText("role: user Mapping");

        javax.swing.GroupLayout listUsersPaneLayout = new javax.swing.GroupLayout(listUsersPane);
        listUsersPane.setLayout(listUsersPaneLayout);
        listUsersPaneLayout.setHorizontalGroup(
            listUsersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listUsersPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(listUsersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(listUsersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(listUsersPaneLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
                .addContainerGap())
        );
        listUsersPaneLayout.setVerticalGroup(
            listUsersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listUsersPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(listUsersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(listUsersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        listTab.addTab("List Users", listUsersPane);

        newUserName.setToolTipText("Enter the username");

        addUser.setText("Add User");
        addUser.setToolTipText("Add a new user");
        addUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserActionPerformed(evt);
            }
        });

        jLabel3.setText("UserName:");

        jLabel4.setText("Password:");

        newPassword.setToolTipText("Enter password");

        newRole.setToolTipText("Enter existing or new role");

        jLabel5.setText("Role:");

        addErrMsg.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout addUserPaneLayout = new javax.swing.GroupLayout(addUserPane);
        addUserPane.setLayout(addUserPaneLayout);
        addUserPaneLayout.setHorizontalGroup(
            addUserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addUserPaneLayout.createSequentialGroup()
                .addGroup(addUserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addUserPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(addUserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(addUserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(newPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(newUserName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(newRole)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addUserPaneLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addUser)))
                .addContainerGap())
            .addGroup(addUserPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addErrMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );
        addUserPaneLayout.setVerticalGroup(
            addUserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addUserPaneLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(addUserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(addUserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(newPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addUserPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(addUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addErrMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        listTab.addTab("Add User to Role", addUserPane);

        resources.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Select one --" }));

        jLabel6.setText("Resource:");

        jLabel7.setText("Roles:");

        createPerm.setText("Create Policy File");
        createPerm.setToolTipText("Update the permissions for selected resource");
        createPerm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPermActionPerformed(evt);
            }
        });

        permErrMsg.setForeground(new java.awt.Color(255, 0, 0));

        roleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Role", "Selected"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        roleTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(roleTable);
        roleTable.getColumnModel().getColumn(0).setResizable(false);
        roleTable.getColumnModel().getColumn(1).setResizable(false);
        roleTable.getColumnModel().getColumn(1).setPreferredWidth(20);

        javax.swing.GroupLayout permissionsPaneLayout = new javax.swing.GroupLayout(permissionsPane);
        permissionsPane.setLayout(permissionsPaneLayout);
        permissionsPaneLayout.setHorizontalGroup(
            permissionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, permissionsPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(permissionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(permissionsPaneLayout.createSequentialGroup()
                        .addGroup(permissionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resources, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(permissionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(permissionsPaneLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(permissionsPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(createPerm))
                    .addComponent(permErrMsg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        permissionsPaneLayout.setVerticalGroup(
            permissionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(permissionsPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(permissionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(permissionsPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resources, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createPerm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(permErrMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        listTab.addTab("Permissions", permissionsPane);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setText("Admin UI");

        logout.setText("Logout");
        logout.setToolTipText("Click here to Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        refreshUserList.setText("Refresh");
        refreshUserList.setToolTipText("Click here to refresh users list");
        refreshUserList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshUserListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(listTab, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refreshUserList)
                        .addGap(18, 18, 18)
                        .addComponent(logout)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(logout)
                    .addComponent(refreshUserList))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listTab, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        lForm.setVisible(true);
    }//GEN-LAST:event_logoutActionPerformed

    private void addUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserActionPerformed
        try {
            // TODO add your handling code here:
            String userName = newUserName.getText();
            String password = newPassword.getText();
            String role = newRole.getText();
            String passwordHash = SHA.SHA512String(password);

            clearErrorMessages();

            if ("".equals(userName)
                    || "".equals(password)
                    || "".equals(role)) {
                setAddErrorMessage("All Fields necessary!");
            } else if (lForm.checkUserName(userName) == false) {
                setAddErrorMessage("Username can be only have:\n [a-z], [0-9], _ or - ");
                newUserName.requestFocus();
            } else if (lForm.checkUserName(role) == false) {
                setAddErrorMessage("Role can be only have:\n [a-z], [0-9], _ or - ");
                newRole.requestFocus();
            } else {

                String msg = "";
                // If user is not admin and not in the loaded list
                if ((lForm.getLoadedPassword(userName) == null)
                        && (!userName.equals("admin"))) {

                    //add to etc/passwd file
                    FileWriter fileWriter = new FileWriter(lForm.getPasswdFD(), true);
                    BufferedWriter bufWriter = new BufferedWriter(fileWriter);
                    bufWriter.write(userName + ":" + passwordHash + "\n");
                    lForm.setUserToPass(userName, passwordHash);
                    bufWriter.close();
                    fileWriter.close();
                    msg = "Added new user to file! ";
                }

                //call AdminFunction.add

                admin.addUserToRole(role, userName);
                setAddErrorMessage(msg + "Added user to role!");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AdminForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addUserActionPerformed

    private void refreshUserListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshUserListActionPerformed
        // TODO add your handling code here:
        refreshUserRole();
        loadRoles();
        loadResources();
    }//GEN-LAST:event_refreshUserListActionPerformed

    private void listTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTabMouseClicked
        // TODO add your handling code here:
        refreshUserRole();
        loadRoles();
    }//GEN-LAST:event_listTabMouseClicked

    private void createPermActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPermActionPerformed
        // TODO add your handling code here:
        createPerm();
    }//GEN-LAST:event_createPermActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addErrMsg;
    private javax.swing.JButton addUser;
    private javax.swing.JPanel addUserPane;
    private javax.swing.JButton createPerm;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane listTab;
    private javax.swing.JPanel listUsersPane;
    private javax.swing.JButton logout;
    private javax.swing.JTextField newPassword;
    private javax.swing.JTextField newRole;
    private javax.swing.JTextField newUserName;
    private javax.swing.JLabel permErrMsg;
    private javax.swing.JPanel permissionsPane;
    private javax.swing.JButton refreshUserList;
    private javax.swing.JComboBox resources;
    private javax.swing.JTextArea roleListArea;
    private javax.swing.JTable roleTable;
    private javax.swing.JTextArea userListArea;
    // End of variables declaration//GEN-END:variables
}
