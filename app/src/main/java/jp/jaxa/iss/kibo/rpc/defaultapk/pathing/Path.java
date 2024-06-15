package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import gov.nasa.arc.astrobee.Result;


public class Path {

    ArrayList<Point> PointSet = new ArrayList<>(); // Could use linked list maybe? Please implement if it actually is better.

    public void moveByPath(){
        Quaternion tempoQuat = new Quaternion(0f,0f,-0.707f,0.707f);
        for(Point p: PointSet){
            String msg = "targ: " + p.getX() + " " + p.getY() + " " + p.getZ();
            Log.i("Path",msg);
            Astro.bee.moveTo(p,tempoQuat);
        }
    }

    public void debugDijkstra(){
        for(Point p: PointSet){
            String msg ="targ: " + p.getX() + " " + p.getY() + " " + p.getZ();
            Log.i("DijkstraDebug",msg);
        }
    }
    /**
     * Path finding using dijkstra algorithm
     * will change to A* later probably
     * */
    public static Path dijkstra(Node sourceNode, Node targetNode){
        ArrayList<Integer> visitedNodeIndex = new ArrayList<>();
        PriorityQueue<Pair<Double, Node>> DistanceIndex = new PriorityQueue<>(new Comparator<Pair<Double, Node>>() { //PriorityQueue isn't documented since Java 8
            @Override
            public int compare(Pair<Double, Node> doubleNodePair, Pair<Double, Node> t1) {
                if(doubleNodePair.first<t1.first){
                    return -1;
                }else if(doubleNodePair.first.equals(t1.first)){
                    return 0;
                }
                return 1;
            }
        });
        Node CheckingNode; //This node will NOT be add to the global graph.

        visitedNodeIndex.add(sourceNode.index);
        Path retPath = new Path();
        retPath.PointSet.add(sourceNode.Point);
        DistanceIndex.add(new Pair<>(0d, sourceNode));
        for(EdgesInfo EI: sourceNode.Edges){
            DistanceIndex.add(new Pair<>(EI.Distance, EI.DestNode));
        }
        while(!DistanceIndex.isEmpty()) {
            CheckingNode = DistanceIndex.peek().second;
            if(visitedNodeIndex.contains(CheckingNode.index)){
                DistanceIndex.poll();
            }else{
                visitedNodeIndex.add(CheckingNode.index);
                double lastDistance = DistanceIndex.peek().first;
                retPath.PointSet.add(CheckingNode.Point);
                if(CheckingNode.index == targetNode.index){
                    break;
                }
                for(EdgesInfo EI: CheckingNode.Edges){
                    if(!visitedNodeIndex.contains(EI.DestNode.index)){
                        DistanceIndex.add(new Pair<>(EI.Distance+lastDistance, EI.DestNode));
                    }
                }
            }
        }

        return retPath;
    }
}
