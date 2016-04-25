/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms.utils;

import com.auliayf.vms.models.BaseModel;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author uoy
 */
public abstract class MYTable extends AbstractTableModel {

    protected List<? extends BaseModel> mModels;

    public MYTable(List<? extends BaseModel> models) {
        this.mModels = models;
    }

    @Override
    public int getRowCount() {
        return mModels.size();
    }

    @Override
    public int getColumnCount() {
        return getCols().length;
    }

    @Override
    public String getColumnName(int index) {
        return getCols()[index];
    }

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    public abstract String[] getCols();

}
