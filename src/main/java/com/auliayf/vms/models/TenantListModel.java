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
public class TenantListModel extends BaseModel {

    TenantModel tenants[];

    public TenantModel[] getTenants() {
        return tenants;
    }
}
