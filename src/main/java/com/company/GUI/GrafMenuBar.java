package main.java.com.company.GUI;

import javax.swing.*;

import static javax.swing.JOptionPane.*;

public class GrafMenuBar extends JMenuBar {
    public GrafMenuBar(){
        //мекет меню справки
        JMenu helpMenu = new JMenu("Справка");
        JMenuItem rebraHelpItem = new JMenuItem("Ребра графа");
        helpMenu.add(rebraHelpItem);
        JMenuItem granyHelpItem = new JMenuItem("Вершины графа");
        helpMenu.add(granyHelpItem);
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
        granyHelpItem.addActionListener(e -> showMessageDialog(null, """
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
        JMenuItem outputFileItem = new JMenuItem("Вывод в файл");
        JMenuItem inputFileItem = new JMenuItem("Ввод из файла");
        fileMenu.add(outputFileItem);
        fileMenu.add(inputFileItem);

        //сборка панели меню
        this.add(helpMenu);
        this.add(waysMenu);
        this.add(fileMenu);
    }

}
