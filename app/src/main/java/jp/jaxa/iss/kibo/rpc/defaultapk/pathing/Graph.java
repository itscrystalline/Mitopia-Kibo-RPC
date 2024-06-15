package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import java.util.ArrayList;
import java.util.Arrays;

import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;

/**
* Implementation of adjacency list graph data structure
* */
public class Graph {
    public static ArrayList<Node> AL = new ArrayList<>();
    public static int currentIndex = 0; // for unique node in graph
    private final static double KOZ_SCALE = 1.2;

    /**
     * Initial function add most of the necessary given coordinate.
     */
    public static void Init(){
        addNode(MapPositionManager.ASTRONAUT,POI_ID.ASTRONAUT);

        ArrayList<Point> KOZs = new ArrayList<>();
        for(BoundingBox KOZ: MapPositionManager.KOZs){
            KOZs.addAll(Arrays.asList(KOZ.scale(KOZ_SCALE).points()));
        }
        addNode(KOZs);
    }
    /**
     * Add an array of point to the global graph. (Intercept of a line between 2 point make the weight = 999999999)
     * @param P array of points
     */

    public static void addNode(ArrayList<Point> P){
        for(Point p: P) {
            addNode(p);
        }
    }
    /**
     * Add point as node to the global graph. (Intercept of a line between 2 point make the weight = 999999999)
     * @param p Point
     * */
    public static void addNode(Point p) {
        Node node = new Node(p, currentIndex);
        if(node.Point==null){return;}
        currentIndex++;
        AL.add(node);
        for (Node value : AL) {
            if(node.index == value.index){continue;}
            if (PassesKOZs(node, value)) {
                node.AddEdgeList(value, 999999999d);
                value.AddEdgeList(node, 999999999d);
            } else {
                node.AddEdgeList(value);
                value.AddEdgeList(node);
            }
        }
    }
    public static void addNode(double[] Pos){
        addNode(new Point(Pos[0],Pos[1],Pos[2]));
    }

    public static void addNode(Point point, POI_ID id){
        Node node = new Node(point,currentIndex,id);
        if(node.Point==null){return;}
        currentIndex++;
        AL.add(node);
        for (Node value : AL) {
            if(node.index == value.index){continue;}
            if (PassesKOZs(node, value)) {
                node.AddEdgeList(value, 999999999d);
                value.AddEdgeList(node, 999999999d);
            } else {
                node.AddEdgeList(value);
                value.AddEdgeList(node);
            }
        }
    }

    /**
     * Give node reference corresponding to index in the global graph
     */

    public static Node getNodeReferences(POI_ID id){
        for(Node node: AL){
            if(node.specialIndex==id){
                return node;
            }
        }
        return null;
    }

    public static Node getNodeReferences(int Index){
        for(Node node: AL){
            if(node.index==Index){
                return node;
            }
        }
        return null;
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
