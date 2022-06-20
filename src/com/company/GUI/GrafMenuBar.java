package com.company.GUI;

import javax.swing.*;

public class GrafMenuBar extends JMenuBar {
    public GrafMenuBar(){
        JMenu helpMenu = new JMenu("Справка");
        JMenuItem rebraHelpItem = new JMenuItem("Ребра графа");
        helpMenu.add(rebraHelpItem);
        JMenuItem granyHelpItem = new JMenuItem("Вершины графа");
        helpMenu.add(granyHelpItem);
        JMenuItem buttonHelpItem = new JMenuItem("Функциональные кнопки");
        helpMenu.add(buttonHelpItem);
        this.add(helpMenu);
    }

}
