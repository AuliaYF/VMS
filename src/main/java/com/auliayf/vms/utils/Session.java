/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms.utils;

import com.auliayf.vms.models.UserModel;

/**
 *
 * @author uoy
 */
public class Session {

    private static UserModel SESSION = null;

    public static void setSession(UserModel sessionModel) {
        SESSION = sessionModel;
    }

    public static UserModel getSession() {
        return SESSION;
    }

}
