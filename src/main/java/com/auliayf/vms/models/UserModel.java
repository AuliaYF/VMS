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
public class UserModel {

    public String cUsername;
    public String cNama;

    public UserModel() {
    }

    public UserModel(String cUsername, String cNama) {
        this.cUsername = cUsername;
        this.cNama = cNama;
    }

    public void setcUsername(String cUsername) {
        this.cUsername = cUsername;
    }

    public void setcNama(String cNama) {
        this.cNama = cNama;
    }

    public String getcUsername() {
        return cUsername;
    }

    public String getcNama() {
        return cNama;
    }

}
