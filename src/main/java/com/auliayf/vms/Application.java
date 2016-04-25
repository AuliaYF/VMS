/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms;

import com.auliayf.vms.utils.Session;
import com.auliayf.vms.views.LoginView;
import com.auliayf.vms.views.MainView;

/**
 *
 * @author uoy
 */
public class Application {
    public static void main(String[] args){
        if(Session.getSession() == null){
            new LoginView().setVisible(true);
        }else{
            new MainView().setVisible(true);
        }
    }
}
