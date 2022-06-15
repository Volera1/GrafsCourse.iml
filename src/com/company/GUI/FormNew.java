package com.company.GUI;

import com.company.GrafLogic.Graf;

import javax.swing.*;

public class FormNew extends JFrame{
    private final Graf graf=new Graf(1);
    private JPanel mainPanel;
    private JScrollPane scrollGraf;
    private JTable tableGraf;

    public FormNew(){
        super("Graf");
        setSize(220,220);

        GrafsIncidenceTableModel grafsIncidenceTableModel = new GrafsIncidenceTableModel(graf);
        tableGraf.setModel(grafsIncidenceTableModel);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
    }
    public static void main(String[] args){
        JFrame newFrame=new FormNew();
        newFrame.setVisible(true);
    }
}
