package com.company;

public class Main {

    public static void main(String[] args) {
        //Form f= new Form();
        Graf firstGraf = new Graf(3);


        firstGraf.addRebra(1,2);
        firstGraf.addRebra(3,2);
        firstGraf.addRebra(3,1);
        firstGraf.addRebra(3,3);
        firstGraf.addRebra(1,1);

        for (Grany grany:firstGraf.granyList) {
            System.out.println(grany.getStepenGrany());
        }
        System.out.println(firstGraf.getCountOfLoops());
        System.out.println(firstGraf.transitive());
        System.out.println(firstGraf.simmetry());
        System.out.println(firstGraf.refleks());
    }
}
