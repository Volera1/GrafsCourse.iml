package com.company.GrafLogic;

public class Rebra {
    public Grany startPoint;
    public Grany finishPoint;

    public Rebra() { //
        startPoint = new Grany();
        finishPoint = new Grany();
    }

    public Rebra(Grany start, Grany finish) {
        startPoint = start;
        start.StepenGrany += 1; //степени граней увеличиваются при появлении ребра
        finishPoint = finish;
        finish.StepenGrany += 1;
    }

    public boolean isLoop() { //проверка ребра на петлю
        return this.startPoint == this.finishPoint;
    }
    public int getPrice(){
        return 1;
    }
}

class RebraWithPrice extends Rebra { //ребра с длинной или ценой
    int Price;
    public RebraWithPrice(int price) {
        Price=price;
    }
    public RebraWithPrice(){Price=0;}

    public void setPrice(int price) {
        Price = price;
    }
    @Override
    public int getPrice() {
        return Price;
    }
}