package com.company.GUI;

import com.company.GrafLogic.Graf;

import javax.swing.table.AbstractTableModel;

public class GrafsIncidenceTableModel extends AbstractTableModel { //класс модели для инцендентно заданного графа
    private final Graf data;

    public GrafsIncidenceTableModel(Graf graf) {
        data = graf;
    } //конструктор с заданным графом

    @Override
    public String getColumnName(int column) {
        if (column==0){
            return "№";
        }
        if (column <= data.vertexList.size()) {
            return String.valueOf(column);
        }
        return "";
    }

    @Override
    public int getRowCount() {
        if (data.rebraList.isEmpty()) {
            return 1;
        }
        return data.rebraList.size()+1;
    }

    @Override
    public int getColumnCount() {
        return data.vertexList.size()+1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex==0){
            return rowIndex+1;
        }
        if (data.rebraList.isEmpty()||data.rebraList.size()==rowIndex) {
            return false;
        }
        return data.getRebra(rowIndex + 1).startPoint == data.getGrany(columnIndex) || data.getRebra(rowIndex + 1).finishPoint == data.getGrany(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex==0){
            return Integer.class;
        }
        return Boolean.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (getValueAt(rowIndex,columnIndex) instanceof Boolean) {
            if (getValue(rowIndex,columnIndex)) { //Если ребро было инцендентно вершине, то мы создаем петлю. Ребро не может быть без начала и конца
                if (data.getRebra(rowIndex+1).isLoop()){ //если нажатое было петлей, то удаляем
                    data.rebraList.remove(rowIndex);
                    this.fireTableStructureChanged();
                    return;
                }
                if (data.getGrany(columnIndex) == data.getRebra(rowIndex+1).finishPoint) {
                    data.getRebra(rowIndex+1).finishPoint = data.getRebra(rowIndex+1).startPoint;
                } else {
                    data.getRebra(rowIndex+1).startPoint = data.getRebra(rowIndex+1).finishPoint;
                }
            } else {
                if (data.rebraList.isEmpty()||data.rebraList.size()==rowIndex){
                    data.addRebra(columnIndex,columnIndex);
                }
                if (data.getRebra(rowIndex+1).isLoop()) {
                    data.getRebra(rowIndex+1).finishPoint = data.getGrany(columnIndex);
                }
            }
        }
        this.fireTableStructureChanged();
    }
    public <T> T getValue(int row, int column) {
        return (T) getValueAt(row, column);
    } //для преобразования классов внутри методов этого класса (setValueAt)
}