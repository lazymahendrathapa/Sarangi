//package com.myapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

public class Cluster{

        private List<Point> pointcollection; 
        private Point centroid;
        private int id;

        public Cluster(int id){
                this.id = id;
                this.pointcollection = new ArrayList<Point>();
                this.centroid = new Point();
        }

        public int getID(){
                return id;
        }

        public Point getCentroid(){
                return centroid;
        }

        public boolean matchCentroid(Point oldcentroid){
                return (oldcentroid.equals(centroid));

        }

        public void addPoint(final Point p){ pointcollection.add(p);
        }

        public void clearCluster(){
                pointcollection.clear();
        }

        public List<Point> getPointCollection(){
                List<Point> pointcollectionclone = new ArrayList<Point>(pointcollection);
                return pointcollectionclone; 
        }

        public static double distance(Point point, Point centre){
                int dimension = point.getDimension();
                //Point p1[] = new Point[dimension];
                //Point p2[] = new Point[dimension];
                //p1 = p.toArray(p1);
                //p2 = p.toArray(p2);
                double temp = 0;
                for(int i = 0; i < dimension; i++)
                        temp += Math.pow((point.getPoint().get(i)-centre.getPoint().get(i)),2); 
                return Math.sqrt(temp);
        }

        public void initializeCentroid(){
                Random random = new Random(); //random selection of points from the collection
                centroid = pointcollection.get(random.nextInt(pointcollection.size()));
        }
         
        public void calculateCentroid(){
                centroid.clearPoint();
                int dimension = pointcollection.get(0).getDimension();
                for(int i = 0; i < dimension; i++){
                        double temp = 0;
                        for(Point p : pointcollection){
                                temp += p.getPoint().get(i); 
                        }
                        centroid.setPoint(temp/pointcollection.size()); 
                }
        }

        public void printCluster(){
                System.out.println("Printing Cluster " + id + ":");
                for(Point p : pointcollection)
                        p.printPoint();
                System.out.println("Centroid of Cluster "+ id + ":");
                centroid.printPoint();
        }

        public Point getPoints(int i){
                return pointcollection.get(i);
        }

        public void shuffleCluster(){
                Collections.shuffle(pointcollection);
        }


        public static void main(String[] args){
                Cluster sack = new Cluster(0);
                Point p1 = new Point();
                Point p2 = new Point();
                p1.setPoint(2);
                p1.setPoint(4);
                p2.setPoint(5);
                p2.setPoint(1);
                p2.setDimension(4);
                p1.clearPoint();
                p1.setPoint(10);
                p1.printPoint();
                Point t = new Point();
                t = p1;
                t.setDimension(5);
                System.out.println(p1.getDimension());
                sack.addPoint(p1);
                sack.addPoint(p2);
                sack.printCluster();
                p1.clearPoint();
                sack.printCluster();
                //sack.calculateCentroid();
                Point temp = new Point();
                temp.setPoint(3.5);
                temp.setPoint(2.5);



                

                

        }

}
