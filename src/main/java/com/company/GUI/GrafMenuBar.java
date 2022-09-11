package com.company.GUI;

import com.company.GrafLogic.Graf;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.io.IOException;

import static javax.swing.JOptionPane.*;
import static com.company.GrafExelWork.*;

public class GrafMenuBar extends JMenuBar {
    private final Component parent = getParent();

    JFileChooser fileChoose = new JFileChooser("C:\\Users\\User\\Documents\\GrafsCourse");
    FileFilter filter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");

    public GrafMenuBar(Graf graf,GrafsIncidenceTableModel grafsIncidenceTableModel){
        //добавляем фильтр файловыбиратору
        fileChoose.addChoosableFileFilter(filter);
        fileChoose.setFileFilter(filter);
        //мекет меню справки
        JMenu helpMenu = new JMenu("Справка");
        JMenuItem rebraHelpItem = new JMenuItem("Ребра графа");
        helpMenu.add(rebraHelpItem);
        JMenuItem vertexHelpItem = new JMenuItem("Вершины графа");
        helpMenu.add(vertexHelpItem);
        JMenuItem buttonHelpItem = new JMenuItem("Функциональные кнопки");
        helpMenu.add(buttonHelpItem);
        //листенеры меню справки
        rebraHelpItem.addActionListener(e -> showMessageDialog(null, """
                        Ребра графа представляются строками таблицы (согласно правилам задания графа матрицей инцендентности).
                        В строке может стоять максимум две галочки, так как у стороны графа есть только начало и конец.
                        Минимальное количество галочек для существования ребра - 1. В таком случае ребро является петлей.
                        При убирании последней проставленной галочки в строке ребро удаляется!
                        Для добавления нового ребра достаточно в последней строке таблицы (она всегда пустая для заполнения и не считается ребром) поставить галочку к любому номеру вершины.""",
                "Ребра графа",JOptionPane.PLAIN_MESSAGE));
        vertexHelpItem.addActionListener(e -> showMessageDialog(null, """
                        Вершины графа представляются столбцами таблицы (согласно правилам задания графа матрицей инцендентности).
                        В столбце может стоять сколко угодно галочек, в том числе и 0.
                        Количество вершин регулируется при помощи счетчика, который расположен справа в верхней части окна. (от 1 до 40 вершин)
                        Для применения нового количесвтва вершин необходимо нажать кнопку ПРИМЕНИТЬ !!!
                        При уменьшении количества вершин последние проставленные галочки удаляются (создаются петли с оставшимися у ребер вершинами и удаляются петли)
                        При увеличении количетва вершин добаляются новые вершины без связей""",
                "Вершины графа",JOptionPane.PLAIN_MESSAGE));
        buttonHelpItem.addActionListener(e -> showMessageDialog(null, """
                        Основные функциональные кнопки расположены справа в нижней части экрана
                        Кнопка СВОЙСТВА ГРАФА отображает на панеле выше свойства графа, которые были вычислены.
                        Кнопка ОЧИСТИТЬ ГРАФ очищает всю информацию о графе. Удаляются все ребра, а вершины приходят к количеству, указанному на счетчике
                        Кнопка ПРИМЕНИТЬ создана для регулирования количества вершин в графе. Подробнее можно узнать в разделе Справка/Вершины графа""",
                "Функциональные кнопки",JOptionPane.PLAIN_MESSAGE));
        //Пути
        JMenu waysMenu = new JMenu("Пути");
        JMenuItem minimalWayItem = new JMenuItem("Минимальный путь");
        waysMenu.add(minimalWayItem);

        //работа с файлами
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem outputFileItem = new JMenuItem("Сохранить файл");
        JMenuItem inputFileItem = new JMenuItem("Ввод из файла");
        fileMenu.add(outputFileItem);
        fileMenu.add(inputFileItem);
        outputFileItem.addActionListener(e -> { //сохранение файла
            int outFile = fileChoose.showSaveDialog(parent); //получаем информацию о выбранном файле
            switch (outFile){
                case JFileChooser.APPROVE_OPTION -> { //файл выбран успешно, сохраняем
                    try {
                        showMessageDialog(null,crateGrafExel(graf, String.valueOf(fileChoose.getSelectedFile())),"Сохрание",JOptionPane.PLAIN_MESSAGE);
                    } catch (IOException ex) {
                        showMessageDialog(null,"Что-то пошло не так","Ошибка сохранения",JOptionPane.PLAIN_MESSAGE);
                        ex.printStackTrace();
                    }
                    }
                case JFileChooser.ERROR_OPTION -> { //произошла ошибка, сообщаем об этом
                    showMessageDialog(null,"Файл не был сохранен","Сохрание",JOptionPane.PLAIN_MESSAGE);
                    }
            }

        }        );
        inputFileItem.addActionListener(e -> { //открытие фала
            int inFile = fileChoose.showOpenDialog(parent);
            switch (inFile){
                case JFileChooser.APPROVE_OPTION -> { //файл выбран успешно, заполняем
                    try {
                        showMessageDialog(null,readGrafExel(graf, String.valueOf(fileChoose.getSelectedFile())),"Ввод"+graf.rebraList.size()+graf.vertexList.size(),JOptionPane.PLAIN_MESSAGE);
                        grafsIncidenceTableModel.fireTableStructureChanged();
                    } catch (IOException ioException) {
                        showMessageDialog(null,"Что-то пошло не так","Ошибка чтения файла",JOptionPane.PLAIN_MESSAGE);
                        ioException.printStackTrace();
                    }

                }
                case JFileChooser.ERROR_OPTION -> { //произошла ошибка, сообщаем об этом
                    showMessageDialog(null,"Файл не введен","Ввод",JOptionPane.PLAIN_MESSAGE);
                    }
            }

        });

        //сборка панели меню
        this.add(helpMenu);
        this.add(waysMenu);
        this.add(fileMenu);
    }

}
