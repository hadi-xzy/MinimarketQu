/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.HeadlessException;
import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author hadip
 */
public class Form_Penjualan extends javax.swing.JFrame {
    
    //metode untuk membuat Format mata Uang
    NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in","ID"));
    
    //DefaultTableModel modelisi untuk judul kolom tabel isi keranjang
    DefaultTableModel modelisi = new DefaultTableModel();
    
    //untuk membuat no faktur automatis
    private void auto_no_faktur(){
        try{
            //seleksi tabel penjualan berdasarkan no_faktur diurutkan dari besar
            String sql = "SELECT * FROM penjualan ORDER BY no_faktur DESC";
            java.sql.Connection con = (Connection)koneksi.configDB();//panggil koneksi
            java.sql.Statement stm = con.createStatement();//panggil statement
            java.sql.ResultSet res = stm.executeQuery(sql);//panggil query
            
            if (res.next()){
                //"no_faktur" merupakan atribut pada tabel penjualan
                //substring(3) sebelum angka nantinya ada 3 karakter di depan
                String id = res.getString("no_faktur").substring(3);
                String AN =""+(Integer.parseInt(id)+1);
                String Nol ="";
                if (AN.length()==1)
                {Nol="0000";}
                else if (AN.length()==2)
                {Nol="000";}
                else if (AN.length()==3)
                {Nol="00";}
                else if (AN.length()==4)
                {Nol="0";}
                else if (AN.length()==5)
                {Nol="";}
                    txtnofaktur.setText("NFS"+Nol+AN);
                }else{
                    txtnofaktur.setText("NFS00001");
                }
        }catch(SQLException e){
            System.out.println("Error :"+e.getMessage());
        }
    }
    
    //deklarasi metod tampilkan barang
    private void tampilkan_barang(){
        DefaultTableModel modelbarang = new DefaultTableModel();
        modelbarang.addColumn("Kode");
        modelbarang.addColumn("Nama Barang");
        modelbarang.addColumn("Harga");
        modelbarang.addColumn("Stok");
        
        //variable cari untuk proses cari data
        String caribrg = txtcari.getText();
        try{
            String sql = "SELECT * FROM barang where kd_barang LIKE '%"+caribrg+"%'"+
                    "or nama_barang LIKE '%"+caribrg+"%'";
            java.sql.Connection con=(Connection)koneksi.configDB();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()){
                //addrow menambahkan sebuah baris untuk
                //ditempatkan di posisi terakhir dari model
                modelbarang.addRow(new Object[]{
                    res.getString(1),res.getString(2),
                    res.getString(4),res.getString(5)
                });
                    tabelbarang.setModel(modelbarang);
                    //untuk mengatur lebar kolom
                    TableColumn column;
                    tabelbarang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                    column = tabelbarang.getColumnModel().getColumn(0);
                    column.setPreferredWidth(75);
                    column = tabelbarang.getColumnModel().getColumn(1);
                    column.setPreferredWidth(170);
                    column = tabelbarang.getColumnModel().getColumn(2);
                    column.setPreferredWidth(70);
                    column = tabelbarang.getColumnModel().getColumn(3);
                    column.setPreferredWidth(45);
            }
        }catch (SQLException e){
            System.out.println("Error :"+e.getMessage());
        }
    }
    private void judul_kolom_keranjang(){
        modelisi.addColumn("No");
        modelisi.addColumn("Stok Awal");
        modelisi.addColumn("Stok Akhir");
        modelisi.addColumn("Kode Barang");
        modelisi.addColumn("Nama Barang");
        modelisi.addColumn("Harga");
        modelisi.addColumn("Jumlah");
        modelisi.addColumn("Sub Total");
        
        tabelkeranjang.setModel(modelisi);
        //untuk mengatur lebar kolom
        
        TableColumn column;
            tabelkeranjang.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            column = tabelkeranjang.getColumnModel().getColumn(0);
            column.setPreferredWidth(50);
            column = tabelkeranjang.getColumnModel().getColumn(1);
            column.setPreferredWidth(70);
            column = tabelkeranjang.getColumnModel().getColumn(2);
            column.setPreferredWidth(70);
            column = tabelkeranjang.getColumnModel().getColumn(3);
            column.setPreferredWidth(90);
            column = tabelkeranjang.getColumnModel().getColumn(4);
            column.setPreferredWidth(190);
            column = tabelkeranjang.getColumnModel().getColumn(5);
            column.setPreferredWidth(90);
            column = tabelkeranjang.getColumnModel().getColumn(6);
            column.setPreferredWidth(60);
            column = tabelkeranjang.getColumnModel().getColumn(7);
            column.setPreferredWidth(94);
    }
    //deklarasi metod tanggal sesuai dengan di pc
    private void tanggal(){
        Date now = new Date();
        tgltransaksi.setDate(now);
    }
    
    //deklarasi metod Load Data pada Tabel Keranjang
    private void load_data(){
        DefaultTableModel model = (DefaultTableModel) tabelkeranjang.getModel();
        Object obj []= new Object [8];
        obj [1] = txtsaw.getText();
        obj [2] = txtsak.getText();
        obj [3] = txtkd.getText();
        obj [4] =txtnb.getText();
        obj [5] =txthrg.getText();
        obj [6] =txtjumlah.getText();
        obj [7] =txtsubtotal.getText();
        
        model.addRow(obj);
        //getrowcount() mengembalikan nilai int yg merupakan jumlah baris tabel
        int baris = model.getRowCount();
        for (int a = 0; a<baris; a ++){
            String no = String.valueOf(a+1);
            model.setValueAt(no + ".", a, 0);
        }
        //tinggi baris
        tabelkeranjang.setRowHeight(25);
    }
    
    //deklarasi metodkosongkan_1 digunakan untuk mengkosongkan form 
    //pada setiap objek yg ada
    private void kosongkan_1(){
        txtkd.setText("");
        txtsaw.setText("");
        txtsak.setText("");
        txtnb.setText("");
        txthrg.setText("");
        txtjumlah.setText("");
        txtsubtotal.setText("");
    }
    //deklarasi metod kosongkan_2 (sama seperti kosong 1)
    private void kosongkan_2(){
        txtnofaktur.setText("");
        txttotal.setText("");
        txtbayar.setText("");
        txtkembalian.setText("");
        txttampiltotal.setText("");
        auto_no_faktur();
    }
    
    //deklarasi metod total bayar untuk menjumlahkan seluruh transaksi
    //penjualan dengan perhitungan (jumlah x harga)
    
    private void total_biaya(){
        //getrowcount() mengembalikan nilai int yg merupakan jumlah baris
        //tabel
        
        int jumlahbaris=tabelkeranjang.getRowCount();
        int totalbiaya=0;
        int jumlahbarang, hargabarang;
        for (int i = 0; i<jumlahbaris; i++){
            hargabarang = Integer.parseInt(tabelkeranjang.getValueAt(i, 5).toString());
            jumlahbarang = Integer.parseInt(tabelkeranjang.getValueAt(i, 6).toString());
            totalbiaya = totalbiaya +(jumlahbarang * hargabarang);
        }
        txttotal.setText(nf.format(totalbiaya));
        txttampiltotal.setText("Rp. "+nf.format(totalbiaya));
    }
    
    //deklarasi metod tambah transaksi
    private void tambah_transaksi(){
        int jumlah,harga,total;
        //integer.valuof mengembalikan sebuah objek int
        jumlah = Integer.valueOf(txtjumlah.getText());
        harga = Integer.valueOf(txthrg.getText());
        total = jumlah * harga;
        txttotal.setText(nf.format(total));
        total_biaya();
        kosongkan_1();
        txtkd.requestFocus();
    }
    
    //deklarasi metod kosongkan keranjang
    private void kosongkan_keranjang(){
        //getmodel mengembalikan tablemodel sebagai data tabel
        DefaultTableModel model = (DefaultTableModel)tabelkeranjang.getModel();
            //getrowcount() mengembalikan nilai int yg merupakan jumlah baris tabel
            while (model.getRowCount()>0){
                //menghapus baris pada baris dari model
                model.removeRow(0);
            }
    }
    
    
    

    /**
     * Creates new form Form_Penjualan
     */
    public Form_Penjualan() {
        initComponents();
        //this.setExtendedState(Form_Penjualan.MAXIMIZED_HORIZ);
        //this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(Form_Penjualan.DO_NOTHING_ON_CLOSE);
        auto_no_faktur();
        tanggal();
        tampilkan_barang();
        judul_kolom_keranjang();
        txtnofaktur.setEnabled(false);
        tgltransaksi.setEnabled(false);
        //txtid.setEnabled(false);
        //txtuser.setEnabled(false);
        //txtacc.setEnabled(false);
        txtsaw.setEnabled(false);
        txtsak.setEnabled(false);
        txtnb.setEnabled(false);
        txthrg.setEnabled(false);
        txtsubtotal.setEnabled(false);
        txttotal.setEnabled(false);
        txtkembalian.setEnabled(false);
        txttampiltotal.setEnabled(false);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtnofaktur = new javax.swing.JTextField();
        tgltransaksi = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtkd = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtnb = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtsaw = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtsak = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txthrg = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtjumlah = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtsubtotal = new javax.swing.JTextField();
        btnnambah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelkeranjang = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelbarang = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txttampiltotal = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtbayar = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtkembalian = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btncetakS = new javax.swing.JButton();
        btnkeluar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("No Faktur");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, -1, -1));

        jLabel4.setText("Tanggal Transaksi");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 36, -1, -1));
        jPanel1.add(txtnofaktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 110, -1));
        jPanel1.add(tgltransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        jLabel2.setText("Username");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));
        jPanel1.add(txtuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 120, -1));

        jLabel7.setText("Id User");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, -1));

        jLabel8.setText("Akses");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, -1, -1));
        jPanel1.add(txtid, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 140, -1));
        jPanel1.add(txtacc, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 140, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 710, 80));

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Jl. Purwosari Pekanbaru Prov. Riau WA 0895393500007 - IG.@murah.banget - Website: https://murahbangetshop.com");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Transaksi Minimarket Bulanan Murah Banget :D");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/005-cashier-1.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, -1, 80));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 80));

        jPanel3.setBackground(new java.awt.Color(102, 204, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setText("Kode Barang");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtkd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtkdKeyReleased(evt);
            }
        });
        jPanel3.add(txtkd, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 120, -1));

        jLabel10.setText("Nama Barang");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, -1, -1));
        jPanel3.add(txtnb, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 160, -1));

        jLabel11.setText("Stok Awal");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, -1));
        jPanel3.add(txtsaw, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 50, -1));

        jLabel12.setText("Stok Akhir");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, -1, -1));
        jPanel3.add(txtsak, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 50, -1));

        jLabel13.setText("Harga Barang");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));
        jPanel3.add(txthrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 100, -1));

        jLabel14.setText("Jumlah");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, -1));

        txtjumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtjumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtjumlahKeyTyped(evt);
            }
        });
        jPanel3.add(txtjumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 70, -1));

        jLabel15.setText("Sub Total");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));
        jPanel3.add(txtsubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 150, -1));

        btnnambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconplus-sum-24.png"))); // NOI18N
        btnnambah.setText("Nambah Item");
        btnnambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnambahActionPerformed(evt);
            }
        });
        jPanel3.add(btnnambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, -1));

        btnhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Editing-Delete-icon 32x32.png"))); // NOI18N
        btnhapus.setText("Hapus Item");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });
        jPanel3.add(btnhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 150, 30));

        tabelkeranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelkeranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkeranjangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelkeranjang);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 100, 700, 320));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 710, 430));

        jPanel4.setBackground(new java.awt.Color(102, 255, 102));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setText("Cari Barang");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
        });
        jPanel4.add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 340, 30));

        tabelbarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelbarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelbarangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelbarang);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 370, 390));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 100, 430, 520));

        jPanel5.setBackground(new java.awt.Color(153, 153, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel17.setText("TOTAL");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        txttampiltotal.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txttampiltotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txttampiltotal.setText("Rp. 0");
        txttampiltotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttampiltotalActionPerformed(evt);
            }
        });
        jPanel5.add(txttampiltotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 300, 60));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/002-groceries.png"))); // NOI18N
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jLabel19.setText("Kembalian");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 80, -1, -1));

        txttotal.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txttotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txttotal.setText("0");
        jPanel5.add(txttotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 110, -1));

        jLabel20.setText("Total Belanja");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 20, -1, -1));

        txtbayar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtbayar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtbayar.setText("0");
        txtbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbayarActionPerformed(evt);
            }
        });
        jPanel5.add(txtbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, 110, -1));

        jLabel21.setText("Bayar");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, -1, -1));

        txtkembalian.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        txtkembalian.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtkembalian.setText("0");
        jPanel5.add(txtkembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 110, -1));

        jLabel22.setText("Tekan Enter");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 50, -1, -1));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/chart-search-icon 32x32.png"))); // NOI18N
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 30, 30, 40));

        btncetakS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Printer-icon.png"))); // NOI18N
        btncetakS.setText("Cetak Struk & Simpan");
        btncetakS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakSActionPerformed(evt);
            }
        });
        jPanel5.add(btncetakS, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, 190, 40));

        btnkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Actions-window-close-icon 32x32.png"))); // NOI18N
        btnkeluar.setText("Keluar");
        btnkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnkeluarActionPerformed(evt);
            }
        });
        jPanel5.add(btnkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1035, 61, 120, 40));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 1170, 110));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txttampiltotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttampiltotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttampiltotalActionPerformed

    private void txtkdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkdKeyReleased
        try{
            java.sql.Connection con =(Connection)koneksi.configDB();
            String sql = "SELECT * FROM barang WHERE kd_barang=?";
            java.sql.PreparedStatement pst=con.prepareStatement(sql);
            pst.setString(1, txtkd.getText());
            java.sql.ResultSet res = pst.executeQuery();
            if(res.next()){
                String namabrg = res.getString("nama_barang");
                txtnb.setText(namabrg);
                String stokawal = res.getString("stok");
                txtsaw.setText(stokawal);
                String hargabrg = res.getString("harga");
                txthrg.setText(hargabrg);
            }
        }catch (SQLException e){
            System.out.println("Error :"+e.getMessage());
        }
    }//GEN-LAST:event_txtkdKeyReleased

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        tampilkan_barang();
    }//GEN-LAST:event_txtcariKeyReleased

    private void tabelbarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelbarangMouseClicked
        if (txtid.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Pilih User or Kasir");
            txtid.requestFocus();
        }else{
            int baris=tabelbarang.rowAtPoint(evt.getPoint());
            String kode = tabelbarang.getValueAt(baris, 0).toString();
            txtkd.setText(kode);
            String nama = tabelbarang.getValueAt(baris, 1).toString();
            txtnb.setText(nama);
            String harga = tabelbarang.getValueAt(baris, 2).toString();
            txthrg.setText(harga);
            String stok = tabelbarang.getValueAt(baris, 3).toString();
            txtsaw.setText(stok);
        }
    }//GEN-LAST:event_tabelbarangMouseClicked

    private void txtjumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahKeyReleased
        if (txtjumlah.getText().equals("")||txtjumlah.getText().equals(0)){
            txtsubtotal.setText("0");
        }else{
            int jumlah,harga,subtotal;
            jumlah = Integer.valueOf(txtjumlah.getText());
            harga = Integer.valueOf(txthrg.getText());
            subtotal = jumlah * harga;
            txtsubtotal.setText(nf.format(subtotal));
        }
        int jumlahbeli,stokawal,stokakhir;
        jumlahbeli = Integer.valueOf(txtjumlah.getText());
        stokawal = Integer.valueOf(txtsaw.getText());
        
        if (jumlahbeli>stokawal){
            JOptionPane.showMessageDialog(null, "Stok Barang Tidak Mencukupi...!");
            btnnambah.setEnabled(false);
            txtsak.setText("");
            txtjumlah.requestFocus();
        }else{
            stokakhir=(stokawal-jumlahbeli);
            txtsak.setText(nf.format(stokakhir));
            btnnambah.setEnabled(true);
        }
    }//GEN-LAST:event_txtjumlahKeyReleased

    private void txtjumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtjumlahKeyTyped
        //agar yg diinput hanya karakter angka
            char enter = evt.getKeyChar();
            if(!(Character.isDigit(enter))){
                evt.consume();
                JOptionPane.showMessageDialog(null, "Masukan Angka 0 s/d 9");
                txtjumlah.setText("0");
                txtsak.setText("0");
            }
    }//GEN-LAST:event_txtjumlahKeyTyped

    private void btnnambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnambahActionPerformed
        if (txtjumlah.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Masukan Jumlah Barang");
            txtid.requestFocus();
        }else{
            load_data();
            tambah_transaksi();
            kosongkan_1();
        }
        
        try{
            int baris2 = tabelkeranjang.getRowCount();
            for (int i = 0; i<baris2; i++){
                String sqlup="UPDATE barang SET stok='"+tabelkeranjang.getValueAt(i, 2).toString()
                        +"'WHERE kd_barang='"+tabelkeranjang.getValueAt(i, 3).toString()+"'";
                java.sql.Connection con =(Connection)koneksi.configDB();
                java.sql.PreparedStatement pstm =con.prepareStatement(sqlup);
                pstm.execute();
                pstm.close();
            }
            JOptionPane.showMessageDialog(null, "Data Masuk Keranjang");
            tampilkan_barang();
        }catch (HeadlessException | SQLException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal...!");
        }
    }//GEN-LAST:event_btnnambahActionPerformed

    private void tabelkeranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelkeranjangMouseClicked
        int baris = tabelkeranjang.rowAtPoint(evt.getPoint());
        String stokawal = tabelkeranjang.getValueAt(baris, 1).toString();
        txtsaw.setText(stokawal);
        String stokakhir = tabelkeranjang.getValueAt(baris, 2).toString();
        txtsak.setText(stokakhir);
        String kode = tabelkeranjang.getValueAt(baris, 3).toString();
        txtkd.setText(kode);
        String nama = tabelkeranjang.getValueAt(baris, 4).toString();
        txtnb.setText(nama);
        String harga = tabelkeranjang.getValueAt(baris, 5).toString();
        txthrg.setText(harga);
        String jumlah = tabelkeranjang.getValueAt(baris, 6).toString();
        txtjumlah.setText(jumlah);
        String subtotal = tabelkeranjang.getValueAt(baris, 7).toString();
        txtsubtotal.setText(subtotal);
    }//GEN-LAST:event_tabelkeranjangMouseClicked

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        DefaultTableModel model = (DefaultTableModel)tabelkeranjang.getModel();
        
        try{
            int baris2= tabelkeranjang.getRowCount();
            for (int i = 0; i < baris2; i++){
                String sqlup = "UPDATE barang SET stok='"+tabelkeranjang.getValueAt(i, 1).toString()
                        +"'WHERE kd_barang='"+tabelkeranjang.getValueAt(i, 3).toString();
                java.sql.Connection con =(Connection)koneksi.configDB();
                java.sql.PreparedStatement pstm=con.prepareStatement(sqlup);
                pstm.execute();
                pstm.close();
            }
            JOptionPane.showMessageDialog(null, "Transaksi Dibatalkan");
            tampilkan_barang();
        }catch(HeadlessException | SQLException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal...!");
        }
        int baris=tabelkeranjang.getSelectedRow();
        model.removeRow(baris);
        
        tabelkeranjang.setRowHeight(25);
        total_biaya();
        txtbayar.setText("0");
        txtkembalian.setText("0");
        kosongkan_1();
    }//GEN-LAST:event_btnhapusActionPerformed

    private void txtbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbayarActionPerformed
        int total,bayar,kembalian;
        total = Integer.valueOf(txttotal.getText().replace(".", ""));
        bayar = Integer.valueOf(txtbayar.getText().replace(".", ""));
        
        if (total > bayar){
            JOptionPane.showMessageDialog(null, "Uang tidak cukup untuk melakukan Pembayaran...");
        }else{
            kembalian = bayar - total;
            //txtkembalian.setText(String.valueOf(kembalian));
            txtkembalian.setText(nf.format(kembalian));
            if(kembalian ==0){
                //txtkembalian di manipulasi agar tidak error saat dicetak
                //kalau kembalian sama dengan =0
                txtkembalian.setText("0.1");
                JOptionPane.showMessageDialog(null, "Terimakasih sudah membayar");
            }
        }
    }//GEN-LAST:event_txtbayarActionPerformed

    private void btncetakSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakSActionPerformed
        int total,bayar;
        total = Integer.valueOf(txttotal.getText().replace(".", ""));
        bayar = Integer.valueOf(txtbayar.getText().replace(".", ""));
        
        if (bayar<total){
            JOptionPane.showMessageDialog(null, "Lakukan Pembayaran");
        }else{
            //untuk format tanggal pada JDateChooser
            String tampilan ="yyyy-MM-dd";
            SimpleDateFormat fm= new SimpleDateFormat(tampilan);
            String tanggal=String.valueOf(fm.format(tgltransaksi.getDate()));
            try{
                int baris = tabelkeranjang.getRowCount();
                for (int i = 0;i<baris;i++){
                    String sql="INSERT INTO penjualan VALUES ( NULL,'"
                            +txtnofaktur.getText()+"','"
                            +tanggal+"','"
                            +tabelkeranjang.getValueAt(i, 3)+"','"
                            +txtid.getText()+"','"
                            +tabelkeranjang.getValueAt(i, 6)+"','"
                            +tabelkeranjang.getValueAt(i, 7).toString().replace(".", "")+"')";
                    java.sql.Connection con=(Connection)koneksi.configDB();
                    java.sql.PreparedStatement pstm = con.prepareStatement(sql);
                    pstm.execute();
                    pstm.close();
                }
                JOptionPane.showMessageDialog(null, "Data Berhasil di Simpan...!");
            }catch(HeadlessException | SQLException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan...!");
        }
            JasperReport jr;
            JasperPrint jp;
            JasperDesign jd;
            try{
                java.sql.Connection conn =(Connection)koneksi.configDB();
                HashMap iniparameter =new HashMap();
                iniparameter.put("no_faktur",txtnofaktur.getText());
                iniparameter.put("totalbelanja",txttotal.getText());
                iniparameter.put("bayar",txtbayar.getText());
                iniparameter.put("kembalian",txtkembalian.getText());
                
                File report =new File("src/Laporan/struk.jrxml");
                jd=JRXmlLoader.load(report);
                jr=JasperCompileManager.compileReport(jd);
                jp=JasperFillManager.fillReport(jr,iniparameter,conn);
                JasperViewer.viewReport(jp,false);
            }catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
            kosongkan_keranjang();
            kosongkan_2();
            tampilkan_barang();
            txttotal.setText("0");
            txtbayar.setText("0");
            txtkembalian.setText("0");
            txttampiltotal.setText("Rp. 0");
        }
    }//GEN-LAST:event_btncetakSActionPerformed

    private void btnkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnkeluarActionPerformed
     int pilihan =JOptionPane.showConfirmDialog(null,
                "Apakah Anda ingin keluar dari program ?",
                "Title Keluar:",JOptionPane.YES_NO_OPTION);
        if (pilihan==JOptionPane.YES_OPTION)
            this.dispose();
        else
            txtkd.requestFocus();
    }//GEN-LAST:event_btnkeluarActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncetakS;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnkeluar;
    private javax.swing.JButton btnnambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelbarang;
    private javax.swing.JTable tabelkeranjang;
    private com.toedter.calendar.JDateChooser tgltransaksi;
    public static final javax.swing.JTextField txtacc = new javax.swing.JTextField();
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txthrg;
    public static final javax.swing.JTextField txtid = new javax.swing.JTextField();
    private javax.swing.JTextField txtjumlah;
    private javax.swing.JTextField txtkd;
    private javax.swing.JTextField txtkembalian;
    private javax.swing.JTextField txtnb;
    private javax.swing.JTextField txtnofaktur;
    private javax.swing.JTextField txtsak;
    private javax.swing.JTextField txtsaw;
    private javax.swing.JTextField txtsubtotal;
    private javax.swing.JTextField txttampiltotal;
    private javax.swing.JTextField txttotal;
    public static final javax.swing.JTextField txtuser = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
}
