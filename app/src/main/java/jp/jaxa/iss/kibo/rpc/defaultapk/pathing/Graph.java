package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;

/**
* Implementation of adjacency list graph data structure
* */
public class Graph {
    private static ArrayList<Node> AL;
    private static final double KOZ_SCALE = 1.5;

    /**
     * Add KOZs to the graph
     */
    public static void Init(){
        ArrayList<Point> InterestPoints = new ArrayList<Point>();
        for(BoundingBox KOZ: MapPositionManager.KOZs){
            InterestPoints.addAll(Arrays.asList(KOZ.scale(KOZ_SCALE).points()));
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
            for (Node value : AL) {
                if (PassesKOZs(node, value)) {
                    node.AddEdgeList(value, 999999999d);
                    value.AddEdgeList(node, 999999999d);
                } else {
                    node.AddEdgeList(value);
                    value.AddEdgeList(node);
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
        for (Node value : AL) {
            if (PassesKOZs(node, value)) {
                node.AddEdgeList(value, 999999999d);
                value.AddEdgeList(node, 999999999d);
            } else {
                node.AddEdgeList(value);
                value.AddEdgeList(node);
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
