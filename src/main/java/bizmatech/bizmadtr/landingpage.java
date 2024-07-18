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
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
    
    
     private void exportToPDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Define table headers
            String[] headers = {"Name", "Last Name", "Date", "Time In (AM)", "Time Out (AM)", "Time In (PM)", "Time Out (PM)"};

            // Define table data
            List<UserEntry> entries = dbController.getUserEntries(currentUser);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 5;

            // Draw headers
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yStart);
            for (String header : headers) {
                contentStream.showText(header);
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
            }
            contentStream.endText();

            // Draw rows
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            for (UserEntry entry : entries) {
                yPosition -= rowHeight;
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(entry.getname());
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(entry.getLastname());
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(dateFormat.format(entry.getdate()));
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(timeFormat.format(entry.getTimein()));
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(timeFormat.format(entry.getTimeout()));
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(timeFormat.format(entry.getTimein_Pm()));
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.showText(timeFormat.format(entry.getTimeout_Pm()));
                contentStream.newLineAtOffset((tableWidth / headers.length), 0);
                contentStream.endText();
            }

            contentStream.close();
            document.save(new File("DTR_Report.pdf"));
            document.close();

            JOptionPane.showMessageDialog(null, "PDF Report generated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error generating PDF Report: " + e.getMessage());
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
        btnpdf = new javax.swing.JButton();
        runningtime = new javax.swing.JLabel();
        btnlogout = new javax.swing.JButton();
        dropbox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtdate = new javax.swing.JLabel();
        txtuserlabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("BIZMATECH"); // NOI18N
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));

        btnlogin.setFont(new java.awt.Font("Segoe UI Historic", 0, 24)); // NOI18N
        btnlogin.setText("Log In");
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("User:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel2.setText("Password:");

        txtpass.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Time:");

        btntimein.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btntimein.setText("Time In");
        btntimein.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimeinActionPerformed(evt);
            }
        });

        btntimeout.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btntimeout.setText("Time Out");
        btntimeout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimeoutActionPerformed(evt);
            }
        });

        btnpdf.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnpdf.setText("View Printable PDF");
        btnpdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpdfActionPerformed(evt);
            }
        });

        runningtime.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        btnlogout.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnlogout.setText("Log Out");
        btnlogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlogoutActionPerformed(evt);
            }
        });

        dropbox.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        dropbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        dropbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropboxActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Date:");

        txtdate.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        txtuserlabel.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        txtuserlabel.setForeground(new java.awt.Color(51, 153, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnlogin)
                        .addGap(30, 30, 30)
                        .addComponent(btnlogout)
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dropbox, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(btntimein)
                                    .addGap(32, 32, 32)
                                    .addComponent(btntimeout))
                                .addComponent(txtuserlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnpdf)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(runningtime, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(80, 80, 80))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dropbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnlogout)
                    .addComponent(btnlogin))
                .addGap(18, 18, 18)
                .addComponent(btnpdf)
                .addGap(45, 45, 45)
                .addComponent(txtuserlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel4)
                        .addGap(43, 43, 43)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtdate, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(runningtime, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btntimein)
                    .addComponent(btntimeout))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 70)); // NOI18N
        jLabel5.setText("BIZMATECH");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel6.setText("Daily Time Record");

        jPanel3.setBackground(new java.awt.Color(255, 153, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 771, Short.MAX_VALUE)
        );

        jTable1.setBackground(new java.awt.Color(255, 204, 153));
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(607, 607, 607)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(660, 660, 660)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
        exportToPDF();
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

    private void btnlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlogoutActionPerformed
        // TODO add your handling code here:
        isLoggedIn = false;
        currentUser = null;

        btnlogin.setEnabled(true);
        btnlogout.setEnabled(false);
        btntimein.setEnabled(false);
        btntimeout.setEnabled(false);
        dropbox.setEnabled(true);
        txtpass.setEnabled(true);
        txtpass.setText(""); // Clear password field

        txtuserlabel.setText(""); // Clear user label

        JOptionPane.showMessageDialog(this, "Logged out successfully!");

        // Clear table
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_btnlogoutActionPerformed

    private void btnpdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnpdfActionPerformed
        // TODO add your handling code here:
        exportToPDF();
        
    }//GEN-LAST:event_btnpdfActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlogin;
    private javax.swing.JButton btnlogout;
    private javax.swing.JButton btnpdf;
    private javax.swing.JButton btntimein;
    private javax.swing.JButton btntimeout;
    private javax.swing.JComboBox<String> dropbox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");
            runningtime.setText(timeFormat.format(cal.getTime()));
            try {
                Thread.sleep(1000); // Update every second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void Date() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        txtdate.setText(dateFormat.format(cal.getTime()));
    }
}
