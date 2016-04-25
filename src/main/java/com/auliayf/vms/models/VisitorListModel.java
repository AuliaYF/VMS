/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author uoy
 */
public class VisitorListModel {
    VisitorModel visitors[];
    
    public VisitorModel[] getVisitors(){
        return visitors;
    }
    
    public List<VisitorModel> convertToArrayList() {
        List<VisitorModel> tempArray = new ArrayList<>();

        tempArray = Arrays.asList(this.visitors);

        return tempArray;
    }
}
