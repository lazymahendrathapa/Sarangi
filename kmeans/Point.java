//package com.myapp;

import java.util.ArrayList;
import java.util.List;

public class Point{

    private List<Double> point;
    private int cluster_id = 0;
    private int dimension; 

    public Point(){
            this.dimension = 0;
            this.point = new ArrayList<Double>();  
    }

    public void setDimension(int dimension){
            this.dimension = dimension;
    }

    public boolean equals(Point p){
            return point.equals(p.getPoint());

    }

    //public Point(int n){
    //        this.dimension = n;
     //       this.point = new ArrayList<double>(this.dimension);
    //}
    
    public void clearPoint(){
            point.clear();
            dimension = 0;
    }

    public void setPoint(double number){
            point.add(number);
            dimension++;
    }

    public List<Double> getPoint(){
            return point;
    }

    public void setClusterID(int n){
            this.cluster_id = n;
    }

    public int getClusterID(){
            return this.cluster_id;
    }

    public int getDimension(){
            return this.dimension;
    }

    public void printPoint(){
            System.out.println(point);
            //System.out.println(this.dimension);
    } 

   // public static void main(String[] args){
    //        Point p = new Point();
     //       p.setPoint(100.000001);
      //      p.setPoint(12.0);
       //     double temp = p.getPoint().get(0) - p.getPoint().get(1);
        //kj    System.out.println(temp);
         //   p.print();
    //}
}



