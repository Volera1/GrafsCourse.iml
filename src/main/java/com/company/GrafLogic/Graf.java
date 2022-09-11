package com.company.GrafLogic;

import java.util.*;

public class Graf {
    public ArrayList<Vertex> vertexList;
    public ArrayList<Rebra> rebraList;

    public Graf() {
        vertexList = new ArrayList<>();
        rebraList = new ArrayList<>();
    }

    public Graf(int countOfGraney, int countOfReber) { //четко ограниченный граф
        vertexList = new ArrayList<>(countOfGraney);
        for (int i = 0; i < countOfGraney; i++) {
            vertexList.add(new Vertex());
        }
        rebraList = new ArrayList<>(countOfReber);
    }

    public Graf(int countOfGraney) { //только грани заданны
        vertexList = new ArrayList<>(countOfGraney);
        for (int i = 0; i < countOfGraney; i++) {
            vertexList.add(new Vertex());
        }
        rebraList = new ArrayList<>();
    }

    public void cloneGraf(Graf newgraf){
        this.vertexList= newgraf.vertexList;
        this.rebraList= newgraf.rebraList;
    }

    public void deleteRebra(Rebra deleted) //удаление ребра, учитывая степени вершин
    {
        deleted.startPoint.StepenVertex -= 1;
        deleted.finishPoint.StepenVertex -= 1;
        rebraList.remove(deleted);
    }

    public void deleteRebra(int numRebra) { //удаление ребра по номеру, учитывая степени вершин
        rebraList.get(numRebra - 1).startPoint.StepenVertex = -1;
        rebraList.get(numRebra - 1).finishPoint.StepenVertex = -1;
        rebraList.remove(numRebra - 1);
    }

    public void deleteAllRebra() {
        for (Vertex vertex : this.vertexList) {
            vertex.setNullStepenGrany();
        }
        rebraList.clear();
    } //удаление всех ребер

    public void addVertex(Vertex g) { //добавляторы
        vertexList.add(g);
    }

    public void addVertex(){vertexList.add(new Vertex());}

    public void addRebra(){rebraList.add(new Rebra());}

    public void addRebra(Rebra r) {
        rebraList.add(r);
    }

    public void addRebra(int first, int finish) {
        addRebra(new Rebra(getGrany(first), getGrany(finish)));
    }

    public Vertex getGrany(int numGrany) { //getters and setters с учетом счета от 1
        return vertexList.get(numGrany - 1);
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
                        if (adjacency(a, c) && adjacency(b, c)&&!c.isLoop())
                            if ((c.startPoint==a.startPoint||c.startPoint==a.finishPoint)&&(c.finishPoint== b.startPoint||c.finishPoint== b.finishPoint)){ //избежание ошибки начало и конец с должны быть от а и b
                                existC = true;
                            }
                        if ((c.startPoint==b.startPoint||c.startPoint==b.finishPoint)&&(c.finishPoint== a.startPoint||c.finishPoint== a.finishPoint)){ //избежание ошибки
                            existC = true;
                        }
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
            for (Vertex gran : vertexList) {
                if (gran.getStepenVertex() != vertexList.get(0).getStepenVertex()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean refleks() { // рефлексивность - все вершины имеют петли
        for (Vertex gran : vertexList) {
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

    public boolean connect(Vertex a, Vertex b) { //наличие пути меж вершинами
        if (a == b) { //если сравниваемые грани совпали, то нет смысла проверять)
            return true;
        }
        boolean[] visited = new boolean[vertexList.size()];
        visited[vertexList.indexOf(a)] = true;  //начальная точка посещена
        for (Rebra r : rebraList) { //идем по ребрам, которые соединены с А и непройдеными вершинами
            if ((r.startPoint == a && !visited[vertexList.indexOf(r.finishPoint)]) || (r.finishPoint == a && !visited[vertexList.indexOf(r.startPoint)])) {
                if (r.startPoint == a && !visited[vertexList.indexOf(r.finishPoint)]) {
                    if (connect(r.finishPoint, b, visited)) { //рекурсия для прохода по всем возможным путям
                        return true;
                    }
                }
                if (r.finishPoint == a && !visited[vertexList.indexOf(r.startPoint)]) {
                    if (connect(r.startPoint, b, visited)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean connect(Vertex a, Vertex b, boolean[] visited) { //наличие пути меж вершинами вспомогательное
        if (a == b) { //если сравниваемые грани совпали, то они соединены
            return true;
        }
        visited[vertexList.indexOf(a)] = true; //начальная точка посещена
        for (Rebra r : rebraList) { //идем по ребрам, которые соединены с А и непройдеными вершинами
            if (r.startPoint == a && !visited[vertexList.indexOf(r.finishPoint)]) {
                if (connect(r.finishPoint, b, visited)) {
                    return true;
                }
            }
            if (r.finishPoint == a && !visited[vertexList.indexOf(r.startPoint)]) {
                if (connect(r.startPoint, b, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean connectivity() {//связность
        for (Vertex gran1 : vertexList) { //обходим пары вершин, чтобы у каждой из них был путь к любой вершине
            for (Vertex gran2 : vertexList) {
                if (!connect(gran1, gran2)) {
                    return false; //если хоть одна пара вершин не имеют общего пути, то граф не связен
                }
            }
        }
        return true;
    }

    public String minimalWay(Vertex A, Vertex B) { //функция поиска минимального пути
        if (!connect(A, B)) {
            return "Вершины не связаны";
        }
        boolean[] visited = new boolean[vertexList.size()]; //Посещенные вершины первоначально false
        visited[vertexList.indexOf(A)] = true;  //начальная точка посещена
        StringBuilder way = new StringBuilder(); //Путь мин до вершин
        int min = -1; //значение минимального пути до вершины
        for (Rebra r : rebraList) { //идем по ребрам, которые соединены с А и непройдеными вершинами
            if ((r.startPoint == A && !visited[vertexList.indexOf(r.finishPoint)]) || (r.finishPoint == A && !visited[vertexList.indexOf(r.startPoint)])) {
                if (r.startPoint == A && !visited[vertexList.indexOf(r.finishPoint)]) {
                    way.append(minimalWay(r.finishPoint, B, visited, min));//рекурсия для прохода по всем возможным путям
                }
                if (r.finishPoint == A && !visited[vertexList.indexOf(r.startPoint)]) {
                    way.append(minimalWay(r.startPoint, B, visited, min));
                }
                min += r.getPrice();
            }
        }
        System.out.println("\nКратчайшие расстояния до вершин:");
        return way.toString();
    }

    public String minimalWay(Vertex A, Vertex B, boolean[] visited, int min) { //функция поиска минимального пути
        StringBuilder result = new StringBuilder();
        if (A == B) { //если сравниваемые грани совпали, то они соединены
            return String.valueOf(vertexList.indexOf(B));
        }
        visited[vertexList.indexOf(A)] = true; //начальная точка посещена
        for (Rebra r : rebraList) { //идем по ребрам, которые соединены с А и непройдеными вершинами
            if (r.startPoint == A && !visited[vertexList.indexOf(r.finishPoint)]) {
                if (r.startPoint == A && !visited[vertexList.indexOf(r.finishPoint)]) {
                    result.append(minimalWay(r.finishPoint, B, visited, min));
                }
            }
            if (r.finishPoint == A && !visited[vertexList.indexOf(r.startPoint)]) {
                if (r.finishPoint == A && !visited[vertexList.indexOf(r.startPoint)]) {
                    result.append(minimalWay(r.startPoint, B, visited, min));
                }
            }
        }
        return result.toString();
    }
    public String changeCountOfGrany(int newCount){
        if (newCount < vertexList.size()) {
            while (newCount!= vertexList.size()){
                ArrayList<Rebra> anconnected = new ArrayList<>();
                for (Rebra r:rebraList){//проверяем ребра, которые могли быть соединены с вершиной
                    anconnected.add(r);
                    if (r.startPoint == getGrany(vertexList.size())) {//проверяем начальную точку ребра
                        if (r.isLoop()){//если было петлей, то удаляем
                            anconnected.remove(r);
                        }
                        else {
                            r.startPoint=r.finishPoint;//иначе делаем петлей
                        }
                    }
                    if (r.finishPoint == getGrany(vertexList.size())) {//проверяем конечную точку
                        r.finishPoint=r.startPoint;//иначе делаем петлей

                    }
                }
                rebraList=anconnected;
                vertexList.remove(vertexList.size()-1);
            }
            return "Количество вершин уменьшено до "+newCount;
        }
        else if (newCount == vertexList.size()){
            return "Количество вершин не изменено. Было указано значение равное предыдущему";
        }
        else {
            while (newCount!= vertexList.size()){
                vertexList.add(new Vertex());
            }
            return "Количество вершин увеличено до "+newCount;
        }
    }

    public boolean rebraStartCheck(Rebra rebra){//проверка первой связи ребра с вершинами графа
        for (Vertex vertex:vertexList) {
            if (vertex==rebra.startPoint)return true;
        }
        return false;
    }
    public boolean rebraFinishCheck(Rebra rebra){//проверка конечной связи ребра с вершинами графа
        for (Vertex vertex:vertexList) {
            if (vertex==rebra.finishPoint)return true;
        }
        return false;
    }
}


