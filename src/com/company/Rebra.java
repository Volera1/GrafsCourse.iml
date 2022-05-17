package com.company;

public class Rebra {
    protected Grany startPoint;
    protected Grany finishPoint;

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
        if (this.startPoint == this.finishPoint) {
            return true;
        } else {
            return false;
        }
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
    public int getPrice() {
        return Price;
    }
}