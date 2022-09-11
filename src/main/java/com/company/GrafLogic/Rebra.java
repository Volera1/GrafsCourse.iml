package com.company.GrafLogic;

public class Rebra {
    public Vertex startPoint;
    public Vertex finishPoint;

    public Rebra() { //
        startPoint = new Vertex();
        finishPoint = new Vertex();
    }

    public Rebra(Vertex start, Vertex finish) {
        startPoint = start;
        start.StepenVertex += 1; //степени граней увеличиваются при появлении ребра
        finishPoint = finish;
        finish.StepenVertex += 1;
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