/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms.tables;

import com.auliayf.vms.models.VisitorModel;
import com.auliayf.vms.utils.MYTable;
import com.auliayf.vms.utils.Utils;
import java.util.List;

/**
 *
 * @author uoy
 */
public class VisitorListTable extends MYTable {

    private final String[] cols = {"No", "Tanggal", "Nama", "Tujuan", "Lantai", "Waktu", "Status"};

    public VisitorListTable(List<VisitorModel> models) {
        super(models);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VisitorModel model = (VisitorModel) mModels.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return Utils.formatDate(model.gettanggalMasuk());
            case 2:
                return model.getnamaVisitor();
            case 3:
                return model.getkeperluanVisitor();
            case 4:
                return model.getnamaLantai();
            case 5:
                return Utils.formatTime(model.gettanggalMasuk());
            case 6:
                return model.tanggalKeluar.equals("0000-00-00 00:00:00") ? "In" : "Out";
        }

        return null;
    }

    @Override
    public String[] getCols() {
        return cols;
    }
}
