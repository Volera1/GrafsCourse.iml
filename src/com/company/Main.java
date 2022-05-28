package com.company;

public class Main {

    public static void main(String[] args) {
        //Form f= new Form();
        Graf firstGraf = new Graf(3);


        firstGraf.addRebra(1,2);
        firstGraf.addRebra(3,2);
        firstGraf.addRebra(3,3);
        firstGraf.addRebra(1,1);

        for (Grany grany:firstGraf.granyList) {
            System.out.println(grany.getStepenGrany());
        }
        System.out.println("Количество петель: "+firstGraf.getCountOfLoops());
        System.out.println("Транзитивность: "+firstGraf.transitive());
        System.out.println("Симметричность: "+firstGraf.simmetry());
        System.out.println("Рефлексивность: "+firstGraf.refleks());
        System.out.println("Путь 1-1: "+firstGraf.connect(firstGraf.getGrany(1), firstGraf.getGrany(1) ));
        System.out.println("Путь 1-2: "+firstGraf.connect(firstGraf.getGrany(1), firstGraf.getGrany(2) ));
        System.out.println("Путь 1-3: "+firstGraf.connect(firstGraf.getGrany(1), firstGraf.getGrany(3) ));
        System.out.println("Связность: "+firstGraf.connectivity());
    }
}
