package com.company.GrafLogic;

import java.util.*;

public class Graf {
    public ArrayList<Grany> granyList;
    public ArrayList<Rebra> rebraList;

    Graf() {
        granyList = new ArrayList<>();
        rebraList = new ArrayList<>();
    }

    Graf(int countOfGraney, int countOfReber) { //четко ограниченный граф
        granyList = new ArrayList<>(countOfGraney);
        for (int i = 0; i < countOfGraney; i++) {
            granyList.add(new Grany());
        }
        rebraList = new ArrayList<>(countOfReber);
    }

    public Graf(int countOfGraney) { //только грани заданны
        granyList = new ArrayList<>(countOfGraney);
        for (int i = 0; i < countOfGraney; i++) {
            granyList.add(new Grany());
        }
        rebraList = new ArrayList<>();
    }

    public void deleteRebra(Rebra deleted) //удаление ребра, учитывая степени граней
    {
        deleted.startPoint.StepenGrany -= 1;
        deleted.finishPoint.StepenGrany -= 1;
        rebraList.remove(deleted);
    }

    public void deleteRebra(int numRebra) { //удаление ребра по номеру, учитывая степени граней
        rebraList.get(numRebra - 1).startPoint.StepenGrany = -1;
        rebraList.get(numRebra - 1).finishPoint.StepenGrany = -1;
        rebraList.remove(numRebra - 1);
    }

    public void deleteAllRebra() {
        for (Grany grany : this.granyList) {
            grany.setNullStepenGrany();
        }
        rebraList.clear();
    } //удаление всех ребер

    public void addGrany(Grany g) { //добавляторы
        granyList.add(g);
    }

    public void addRebra(Rebra r) {
        rebraList.add(r);
    }

    public void addRebra(int first, int finish) {
        addRebra(new Rebra(getGrany(first), getGrany(finish)));
    }

    public Grany getGrany(int numGrany) { //getters and setters с учетом счета от 1
        return granyList.get(numGrany - 1);
    }

    public Rebra getRebra(int numRebra) {
        return rebraList.get(numRebra - 1);
    }

    //отсюда обновить методы в иллюстрации
    public int getCountOfLoops() { //сколько петель
        int loops = 0; //количество петель
        for (Rebra rebra : rebraList) {
            if (rebra.isLoop()) {
                loops += 1;
            }
        }
        return loops;
    }

    public boolean adjacency(Rebra first, Rebra second) { //смежность ребер
        if ((first.finishPoint == second.finishPoint) || (first.startPoint == second.finishPoint) || (first.startPoint == second.startPoint) || (first.finishPoint == second.startPoint)) {
            return first != second;
        }
        return false;
    }

    public boolean transitive() { //проверка на транзитивность (если вершины 1 и 2, 2 и 3 соединены, то и 1 и 3 соеденены)
        //изначально транзитивный, если даже нет ребер, тк условие транзитивноти соблюдено
        for (Rebra a : rebraList) { //первая связь для проверки
            for (Rebra b : rebraList) { //вторая связь для проверки
                if (a != b && !(a.isLoop() || b.isLoop()) && adjacency(a, b)) { //отобрали то, что стоит проверять
                    boolean existC = false;
                    for (Rebra c : rebraList) { //связь, существование которой проверяется
                        if (adjacency(a, c) && adjacency(b, c))
                            existC = true;
                    }
                    if (!existC) {
                        return false; //не нашли подходящее ребро
                    }
                }
            }
        }
        return true;
    }

    public boolean simmetry() {//симметричный граф - транзитивный, без петель, с единой степенью граней
        if (transitive() && (getCountOfLoops() == 0)) {
            for (Grany gran : granyList) {
                if (gran.StepenGrany != granyList.get(0).getStepenGrany()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean refleks() { // рефлексивность - все вершины имеют петли
        for (Grany gran : granyList) {
            boolean exist = false;
            for (Rebra rebro : rebraList) {
                if (rebro.isLoop() && rebro.startPoint == gran) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                return false;
            }
        }
        return true;
    }

    public boolean connect(Grany a, Grany b) { //наличие пути меж вершинами
        if (a == b) { //если сравниваемые грани совпали, то нет смысла проверять)
            return true;
        }
        boolean[] visited = new boolean[granyList.size()];
        visited[granyList.indexOf(a)] = true;  //начальная точка посещена
        for (Rebra r : rebraList) { //идем по ребрам, которые соединены с А и непройдеными вершинами
            if ((r.startPoint == a && !visited[granyList.indexOf(r.finishPoint)]) || (r.finishPoint == a && !visited[granyList.indexOf(r.startPoint)])) {
                if (r.startPoint == a && !visited[granyList.indexOf(r.finishPoint)]) {
                    if (connect(r.finishPoint, b, visited)) { //рекурсия для прохода по всем возможным путям
                        return true;
                    }
                }
                if (r.finishPoint == a && !visited[granyList.indexOf(r.startPoint)]) {
                    if (connect(r.startPoint, b, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean connect(Grany a, Grany b, boolean[] visited) { //наличие пути меж вершинами вспомогательное
        if (a == b) { //если сравниваемые грани совпали, то они соединены
            return true;
        }
        visited[granyList.indexOf(a)] = true; //начальная точка посещена
        for (Rebra r : rebraList) { //идем по ребрам, которые соединены с А и непройдеными вершинами
            if (r.startPoint == a && !visited[granyList.indexOf(r.finishPoint)]) {
                if (connect(r.finishPoint, b, visited)) {
                    return true;
                }
            }
            if (r.finishPoint == a && !visited[granyList.indexOf(r.startPoint)]) {
                if (connect(r.startPoint, b, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean connectivity() {//связность
        for (Grany gran1 : granyList) { //обходим пары вершин, чтобы у каждой из них был путь к любой вершине
            for (Grany gran2 : granyList) {
                if (!connect(gran1, gran2)) {
                    return false; //если хоть одна пара вершин не имеют общего пути, то граф не связен
                }
            }
        }
        return true;
    }

    public String minimalWay(Grany A, Grany B) { //функция поиска минимального пути
        if (!connect(A, B)) {
            return "Вершины не связаны";
        }
        boolean[] visited = new boolean[granyList.size()]; //Посещенные вершины первоначально false
        visited[granyList.indexOf(A)] = true;  //начальная точка посещена
        StringBuilder way = new StringBuilder(); //Путь мин до вершин
        int min = -1; //значение минимального пути до вершины
        for (Rebra r : rebraList) { //идем по ребрам, которые соединены с А и непройдеными вершинами
            if ((r.startPoint == A && !visited[granyList.indexOf(r.finishPoint)]) || (r.finishPoint == A && !visited[granyList.indexOf(r.startPoint)])) {
                if (r.startPoint == A && !visited[granyList.indexOf(r.finishPoint)]) {
                    way.append(minimalWay(r.finishPoint, B, visited, min));//рекурсия для прохода по всем возможным путям
                }
                if (r.finishPoint == A && !visited[granyList.indexOf(r.startPoint)]) {
                    way.append(minimalWay(r.startPoint, B, visited, min));
                }
                min += r.getPrice();
            }
        }
        System.out.println("\nКратчайшие расстояния до вершин:");
        return way.toString();
    }

    public String minimalWay(Grany A, Grany B, boolean[] visited, int min) { //функция поиска минимального пути
        StringBuilder result = new StringBuilder();
        if (A == B) { //если сравниваемые грани совпали, то они соединены
            return String.valueOf(granyList.indexOf(B));
        }
        visited[granyList.indexOf(A)] = true; //начальная точка посещена
        for (Rebra r : rebraList) { //идем по ребрам, которые соединены с А и непройдеными вершинами
            if (r.startPoint == A && !visited[granyList.indexOf(r.finishPoint)]) {
                if (r.startPoint == A && !visited[granyList.indexOf(r.finishPoint)]) {
                    result.append(minimalWay(r.finishPoint, B, visited, min));
                }
            }
            if (r.finishPoint == A && !visited[granyList.indexOf(r.startPoint)]) {
                if (r.finishPoint == A && !visited[granyList.indexOf(r.startPoint)]) {
                    result.append(minimalWay(r.startPoint, B, visited, min));
                }
            }
        }
        return result.toString();
    }
    public String changeCountOfGrany(int newCount){
        if (newCount < granyList.size()) {
            while (newCount!=granyList.size()){
                granyList.remove(granyList.size()-1);
            }
            return "Количество вершин уменьшено до "+newCount;
        }
        else if (newCount == granyList.size()){
            return "Количество вершин не изменено. Было указано значение равное предыдущему";
        }
        else {
            while (newCount!=granyList.size()){
                granyList.add(new Grany());
            }
            return "Количество вершин увеличено до "+newCount;
        }
    }

}


