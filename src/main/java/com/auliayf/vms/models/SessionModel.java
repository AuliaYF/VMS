/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author uoy
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionModel {

    public String status;
    public String msg;
    public UserModel user;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public UserModel getUser() {
        return user;
    }
}
