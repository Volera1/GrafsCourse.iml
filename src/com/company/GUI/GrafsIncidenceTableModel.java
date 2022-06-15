package com.company.GUI;

import com.company.GrafLogic.Graf;

import javax.swing.table.AbstractTableModel;

public class GrafsIncidenceTableModel extends AbstractTableModel { //класс модели для инцендентно заданного графа
    private final Graf data;
    public GrafsIncidenceTableModel(Graf graf){data=graf;} //
    @Override
    public String getColumnName(int column) {
        if (column<data.granyList.size()){
            return String.valueOf(column+1);
        }
        return "";
    }
    @Override
    public int getRowCount() {
        if(data.rebraList.isEmpty()){
            return 1;
        }
        return data.rebraList.size();
    }

    @Override
    public int getColumnCount() {
        return data.granyList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}
