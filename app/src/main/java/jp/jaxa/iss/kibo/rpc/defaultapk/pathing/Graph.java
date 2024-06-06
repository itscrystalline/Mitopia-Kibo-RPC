package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;

/**
* Implementation of adjacency list graph data structure
* */
public class Graph {
    private static ArrayList<Node> AL;
    private static double KOZ_SCALE = 1.5;

    /**
     * Add KOZs to the graph
     */
    public static void Init(){
        ArrayList<Point> InterestPoints = new ArrayList<Point>();
        for(BoundingBox KOZ: MapPositionManager.KOZs){
            for(Point P: KOZ.scale(KOZ_SCALE).points()) {
                InterestPoints.add(P);
            }
        }
        addNode(InterestPoints);
    }
    public static void addNode(ArrayList<Point> P){
        boolean Intercepted = false;
        for(Point p: P) {
            Node node = new Node(p);
            if(AL.isEmpty()){
                AL.add(node);
            }
            for(int i = 0;i<AL.size();i++) {
                if(PassesKOZs(node,AL.get(i))){
                    node.AddEdgeList(AL.get(i).Point,999999999d);
                    AL.get(i).AddEdgeList(p,999999999d);
                }else{
                    node.AddEdgeList(AL.get(i).Point);
                    AL.get(i).AddEdgeList(p);
                }
            }
        }
    }

    public static void addNode(double[] Pos){
        addNode(new Point(Pos[0],Pos[1],Pos[2]));
    }

    public static void addNode(Node node){
        addNode(node.Point);
    }
    public static void addNode(Point p) {
        Node node = new Node(p);
        AL.add(node);
        for(int i = 0;i<AL.size();i++) {
            if(PassesKOZs(node, AL.get(i))){
                node.AddEdgeList(AL.get(i).Point,999999999d);
                AL.get(i).AddEdgeList(p,999999999d);
            }else {
                node.AddEdgeList(AL.get(i).Point);
                AL.get(i).AddEdgeList(p);
            }
        }
    }

    /** Check whether the straight line path is in bounding box or not
     * @param a Starting point
     * @param b End point
     * @param Intercept Box to check if line ab is intercepted
     * @return boolean value if line is intercepted or not
     */
    public static boolean IsIntercept(BoundingBox Intercept, Point a, Point b){
        //Think of this myself may not be accurate or may perform badly
        double pointDistConst = 0.05;
        double t = 0;
        while(t<=1) {
            double xil = a.getX() + t * (b.getX()-a.getX());
            double yil = a.getY() + t * (b.getY()-a.getY());
            double zil = a.getZ() + t * (b.getZ()-a.getZ());
            if(Intercept.contains(xil,yil,zil)){
                return true;
            }
            t+=pointDistConst;
        }
        return false;
    }

    public static boolean PassesKOZs(Node a,Node b){
        for (BoundingBox B : MapPositionManager.KOZs) { // CHECKING EVERY POINTS IF IT INTERCEPT EVERY BOUNDING BOX, POSSIBLE SHIT PERFORMANCE redo if possible
            if(IsIntercept(B, a.Point, b.Point)){
                 return true;
            }
        }
        return false;
    }


}
