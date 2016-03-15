//package kmeans;

import java.util.concurrent.TimeUnit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

class Kmeans{
        //private final int clusters_num = 4;
        private Cluster classic, rock, jazz, pop; 
        private Cluster container;

        public Kmeans(){
                classic = new Cluster(1);
                rock = new Cluster(2);
                jazz = new Cluster(3);
                pop = new Cluster(4);
                container = new Cluster(0); 
        }


        public void accumulateData()throws FileNotFoundException,IOException{
                try{
                        FileReader file = new FileReader("kmeans.txt");
                        double pointholder = 0;
                        Scanner filescanner = new Scanner(file);
                        while(filescanner.hasNextLine()){
                                String line = filescanner.nextLine();
                                Scanner linescanner = new Scanner(line);
                                if(!line.equals("")){
                                        Point temppoint = new Point();
                                        while(linescanner.hasNextDouble()){
                                                pointholder = linescanner.nextDouble(); 
                                                temppoint.setPoint(pointholder);
                                        }
                                        container.addPoint(temppoint);
                                        //temppoint.clearPoint();
                                        container.printCluster();
                                }
                                linescanner.close();
                                System.out.println("-----------");
                        }
                        filescanner.close();
                        file.close();
                }
                catch(FileNotFoundException e){
                        System.out.println(e.getMessage());
                        throw new FileNotFoundException("data file not found");
                }
                catch(IOException e){
                        System.out.println(e);
                }
        }  


        public void initializeCentroid(){
                Random  r = new Random();
                Point p = new Point(); 
                boolean samecentroid = true;
                int index;
                while(true){
                        List<Point> pointcollectionclone = new ArrayList<Point> (container.getPointCollection());
                        index = r.nextInt(pointcollectionclone.size());
                        p = pointcollectionclone.get(index);
                        classic.addPoint(p);
                        pointcollectionclone.remove(index);
                        Collections.shuffle(pointcollectionclone);
                        index = r.nextInt(pointcollectionclone.size());
                        p = pointcollectionclone.get(index);
                        jazz.addPoint(p);
                        pointcollectionclone.remove(index);
                        Collections.shuffle(pointcollectionclone);
                        index = r.nextInt(pointcollectionclone.size());
                        p = pointcollectionclone.get(index);
                        rock.addPoint(p);
                        pointcollectionclone.remove(index);
                        Collections.shuffle(pointcollectionclone);
                        index = r.nextInt(pointcollectionclone.size());
                        p = pointcollectionclone.get(index);
                        pop.addPoint(p);
                        pointcollectionclone.remove(index);
                        classic.calculateCentroid();
                        rock.calculateCentroid();
                        jazz.calculateCentroid();
                        pop.calculateCentroid();
                        samecentroid = classic.matchCentroid(rock.getCentroid()) | classic.matchCentroid(jazz.getCentroid()) | classic.matchCentroid(pop.getCentroid()) | rock.matchCentroid(jazz.getCentroid()) | rock.matchCentroid(pop.getCentroid()) | jazz.matchCentroid(pop.getCentroid());
                        if(!samecentroid)
                                break;
                        classic.clearCluster(); 
                        rock.clearCluster();
                        pop.clearCluster();
                        jazz.clearCluster();


                }
                container.printCluster();


        }

        public void runCalculation() throws InterruptedException{
                boolean nochange = false;
                double finaldistance = 0;
                //List<Point> allpoints = new ArrayList<Point>(); 
                //allpoints = container.getPointCollection();
                int tempid;
                double clusterpointdistance = 0;
                Point classicoldcentroid = new Point();
                Point rockoldcentroid = new Point();
                Point jazzoldcentroid = new Point();
                Point popoldcentroid = new Point();

                while(!nochange){
                        classic.clearCluster();
                        rock.clearCluster();
                        pop.clearCluster();
                        jazz.clearCluster();
                        classic.printCluster();
                        rock.printCluster();
                        pop.printCluster();
                        jazz.printCluster();
                        for(Point p : container.getPointCollection()){
                        finaldistance = Cluster.distance(p,classic.getCentroid());
                        tempid=1;
                        clusterpointdistance = Cluster.distance(p,rock.getCentroid());
                        if(finaldistance > clusterpointdistance){
                                tempid = 2;
                                finaldistance = clusterpointdistance; 
                        }
                        clusterpointdistance = Cluster.distance(p,jazz.getCentroid()); 
                        if(finaldistance > clusterpointdistance){
                                tempid = 3;
                                finaldistance = clusterpointdistance;
                        }
                        clusterpointdistance = Cluster.distance(p,pop.getCentroid());
                        if(finaldistance > clusterpointdistance){
                                tempid = 4;
                                finaldistance = clusterpointdistance;
                        }
                        switch(tempid){
                                case 1:
                                classic.addPoint(p);
                                break;

                                case 2:
                                rock.addPoint(p);
                                break;

                                case 3:
                                jazz.addPoint(p);
                                break;

                                case 4:
                                pop.addPoint(p);
                                break;

                                default:
                                System.out.println("Error assigning to clusters");
                        }
                        }
                        //TimeUnit.SECONDS.sleep(10);
                        System.out.println(finaldistance);
                        classicoldcentroid = classic.getCentroid();
                        jazzoldcentroid = jazz.getCentroid();
                        rockoldcentroid = rock.getCentroid();
                        popoldcentroid = pop.getCentroid();
                        classic.calculateCentroid();
                        jazz.calculateCentroid();
                        pop.calculateCentroid();
                        rock.calculateCentroid();
                        nochange = classic.matchCentroid(classicoldcentroid) & jazz.matchCentroid(jazzoldcentroid) & rock.matchCentroid(rockoldcentroid) & pop.matchCentroid(popoldcentroid);
                        System.out.println("printing classic");
                       classic.printCluster(); 
                       System.out.println("printing rock");
                       rock.printCluster();
                       System.out.println("printing jazz");
                       jazz.printCluster();
                       System.out.println("printing pop");
                       pop.printCluster();
                }
                
       }

        public static void main(String[] args){
                Kmeans classifier = new Kmeans();
                try{
                        classifier.accumulateData();
                        classifier.initializeCentroid();
                        classifier.runCalculation();
                        
                        
                }
                catch (FileNotFoundException e){
                        System.out.println("Caught " + e);
                }
                catch (IOException e){
                        System.out.println("Caught " + e);
                }
                catch (InterruptedException e){
                        System.out.println("Caught" + e);
                }
        
        }




}
