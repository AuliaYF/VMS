/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms.views;

import com.auliayf.vms.models.TenantListModel;
import com.auliayf.vms.models.TenantModel;
import com.auliayf.vms.models.VisitorListModel;
import com.auliayf.vms.models.VisitorModel;
import com.auliayf.vms.utils.Session;
import com.auliayf.vms.utils.Utils;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author uoy
 */
public class FormView extends javax.swing.JFrame {

    private String mMode;
    private int mIdVisitor;
    private VisitorModel mVisitorModel;
    private String mFotoMasuk = "";
    private String mFotoKeluar = "";

    private Webcam mWebcam = null;

    /**
     * Creates new form FormView
     */
    public FormView() {
        initComponents();

        Utils.centerForm(FormView.this);

        RestTemplate restTemplate = new RestTemplate();
        TenantListModel tenantListModel = restTemplate.getForObject(Utils.baseUrl("?par[mode]=tenantList"), TenantListModel.class);
        for (TenantModel tenantModel : tenantListModel.getTenants()) {
            fieldTenant.addItem(tenantModel);
        }
        fieldTenant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox c = (JComboBox) ae.getSource();
                TenantModel tenantModel = (TenantModel) c.getSelectedItem();
                fieldLantai.setText(tenantModel.getLantaiTenant());
                fieldUnit.setText(tenantModel.getUnitTenant());
            }
        });

        if (tenantListModel.getTenants().length > 0) {
            fieldTenant.setSelectedIndex(0);
        }

        RestTemplate restTemplate2 = new RestTemplate();
        VisitorListModel visitorListModel = restTemplate2.getForObject(Utils.baseUrl("?par[mode]=nomorList"), VisitorListModel.class);
        for (VisitorModel visitorModel : visitorListModel.getVisitors()) {
            fieldNomor1.addItem(visitorModel);
        }
        fieldNomor1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox c = (JComboBox) ae.getSource();
//                TenantModel tenantModel = (TenantModel) c.getSelectedItem();
            }
        });

        AutoCompleteDecorator.decorate(fieldNomor1);

        if (Session.getSession() != null) {
            labelNama.setText(Session.getSession().getcNama());
            labelDate.setText(Utils.getCurrentDate());
        } else {
            dispose();
        }
    }

    public FormView(VisitorModel visitorModel) {
        this.mVisitorModel = visitorModel;

        initComponents();

        RestTemplate restTemplate = new RestTemplate();
        TenantListModel tenantListModel = restTemplate.getForObject(Utils.baseUrl("?par[mode]=tenantList"), TenantListModel.class);
        for (TenantModel tenantModel : tenantListModel.getTenants()) {
            fieldTenant.addItem(tenantModel);
        }
        fieldTenant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox c = (JComboBox) ae.getSource();
                TenantModel tenantModel = (TenantModel) c.getSelectedItem();
                fieldLantai.setText(tenantModel.getLantaiTenant());
                fieldUnit.setText(tenantModel.getUnitTenant());
            }
        });

        RestTemplate restTemplate2 = new RestTemplate();
        VisitorListModel visitorListModel = restTemplate2.getForObject(Utils.baseUrl("?par[mode]=nomorList"), VisitorListModel.class);
        for (VisitorModel visitorModel2 : visitorListModel.getVisitors()) {
            fieldNomor1.addItem(visitorModel);
        }
        fieldNomor1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JComboBox c = (JComboBox) ae.getSource();
                final VisitorModel visitorModel2 = (VisitorModel) c.getSelectedItem();

                displaySerial.setText(visitorModel2.getserialVisitor());
                displayNama.setText(visitorModel2.getnamaVisitor());
                displayKeperluan.setText(visitorModel2.getkeperluanVisitor());
                displayTenant.setText(visitorModel2.getnamaTenant());
                displayLantai.setText(visitorModel2.getnamaLantai());
                displayUnit.setText(visitorModel2.getnamaUnit());

                if (visitorModel2.getgenderVisitor().equals("p")) {
                    displayGender.setText("Female");
                } else {
                    displayGender.setText("Male");
                }
            }
        });

        AutoCompleteDecorator.decorate(fieldNomor1);
        if (visitorListModel.getVisitors().length > 0) {
            fieldNomor1.setSelectedIndex(0);
        }

        Utils.centerForm(FormView.this);

        if (Session.getSession() != null) {
            labelNama.setText(Session.getSession().getcNama());
            labelDate.setText(Utils.getCurrentDate());

            fieldNomor.setText(visitorModel.getnomorVisitor());
            fieldNomor1.setSelectedItem(visitorModel);

            fieldSerial.setText(visitorModel.getserialVisitor());
            displaySerial.setText(visitorModel.getserialVisitor());

            fieldNama.setText(visitorModel.getnamaVisitor());
            displayNama.setText(visitorModel.getnamaVisitor());

            fieldKeperluan.setText(visitorModel.getkeperluanVisitor());
            displayKeperluan.setText(visitorModel.getkeperluanVisitor());

            displayTenant.setText(visitorModel.getnamaTenant());

            fieldLantai.setText(visitorModel.getnamaLantai());
            displayLantai.setText(visitorModel.getnamaLantai());

            fieldUnit.setText(visitorModel.getnamaUnit());
            displayUnit.setText(visitorModel.getnamaUnit());

            if (visitorModel.getgenderVisitor().equals("p")) {
                fieldFemale.setSelected(true);
                fieldMale.setSelected(false);
                displayGender.setText("Female");
            } else {
                fieldFemale.setSelected(false);
                fieldMale.setSelected(true);
                displayGender.setText("Male");
            }

            labelMasuk.setText(visitorModel.gettanggalMasuk());
            labelKeluar.setText(visitorModel.gettanggalKeluar().equals("0000-00-00 00:00:00") ? "" : visitorModel.gettanggalKeluar());

            if (!visitorModel.gettanggalMasuk().equals("0000-00-00 00:00:00")) {
                jPanel4.remove(buttonSave);
                jPanel4.remove(jButton1);
                fieldNomor.setEditable(false);
                fieldSerial.setEditable(false);
                fieldNama.setEditable(false);
                fieldKeperluan.setEditable(false);
                fieldTenant.setEnabled(false);
                fieldMale.setEnabled(false);
                fieldFemale.setEnabled(false);
            }

            if (!visitorModel.gettanggalKeluar().equals("0000-00-00 00:00:00")) {
                jPanel8.remove(buttonSave1);
                jPanel8.remove(jButton2);
                fieldNomor1.setEnabled(false);
            }
        } else {
            dispose();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        labelNama = new javax.swing.JLabel();
        labelDate = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        mCameraPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        fieldNomor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fieldSerial = new javax.swing.JTextField();
        fieldNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fieldKeperluan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fieldTenant = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        fieldLantai = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        fieldUnit = new javax.swing.JTextField();
        fieldFemale = new javax.swing.JRadioButton();
        fieldMale = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        buttonSave = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        labelMasuk = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        mCameraPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        displaySerial = new javax.swing.JTextField();
        displayNama = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        displayKeperluan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        displayLantai = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        displayUnit = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        displayTenant = new javax.swing.JTextField();
        displayGender = new javax.swing.JTextField();
        fieldNomor1 = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        buttonSave1 = new javax.swing.JButton();
        buttonCancel1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        labelKeluar = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        cameraProgress = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Visitor Management System");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(63, 81, 181));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 0, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Visitor Management System");

        jPanel2.setBackground(new java.awt.Color(247, 151, 30));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 30));

        labelNama.setForeground(new java.awt.Color(255, 255, 255));

        labelDate.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelNama)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelNama, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(labelDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane2StateChanged(evt);
            }
        });

        mCameraPanel.setPreferredSize(new java.awt.Dimension(320, 240));
        mCameraPanel.setLayout(new java.awt.BorderLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nomor Kartu");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Serial");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nama");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Keperluan");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Tenant");

        fieldLantai.setEditable(false);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Lantai");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Unit");

        fieldUnit.setEditable(false);

        fieldFemale.setText("Female");
        fieldFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldFemaleActionPerformed(evt);
            }
        });

        fieldMale.setSelected(true);
        fieldMale.setText("Male");
        fieldMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldMaleActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Gender");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldKeperluan)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(fieldNomor, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fieldNama)
                    .addComponent(fieldTenant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldLantai, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(fieldMale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fieldFemale)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldUnit))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldNomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(fieldSerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldKeperluan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldTenant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldLantai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(fieldUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldFemale)
                    .addComponent(fieldMale)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonSave.setText("Save");

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        jButton1.setText("Capture");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(6, 6, 6)
                .addComponent(labelMasuk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSave)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton1)
                .addComponent(labelMasuk))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonSave)
                .addComponent(buttonCancel))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mCameraPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mCameraPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Masuk", jPanel5);

        mCameraPanel1.setPreferredSize(new java.awt.Dimension(320, 240));
        mCameraPanel1.setLayout(new java.awt.BorderLayout());

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Nomor Kartu");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Serial");

        displaySerial.setEditable(false);

        displayNama.setEditable(false);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Nama");

        displayKeperluan.setEditable(false);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Keperluan");

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Tenant");

        displayLantai.setEditable(false);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Lantai");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Unit");

        displayUnit.setEditable(false);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Gender");

        displayTenant.setEditable(false);

        displayGender.setEditable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(displayKeperluan)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(fieldNomor1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displaySerial, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(displayNama)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(displayLantai, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(displayUnit))
                    .addComponent(displayTenant)
                    .addComponent(displayGender, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(displaySerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldNomor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayKeperluan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(displayTenant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayLantai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(displayUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(displayGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonSave1.setText("Save");

        buttonCancel1.setText("Cancel");
        buttonCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancel1ActionPerformed(evt);
            }
        });

        jButton2.setText("Capture");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addGap(6, 6, 6)
                .addComponent(labelKeluar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonCancel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSave1)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(buttonSave1)
                .addComponent(buttonCancel1)
                .addComponent(jButton2)
                .addComponent(labelKeluar))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mCameraPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mCameraPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Keluar", jPanel6);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.add(cameraProgress);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane2StateChanged
        JTabbedPane sourceTabbedPane = (JTabbedPane) evt.getSource();
        int index = sourceTabbedPane.getSelectedIndex();

        new CamWorker(index).execute();
    }//GEN-LAST:event_jTabbedPane2StateChanged

    class CamWorker extends SwingWorker<Integer, Integer> {

        private final int index;

        public CamWorker(int index) {
            this.index = index;
        }

        @Override
        protected Integer doInBackground() throws Exception {
            if (mVisitorModel != null) {
                if (mVisitorModel.gettanggalKeluar().equals("0000-00-00 00:00:00")) {
                    mWebcam = Webcam.getDefault();
                    cameraProgress.setText("Closing existing camera");
                    if (mWebcam.isOpen()) {
                        mWebcam.close();
                    }
                    cameraProgress.setText("Setting camera size");
                    mWebcam.setViewSize(WebcamResolution.QVGA.getSize());
                }
            } else {
                mWebcam = Webcam.getDefault();
                cameraProgress.setText("Closing existing camera");
                if (mWebcam.isOpen()) {
                    mWebcam.close();
                }
                cameraProgress.setText("Setting camera size");
                mWebcam.setViewSize(WebcamResolution.QVGA.getSize());
            }

            switch (index) {
                case 1:
                    if (StringUtils.isEmpty(FormView.this.mFotoKeluar)) {
                        if (mVisitorModel != null) {
                            if (mVisitorModel.gettanggalKeluar().equals("0000-00-00 00:00:00")) {
                                if (mWebcam != null) {
                                    cameraProgress.setText("Opening the camera");
                                    mWebcam.open();
                                    cameraProgress.setText("Creating camera panel");
                                    WebcamPanel webcamPanel1 = new WebcamPanel(mWebcam);
                                    webcamPanel1.setFPSDisplayed(false);

                                    cameraProgress.setText("Adding camera panel");
                                    mCameraPanel1.removeAll();
                                    mCameraPanel1.add(webcamPanel1);
                                }
                            } else {
                                BufferedImage image = null;
                                try {
                                    cameraProgress.setText("Downloading image");
                                    String path = Utils.baseUrl2("files/visitor/" + mVisitorModel.getfotoKeluar());
                                    URL url = new URL(path);
                                    image = ImageIO.read(url);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                cameraProgress.setText("Displaying image");
                                mCameraPanel1.removeAll();
                                mCameraPanel1.add(new JLabel(new ImageIcon(image != null ? Utils.resize(image, 320, 240) : null)));
                            }
                        } else if (mWebcam != null) {
                            cameraProgress.setText("Opening the camera");
                            mWebcam.open();
                            cameraProgress.setText("Creating camera panel");
                            WebcamPanel webcamPanel1 = new WebcamPanel(mWebcam);
                            webcamPanel1.setFPSDisplayed(false);

                            cameraProgress.setText("Adding camera panel");
                            mCameraPanel1.removeAll();
                            mCameraPanel1.add(webcamPanel1);
                        }
                    }
                    break;

                default:
                    if (StringUtils.isEmpty(FormView.this.mFotoMasuk)) {
                        if (mVisitorModel != null) {
                            if (mVisitorModel.gettanggalMasuk().equals("0000-00-00 00:00:00")) {
                                if (mWebcam != null) {
                                    cameraProgress.setText("Opening the camera");
                                    mWebcam.open();
                                    cameraProgress.setText("Creating camera panel");
                                    WebcamPanel webcamPanel = new WebcamPanel(mWebcam);
                                    webcamPanel.setFPSDisplayed(false);

                                    cameraProgress.setText("Adding camera panel");
                                    mCameraPanel.removeAll();
                                    mCameraPanel.add(webcamPanel);
                                }
                            } else {
                                BufferedImage image = null;
                                try {
                                    cameraProgress.setText("Downloading image");
                                    String path = Utils.baseUrl2("files/visitor/" + mVisitorModel.getfotoMasuk());
                                    URL url = new URL(path);
                                    image = ImageIO.read(url);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                cameraProgress.setText("Displaying image");
                                mCameraPanel.removeAll();
                                mCameraPanel.add(new JLabel(new ImageIcon(image != null ? Utils.resize(image, 320, 240) : null)));
                            }
                        } else if (mWebcam != null) {
                            cameraProgress.setText("Opening the camera");
                            mWebcam.open();
                            cameraProgress.setText("Creating camera panel");
                            WebcamPanel webcamPanel = new WebcamPanel(mWebcam);
                            webcamPanel.setFPSDisplayed(false);

                            cameraProgress.setText("Adding camera panel");
                            mCameraPanel.removeAll();
                            mCameraPanel.add(webcamPanel);
                        }
                    }
                    break;
            }
            cameraProgress.setText("CamWorker finished");
            return 1;
        }

        @Override
        public void done() {
            cameraProgress.setText("");
        }
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        new DisposeWorker().execute();
    }//GEN-LAST:event_formWindowClosed

    class DisposeWorker extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {
            cameraProgress.setText("Closing existing camera");
            Webcam.getDefault().close();
            Webcam.shutdown();

            if (!StringUtils.isEmpty(FormView.this.mFotoMasuk)) {
                cameraProgress.setText("Trying to delete old images");
                File file = new File("masuk_" + FormView.this.mFotoMasuk + ".png");

                if (file.delete()) {
                    cameraProgress.setText("Old images deleted");
                } else {
                    cameraProgress.setText("Unable to delete old images");
                }
            }
            if (!StringUtils.isEmpty(FormView.this.mFotoKeluar)) {
                cameraProgress.setText("Trying to delete old images");
                File file = new File("keluar_" + FormView.this.mFotoKeluar + ".png");

                if (file.delete()) {
                    cameraProgress.setText("Old images deleted");
                } else {
                    cameraProgress.setText("Unable to deletes old image");
                }
            }
            return 1;
        }

        @Override
        public void done() {
            FormView.this.dispose();
        }
    }
    private void buttonCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancel1ActionPerformed
        new DisposeWorker().execute();
    }//GEN-LAST:event_buttonCancel1ActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        new DisposeWorker().execute();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void fieldMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldMaleActionPerformed
        fieldFemale.setSelected(false);
    }//GEN-LAST:event_fieldMaleActionPerformed

    private void fieldFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldFemaleActionPerformed
        fieldMale.setSelected(false);
    }//GEN-LAST:event_fieldFemaleActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (StringUtils.isEmpty(cameraProgress.getText())) {
            new FotoMasukWorker().execute();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (StringUtils.isEmpty(cameraProgress.getText())) {
            new FotoKeluarWorker().execute();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    class FotoKeluarWorker extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {
            if (!StringUtils.isEmpty(FormView.this.mFotoKeluar)) {
                cameraProgress.setText("Trying to delete old image");
                File file = new File("keluar_" + FormView.this.mFotoKeluar + ".png");

                if (file.delete()) {
                    cameraProgress.setText("Old image deleted");
                } else {
                    cameraProgress.setText("Unable to delete old image");
                }
            }

            FormView.this.mFotoKeluar = Utils.getCurrentDateTime();
            cameraProgress.setText("Trying to save image to:" + "keluar_" + FormView.this.mFotoKeluar + ".png");
            try {
                ImageIO.write(FormView.this.mWebcam.getImage(), "PNG", new File("keluar_" + FormView.this.mFotoKeluar + ".png"));
            } catch (IOException ex) {
                cameraProgress.setText("Unable to save image to:" + "keluar_" + FormView.this.mFotoKeluar + ".png");
                Logger.getLogger(FormView.class.getName()).log(Level.SEVERE, null, ex);
            }
            cameraProgress.setText("Image saved to:" + "keluar_" + FormView.this.mFotoKeluar + ".png");

            BufferedImage image = null;
            try {
                cameraProgress.setText("Displaying image");
                image = ImageIO.read(new File("keluar_" + FormView.this.mFotoKeluar + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            mCameraPanel1.removeAll();
            mCameraPanel1.add(new JLabel(new ImageIcon(image != null ? Utils.resize(image, 320, 240) : null)));

            cameraProgress.setText("Closing existing camera");
            FormView.this.mWebcam.close();
            return 1;
        }

        @Override
        public void done() {
            cameraProgress.setText("");
            labelKeluar.setText(FormView.this.mFotoKeluar);
            jButton2.setEnabled(false);
        }
    }

    class FotoMasukWorker extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {
            if (!StringUtils.isEmpty(FormView.this.mFotoMasuk)) {
                cameraProgress.setText("Trying to delete old image");
                File file = new File("masuk_" + FormView.this.mFotoMasuk + ".png");

                if (file.delete()) {
                    cameraProgress.setText("Old image deleted");
                } else {
                    cameraProgress.setText("Unable to delete old image");
                }
            }

            FormView.this.mFotoMasuk = Utils.getCurrentDateTime();
            cameraProgress.setText("Trying to save image to:" + "masuk_" + FormView.this.mFotoMasuk + ".png");
            try {
                ImageIO.write(FormView.this.mWebcam.getImage(), "PNG", new File("masuk_" + FormView.this.mFotoMasuk + ".png"));
            } catch (IOException ex) {
                cameraProgress.setText("Unable to save image to:" + "masuk_" + FormView.this.mFotoMasuk + ".png");
                Logger.getLogger(FormView.class.getName()).log(Level.SEVERE, null, ex);
            }
            cameraProgress.setText("Image saved to:" + "masuk_" + FormView.this.mFotoMasuk + ".png");

            BufferedImage image = null;
            try {
                cameraProgress.setText("Displaying image");
                image = ImageIO.read(new File("masuk_" + FormView.this.mFotoMasuk + ".png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            mCameraPanel.removeAll();
            mCameraPanel.add(new JLabel(new ImageIcon(image != null ? Utils.resize(image, 320, 240) : null)));

            cameraProgress.setText("Closing existing camera");
            FormView.this.mWebcam.close();
            return 1;
        }

        @Override
        public void done() {
            cameraProgress.setText("");
            labelMasuk.setText(FormView.this.mFotoMasuk);
            jButton1.setEnabled(false);
        }
    }

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
            java.util.logging.Logger.getLogger(FormView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonCancel1;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonSave1;
    private javax.swing.JLabel cameraProgress;
    private javax.swing.JTextField displayGender;
    private javax.swing.JTextField displayKeperluan;
    private javax.swing.JTextField displayLantai;
    private javax.swing.JTextField displayNama;
    private javax.swing.JTextField displaySerial;
    private javax.swing.JTextField displayTenant;
    private javax.swing.JTextField displayUnit;
    private javax.swing.JRadioButton fieldFemale;
    private javax.swing.JTextField fieldKeperluan;
    private javax.swing.JTextField fieldLantai;
    private javax.swing.JRadioButton fieldMale;
    private javax.swing.JTextField fieldNama;
    private javax.swing.JTextField fieldNomor;
    private javax.swing.JComboBox<VisitorModel> fieldNomor1;
    private javax.swing.JTextField fieldSerial;
    private javax.swing.JComboBox<TenantModel> fieldTenant;
    private javax.swing.JTextField fieldUnit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelKeluar;
    private javax.swing.JLabel labelMasuk;
    private javax.swing.JLabel labelNama;
    private javax.swing.JPanel mCameraPanel;
    private javax.swing.JPanel mCameraPanel1;
    // End of variables declaration//GEN-END:variables
}
