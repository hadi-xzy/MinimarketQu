/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.sql.Connection;
import java.awt.HeadlessException;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


import java.io.File;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FormCetaklaporanperperiode extends javax.swing.JFrame {

    /**
     * Creates new form FormCetaklaporanperperiode
     */
    public FormCetaklaporanperperiode() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TanggalTahun = new javax.swing.JLabel();
        btncetakperbulan = new javax.swing.JButton();
        tglhari = new com.toedter.calendar.JDateChooser();
        Tanggal2 = new com.toedter.calendar.JDateChooser();
        btncetakpertahun = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        Tanggal1 = new com.toedter.calendar.JDateChooser();
        btncetakperhari = new javax.swing.JButton();
        tgltahun = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnkeluar = new javax.swing.JButton();
        kembalikeMainMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 153, 153));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Sampai tanggal");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 130, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Tanggal");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 90, -1));

        TanggalTahun.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TanggalTahun.setText("Tahun");
        jPanel2.add(TanggalTahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 70, -1));

        btncetakperbulan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Distributor-report-icon 32x32.png"))); // NOI18N
        btncetakperbulan.setText("Cetak perbulan");
        btncetakperbulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakperbulanActionPerformed(evt);
            }
        });
        jPanel2.add(btncetakperbulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 490, -1));
        jPanel2.add(tglhari, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 36, -1, 30));
        jPanel2.add(Tanggal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, -1, -1));

        btncetakpertahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Distributor-report-icon 32x32.png"))); // NOI18N
        btncetakpertahun.setText("Cetak pertahun");
        btncetakpertahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakpertahunActionPerformed(evt);
            }
        });
        jPanel2.add(btncetakpertahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, -1, -1));
        jPanel2.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 540, 10));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 88, 540, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Dari Tanggal");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 130, -1));
        jPanel2.add(Tanggal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, -1, -1));

        btncetakperhari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Distributor-report-icon 32x32.png"))); // NOI18N
        btncetakperhari.setText("Cetak perhari");
        btncetakperhari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakperhariActionPerformed(evt);
            }
        });
        jPanel2.add(btncetakperhari, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, -1, -1));
        jPanel2.add(tgltahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 540, 300));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Cetak Laporan Perperiode");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 330, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/003-newspaper.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 40, 50));

        btnkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Actions-window-close-icon 24x24.png"))); // NOI18N
        btnkeluar.setText("Keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });
        jPanel1.add(btnkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 380, 120, -1));

        kembalikeMainMenu.setText("Kembali ke Mainmenu");
        kembalikeMainMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembalikeMainMenuActionPerformed(evt);
            }
        });
        jPanel1.add(kembalikeMainMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-3, 0, 600, 430));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
       System.exit(0);
    }//GEN-LAST:event_btnkeluarActionPerformed

    private void btncetakperhariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakperhariActionPerformed
        JasperReport jr;
        JasperPrint jp;
        JasperDesign jd;
        try{

            //panggil class koneksi
            java.sql.Connection conn =(Connection)koneksi.configDB();
            HashMap parameter=new HashMap();
            parameter.put("perhari",tglhari.getDate());

            File report =new File("src/Laporan/LaporanPerhari.jrxml");
            jd=JRXmlLoader.load(report);
            jr=JasperCompileManager.compileReport(jd);
            jp=JasperFillManager.fillReport(jr,parameter,conn);
            JasperViewer.viewReport(jp,false);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_btncetakperhariActionPerformed

    private void btncetakpertahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakpertahunActionPerformed
        JasperReport jr;
        JasperPrint jp;
        JasperDesign jd;
        try{

            //panggil class koneksi
            java.sql.Connection conn =(Connection)koneksi.configDB();
            HashMap parameter=new HashMap();
            parameter.put("Pertahun",tgltahun.getDate());

            File report =new File("src/Laporan/LaporanPertahun.jrxml");
            jd=JRXmlLoader.load(report);
            jr=JasperCompileManager.compileReport(jd);
            jp=JasperFillManager.fillReport(jr,parameter,conn);
            JasperViewer.viewReport(jp,false);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_btncetakpertahunActionPerformed

    private void btncetakperbulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakperbulanActionPerformed
        JasperReport jr;
        JasperPrint jp;
        JasperDesign jd;
        try{

            //panggil class koneksi
            java.sql.Connection conn =(Connection)koneksi.configDB();
            HashMap parameter=new HashMap();
            parameter.put("Perbulan",Tanggal1.getDate());
            parameter.put("Pertahun",Tanggal2.getDate());

            File report =new File("src/Laporan/LaporanPerbulan.jrxml");
            jd=JRXmlLoader.load(report);
            jr=JasperCompileManager.compileReport(jd);
            jp=JasperFillManager.fillReport(jr,parameter,conn);
            JasperViewer.viewReport(jp,false);
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_btncetakperbulanActionPerformed

    private void kembalikeMainMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembalikeMainMenuActionPerformed
        new menuutama().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_kembalikeMainMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormCetaklaporanperperiode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCetaklaporanperperiode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCetaklaporanperperiode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCetaklaporanperperiode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormCetaklaporanperperiode().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Tanggal1;
    private com.toedter.calendar.JDateChooser Tanggal2;
    private javax.swing.JLabel TanggalTahun;
    private javax.swing.JButton btncetakperbulan;
    private javax.swing.JButton btncetakperhari;
    private javax.swing.JButton btncetakpertahun;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton kembalikeMainMenu;
    private com.toedter.calendar.JDateChooser tglhari;
    private com.toedter.calendar.JDateChooser tgltahun;
    // End of variables declaration//GEN-END:variables
}