/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms.models;

/**
 *
 * @author uoy
 */
public class VisitorModel extends BaseModel {

    public String idVisitor;
    public String nomorVisitor;
    public String serialVisitor;
    public String namaVisitor;
    public String keperluanVisitor;
    public String idTenant;
    public String namaTenant;
    public String namaLantai;
    public String namaUnit;
    public String genderVisitor;
    public String fotoMasuk;
    public String fotoKeluar;
    public String tanggalMasuk;
    public String tanggalKeluar;
    public String createBy;
    public String createDate;
    public String updateBy;
    public String updateDate;

    public String getidVisitor() {
        return idVisitor;
    }

    public String getnomorVisitor() {
        return nomorVisitor;
    }

    public String getserialVisitor() {
        return serialVisitor;
    }

    public String getnamaVisitor() {
        return namaVisitor;
    }

    public String getkeperluanVisitor() {
        return keperluanVisitor;
    }

    public String getidTenant() {
        return idTenant;
    }

    public String getnamaTenant() {
        return namaTenant;
    }

    public String getnamaLantai() {
        return namaLantai;
    }

    public String getnamaUnit() {
        return namaUnit;
    }

    public String getgenderVisitor() {
        return genderVisitor;
    }

    public String getfotoMasuk() {
        return fotoMasuk;
    }

    public String getfotoKeluar() {
        return fotoKeluar;
    }

    public String gettanggalMasuk() {
        return tanggalMasuk;
    }

    public String gettanggalKeluar() {
        return tanggalKeluar;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    @Override
    public String toString() {
        return nomorVisitor;
    }
}
