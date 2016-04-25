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
public class TenantModel extends BaseModel {

    public String idTenant;
    public String namaTenant;
    public String lantaiTenant;
    public String unitTenant;

    public String getIdTenant() {
        return idTenant;
    }

    public String getNamaTenant() {
        return namaTenant;
    }

    public String getLantaiTenant() {
        return lantaiTenant;
    }

    public String getUnitTenant() {
        return unitTenant;
    }

    @Override
    public String toString() {
        return namaTenant;
    }
}
