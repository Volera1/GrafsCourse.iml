package com.company.GUI;

import com.company.GrafLogic.Graf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormNew extends JFrame{
    private JPanel mainPanel;
    private JScrollPane scrollGraf;
    private JTable tableGraf;
    private JSpinner spinnerCountOfGraney;

    private JButton butGetChanges;
    private JButton butClean;
    private JLabel massageLable;

    public FormNew(){
        super("Graf");
        setSize(700,400);
        setMinimumSize(new Dimension(700,400));

        //работа с полями
        Graf graf = new Graf(5);
        graf.addRebra(1,3);
        graf.addRebra(3,5);
        graf.addRebra(4,1);

        GrafsIncidenceTableModel grafsIncidenceTableModel = new GrafsIncidenceTableModel(graf);
        SpinnerNumberModel modelSpinnerGraney = new SpinnerNumberModel(1, 1,40, 1);
        spinnerCountOfGraney.setModel(modelSpinnerGraney);
        //таблица
        tableGraf.setModel(grafsIncidenceTableModel);
        //кнопки
        butGetChanges.addActionListener(e -> {
            int valueOfChangeCountGrany = (Integer) spinnerCountOfGraney.getValue();
            massageLable.setText(graf.changeCountOfGrany(valueOfChangeCountGrany));
            grafsIncidenceTableModel.fireTableStructureChanged();
        });
        butClean.addActionListener(e -> {
            int valueOfChangeCountGrany = (Integer) spinnerCountOfGraney.getValue();
            graf.deleteAllRebra();
            graf.changeCountOfGrany(valueOfChangeCountGrany);
            massageLable.setText("Граф очищен");
            grafsIncidenceTableModel.fireTableStructureChanged();
        });

        //основные настройки окна
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
    }
    public static void main(String[] args){
        JFrame newFrame=new FormNew();
        newFrame.setVisible(true);
    }
}
