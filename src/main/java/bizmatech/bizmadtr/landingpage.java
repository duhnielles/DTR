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
        //dropbox.setModel(model); // Set the model for the JComboBox
    }
        
private void populateTable() {
    try {
        List<UserEntry> entries = dbController.getUserEntries(currentUser);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Clear existing rows
    
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");

        for (UserEntry entry : entries) {
            String date = dateFormat.format(entry.getDate());
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

            if (entry.getTimeinPm() != null) {
                timeinPM = timeFormat.format(entry.getTimeinPm());
            }

            if (entry.getTimeoutPm() != null) {
                timeoutPM = timeFormat.format(entry.getTimeoutPm());
            }

            model.addRow(new Object[]{entry.getName(), entry.getLastname(), date, timeinAM, timeoutAM, timeinPM, timeoutPM});
        }
    } catch (Exception e) {
        e.printStackTrace();
        // Optionally show an error message to the user
        JOptionPane.showMessageDialog(this, "An error occurred while populating the table.");
    }
}

    
    
private void exportToPDF() {
    try {
        List<UserEntry> entries = dbController.getUserEntries(currentUser);
        System.out.println("Fetched entries for user: " + currentUser);
        if (entries.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data available to generate PDF for the selected user.");
            return;
        }

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Define table headers
        String[] headers = {"Name", "Last Name", "Date", "Time In (AM)", "Time Out (AM)", "Time In (PM)", "Time Out (PM)"};

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
            System.out.println("Processing entry for user: " + currentUser);
            System.out.println("Name: " + entry.getName());
            System.out.println("Last Name: " + entry.getLastname());
            System.out.println("Date: " + entry.getDate());
            System.out.println("Time In (AM): " + entry.getTimein());
            System.out.println("Time Out (AM): " + entry.getTimeout());
            System.out.println("Time In (PM): " + entry.getTimeinPm());
            System.out.println("Time Out (PM): " + entry.getTimeoutPm());

            yPosition -= rowHeight;
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);

            contentStream.showText(entry.getName() != null ? entry.getName() : "");
            contentStream.newLineAtOffset((tableWidth / headers.length), 0);
            contentStream.showText(entry.getLastname() != null ? entry.getLastname() : "");
            contentStream.newLineAtOffset((tableWidth / headers.length), 0);
            contentStream.showText(entry.getDate() != null ? dateFormat.format(entry.getDate()) : "");
            contentStream.newLineAtOffset((tableWidth / headers.length), 0);
            contentStream.showText(entry.getTimein() != null ? timeFormat.format(entry.getTimein()) : "");
            contentStream.newLineAtOffset((tableWidth / headers.length), 0);
            contentStream.showText(entry.getTimeout() != null ? timeFormat.format(entry.getTimeout()) : "");
            contentStream.newLineAtOffset((tableWidth / headers.length), 0);
            contentStream.showText(entry.getTimeinPm() != null ? timeFormat.format(entry.getTimeinPm()) : "");
            contentStream.newLineAtOffset((tableWidth / headers.length), 0);
            contentStream.showText(entry.getTimeoutPm() != null ? timeFormat.format(entry.getTimeoutPm()) : "");

            contentStream.endText();
        }

        contentStream.close();

        // Determine the Downloads folder path
        String userHome = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();
        String downloadsFolderPath;

        if (os.contains("win")) {
            downloadsFolderPath = userHome + "\\Downloads\\";
        } else {
            downloadsFolderPath = userHome + "/Downloads/";
        }

        // Ensure the directory exists
        File downloadsFolder = new File(downloadsFolderPath);
        if (!downloadsFolder.exists()) {
            downloadsFolder.mkdirs();
        }

        // Save the document in the Downloads folder
        File file = new File(downloadsFolderPath + "DTR_Report_" + currentUser + ".pdf");
        document.save(file);
        document.close();

        JOptionPane.showMessageDialog(null, "PDF Report generated successfully! File directory: Downloads.");
        System.out.println("PDF Report generated successfully for user: " + currentUser);

    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error generating PDF Report: " + e.getMessage());
        System.err.println("Error generating PDF Report for user: " + currentUser + ": " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage());
        System.err.println("Unexpected error for user: " + currentUser + ": " + e.getMessage());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        txtuserlabel = new javax.swing.JLabel();
        btnpdf = new javax.swing.JButton();
        runningtime = new javax.swing.JLabel();
        txtdate = new javax.swing.JLabel();
        btnlogout = new javax.swing.JButton();
        btnlogin = new javax.swing.JButton();
        txtid = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btntimein = new javax.swing.JButton();
        btntimeout = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1685, 977));
        setMinimumSize(new java.awt.Dimension(1685, 977));
        setName("BIZMATECH"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1685, 977));
        setResizable(false);
        setSize(new java.awt.Dimension(1685, 977));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setBackground(new java.awt.Color(237, 192, 87));
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, 1191, 560));

        txtuserlabel.setFont(new java.awt.Font("Segoe UI", 3, 30)); // NOI18N
        txtuserlabel.setForeground(java.awt.Color.orange);
        getContentPane().add(txtuserlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 321, 120));

        btnpdf.setFont(new java.awt.Font("Eras Demi ITC", 1, 24)); // NOI18N
        btnpdf.setText("View Printable PDF");
        btnpdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnpdfActionPerformed(evt);
            }
        });
        getContentPane().add(btnpdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, -1, -1));

        runningtime.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        getContentPane().add(runningtime, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 640, 184, 49));

        txtdate.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        getContentPane().add(txtdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 560, 184, 49));

        btnlogout.setFont(new java.awt.Font("Eras Light ITC", 0, 24)); // NOI18N
        btnlogout.setText("Log Out");
        btnlogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlogoutActionPerformed(evt);
            }
        });
        getContentPane().add(btnlogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, -1, -1));

        btnlogin.setFont(new java.awt.Font("Eras Light ITC", 0, 24)); // NOI18N
        btnlogin.setText("Log In");
        btnlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });
        getContentPane().add(btnlogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, -1, -1));

        txtid.setFont(new java.awt.Font("Dubai Light", 0, 24)); // NOI18N
        getContentPane().add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 341, 61));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setText("User:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Date:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Time:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 650, -1, -1));

        btntimein.setFont(new java.awt.Font("Eras Light ITC", 0, 24)); // NOI18N
        btntimein.setText("Time In");
        btntimein.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimeinActionPerformed(evt);
            }
        });
        getContentPane().add(btntimein, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 720, -1, -1));

        btntimeout.setFont(new java.awt.Font("Eras Light ITC", 0, 24)); // NOI18N
        btntimeout.setText("Time Out");
        btntimeout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntimeoutActionPerformed(evt);
            }
        });
        getContentPane().add(btntimeout, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 720, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bizmatech/bizmadtr/backlogo1.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-200, -200, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bizmatech/bizmadtr/BACKGROUND 1.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 1000));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnloginActionPerformed
        String userId = txtid.getText();

    if (dbController.validateUser(userId)) {
        isLoggedIn = true;
        currentUser = userId;
        String fullName = dbController.getUserFullName(userId); 

        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Successfully logged in!");
            if (fullName != null) {
                txtuserlabel.setText("<html>Logged in as:<br>" + fullName + "</html>");
            } else {
                txtuserlabel.setText("<html>Logged in as:<br>" + currentUser + "</html>"); 
            }

            // Enable Time In and Time Out buttons
            btntimein.setEnabled(true);
            btntimeout.setEnabled(true);

            // Disable Log In and enable Log Out buttons
            btnlogin.setEnabled(false);
            btnlogout.setEnabled(true);
        });

        populateTable();
    } else {
        JOptionPane.showMessageDialog(this, "Invalid user ID");
        txtid.setText(""); 
        btnlogin.setEnabled(true);
    }
    }//GEN-LAST:event_btnloginActionPerformed

    private void btnlogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlogoutActionPerformed
        // TODO add your handling code here:
        isLoggedIn = false;
        currentUser = null;

        btnlogin.setEnabled(true);
        btnlogout.setEnabled(false);
        btntimein.setEnabled(false);
        btntimeout.setEnabled(false);
        //dropbox.setEnabled(true);
        txtid.setEnabled(true);
        txtid.setText(""); // Clear password field

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

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        register register = new register();
        register.setVisible(true);
        register.setLocationRelativeTo(null);
        dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnlogin;
    private javax.swing.JButton btnlogout;
    private javax.swing.JButton btnpdf;
    private javax.swing.JButton btntimein;
    private javax.swing.JButton btntimeout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel runningtime;
    private javax.swing.JLabel txtdate;
    private javax.swing.JTextField txtid;
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
