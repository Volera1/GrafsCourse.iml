package com.company;
import java.util.*;

public class Graf {
    protected ArrayList<Grany> granyList;
    protected ArrayList<Rebra> rebraList;
    Graf(){
        granyList = new ArrayList<>();
        rebraList = new ArrayList<>();
    }
    Graf(int countOfGraney, int countOfReber){ //четко ограниченный граф
        granyList = new ArrayList<>(countOfGraney);
        for (int i=0;i<countOfGraney;i++){
            granyList.add(new Grany());
        }
        rebraList = new ArrayList<>(countOfReber);
    }
    Graf(int countOfGraney){ //только грани заданны
        granyList = new ArrayList<>(countOfGraney);
        for (int i=0;i<countOfGraney;i++){
            granyList.add(new Grany());
        }
        rebraList = new ArrayList<>();
    }
    public void deleteRebra(Rebra deleted) //удаление ребра, учитывая степени граней
    {
        deleted.startPoint.StepenGrany-=1;
        deleted.finishPoint.StepenGrany-=1;
        rebraList.remove(deleted);
    }
    public void deleteRebra(int numRebra){ //удаление ребра по номеру, учитывая степени граней
        rebraList.get(numRebra-1).startPoint.StepenGrany=-1;
        rebraList.get(numRebra-1).finishPoint.StepenGrany=-1;
        rebraList.remove(numRebra-1);
    }
    public void deleteAllRebra(){
        for (Grany grany:this.granyList) {
            grany.setNullStepenGrany();
        }
        rebraList.clear();
    }; //удаление всех ребер

    public void addGrany(Grany g){ //добавляторы
        granyList.add(g);
    }
    public void addRebra(Rebra r){
        rebraList.add(r);
    }
    public void addRebra(int first, int finish){
        addRebra(new Rebra(getGrany(first),getGrany(finish)));
    }

    public Grany getGrany(int numGrany){ //getters and setters с учетом счета от 1
        return granyList.get(numGrany-1);
    }
    public Rebra getRebra(int numRebra){
        return rebraList.get(numRebra-1);
    }

    //отсюда обновить методы в иллюстрации
    public int getCountOfLoops(){ //сколько петель
        int loops = 0; //количество петель
        for (Rebra rebra:rebraList) {
            if (rebra.isLoop()){
                loops+=1;
            }
        }
        return loops;
    }
    public boolean transitive(){ //проверка на транзитивность (если вершины 1 и 2, 2 и 3 соединены, то и 1 и 3 соеденены)
        //изначально транзитивный, если даже нет ребер, тк условие транзитивноти соблюдено
        for (Rebra a:rebraList) { //первая связь для проверки
            for (Rebra b:rebraList) { //вторая связь для проверки
                if (a != b && !(a.isLoop() && b.isLoop()) && ((a.startPoint == b.startPoint) || (a.finishPoint == b.startPoint))) { //отобрали то, что стоит проверять
                    boolean existC=false;
                    for (Rebra c : rebraList) { //связь, существование которой проверяется
                        if ((a.startPoint == b.startPoint) && (c.startPoint==a.finishPoint || c.startPoint== b.finishPoint) &&(c.finishPoint==a.finishPoint || c.finishPoint==b.finishPoint) && !c.isLoop()) {
                            existC=true;
                        }
                        if ((a.finishPoint == b.startPoint) && (c.startPoint==a.startPoint || c.startPoint== b.finishPoint) &&(c.finishPoint==a.startPoint || c.finishPoint==b.finishPoint) && !c.isLoop()) {
                            existC=true;
                        }
                    }
                    if (existC==false){
                        System.out.println(rebraList.indexOf(a));
                        System.out.println(rebraList.indexOf(b));
                        return false; //не нашли подходящее ребро
                    }
                }
            }
        }
        return true;
    }

    public boolean simmetry(){//симметричный граф - транзитивный, без петель, с единой степенью граней
        if(transitive()&&(getCountOfLoops()==0)){
            for (Grany gran: granyList) {
                if(gran.StepenGrany!=granyList.get(0).getStepenGrany()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean refleks(){ // рефлексивность - все вершины имеют петли
        for (Grany gran: granyList){
            boolean exist = false;
            for (Rebra rebro: rebraList) {
                if (rebro.isLoop() && rebro.startPoint==gran){
                    exist=true;
                    break;
                }
            }
            if (!exist){
            return false;}
        }
        return true;
    }
}


