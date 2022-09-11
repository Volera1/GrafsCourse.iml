package com.company.GrafLogic;

public class Vertex {
    protected int StepenVertex; //степень вершины (количество ребер)

    public Vertex() {
        StepenVertex = 0;
    }

    public int getStepenVertex() {
        return StepenVertex;
    }
    public void setNullStepenGrany(){
        StepenVertex =0;
    }
}
