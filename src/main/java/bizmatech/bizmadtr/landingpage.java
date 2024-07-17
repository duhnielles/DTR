/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package bizmatech.bizmadtr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.SwingUtilities;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

/**
 *
 * @author Danielle
 */
public class landingpage extends javax.swing.JFrame implements Runnable {

     DBController dbController;
     int hour, seconds, minute;
     int day, month, year;
     private boolean isLoggedIn = false;
     private String currentUser;
     
    public landingpage() {
        initComponents();
        dbController = new DBController();
        loadUsersToDropbox();
        
        //login
        btnlogin.setEnabled(true);
        btnlogout.setEnabled(true);
        btntimein.setEnabled(false);
        btntimeout.setEnabled(false);
        
     
        //clock
        Thread t = new Thread(this);
        t.start();
        
        //date
        Date();
    }
    
    private void loadUsersToDropbox() {
        List<String> users = dbController.getUsers(); // Fetch the users from the database
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(users.toArray(new String[0])); // Create the model
        dropbox.setModel(model); // Set the model for the JComboBox
    }
        
    private void populateTable() {
    List<UserEntry> entries = dbController.getUserEntries(currentUser);
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); // Clear existing rows
    
     SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
     SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");
    
    for (UserEntry entry : entries) {
    String date = dateFormat.format(entry.getdate());
    String timeinAM = "";
    String timeoutAM = "";
    String timeinPM = "";
    String timeoutPM = "";

    if (entry.getTimein() != null) {
        timeinAM = timeFormat.format(entry.getTimein());
    }

    if (entry.getTimeout() != null) {
        timeoutAM = timeFormat.format(entry.getTimeout());
    }

    if (entry.getTimein_Pm() != null) {
        timeinPM = timeFormat.format(entry.getTimein_Pm());
    }

    if (entry.getTimeout_Pm() != null) {
        timeoutPM = timeFormat.format(entry.getTimeout_Pm());
    }

    model.addRow(new Object[]{entry.getname(), entry.getLastname(), date, timeinAM, timeoutAM, timeinPM, timeoutPM});
}

}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnlogin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtpass = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        btntimein = new javax.swing.JButton();
        btntimeout = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        runningtime = new javax.swing.JLabel();
        btnlogout = new javax.swing.JButton();
        dropbox = new javax.swing.JComboBox<>();
        txtuserlabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtdate = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("BIZMATECH"); // NOI18N
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));

        btnlogin.setText("Log In");
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });

        jLabel1.setText("User:");

        jLabel2.setText("Password:");

        jLabel3.setText("time");

        btntimein.setText("Time In");
        btntimein.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimeinActionPerformed(evt);
            }
        });

        btntimeout.setText("Time Out");
        btntimeout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimeoutActionPerformed(evt);
            }
        });

        jButton4.setText("View Summary");

        btnlogout.setText("Log Out");

        dropbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        dropbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropboxActionPerformed(evt);
            }
        });

        txtuserlabel.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        txtuserlabel.setForeground(new java.awt.Color(51, 153, 255));

        jLabel4.setText("date");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtuserlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btntimein)
                                .addGap(41, 41, 41)
                                .addComponent(btntimeout))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(37, 37, 37)
                                    .addComponent(runningtime, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dropbox, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jButton4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnlogin)
                                .addGap(48, 48, 48)
                                .addComponent(btnlogout)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dropbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnlogin)
                    .addComponent(btnlogout))
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(65, 65, 65)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtuserlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 25, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(15, 15, 15))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(runningtime, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntimein)
                    .addComponent(btntimeout))
                .addGap(48, 48, 48))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Name", "Last Name", "Date", "Time In (AM)", "Time Out (AM)", "Time In (PM)", "Time Out (PM)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dropboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropboxActionPerformed

    }//GEN-LAST:event_dropboxActionPerformed

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        String selectedUser = (String) dropbox.getSelectedItem();
        String password = new String(txtpass.getPassword());

        if (dbController.validateUser(selectedUser, password)) {
            isLoggedIn = true;
            currentUser = selectedUser;
            SwingUtilities.invokeLater(() -> {
            txtuserlabel.setText("Logged in as: " + currentUser);
         
            // Enable Time In and Time Out buttons
            btntimein.setEnabled(true);
            btntimeout.setEnabled(true);
            
            // disable log in and log out buttons
            btnlogout.setEnabled(true);
            });
            
            populateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid password");
            txtpass.setText("");
            btnlogin.setEnabled(true);
        }
    }//GEN-LAST:event_btnloginActionPerformed

    private void btntimeinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimeinActionPerformed
        // TODO add your handling code here:
        if (isLoggedIn) {
        if (dbController.setTimeIn(currentUser)) {
            JOptionPane.showMessageDialog(this, "Time In Recorded Successfully");
            populateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Record Time In");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please log in first");
    }
    }//GEN-LAST:event_btntimeinActionPerformed

    private void btntimeoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntimeoutActionPerformed
        // TODO add your handling code here:
        if (isLoggedIn) {
        if (dbController.setTimeOut(currentUser)) {
            JOptionPane.showMessageDialog(this, "Time Out Recorded Successfully");
            populateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Record Time Out");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Please log in first");
    }
    }//GEN-LAST:event_btntimeoutActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlogin;
    private javax.swing.JButton btnlogout;
    private javax.swing.JButton btntimein;
    private javax.swing.JButton btntimeout;
    private javax.swing.JComboBox<String> dropbox;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel runningtime;
    private javax.swing.JLabel txtdate;
    private javax.swing.JPasswordField txtpass;
    private javax.swing.JLabel txtuserlabel;
    // End of variables declaration//GEN-END:variables
    
@Override
    public void run() {
        while (true) {
            Calendar cal = Calendar.getInstance();
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
            seconds = cal.get(Calendar.SECOND);

            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm:ss aa");
            String timeString = sdf12.format(cal.getTime());

            // Update the JLabel in the Event Dispatch Thread
            SwingUtilities.invokeLater(() -> {
                runningtime.setText(timeString);
            });
            
            try {
                Thread.sleep(1000); // Update every second
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void Date() {
        GregorianCalendar gc = new GregorianCalendar();
            day = gc.get(Calendar.DAY_OF_MONTH);
            month = gc.get(Calendar.MONDAY);
            year = gc.get(Calendar.YEAR);
            
            SimpleDateFormat sdfd = new SimpleDateFormat("yyyy/MM/dd");
            String datenow = sdfd.format(new Date());
            txtdate.setText(datenow);
            
    }
   
    
}