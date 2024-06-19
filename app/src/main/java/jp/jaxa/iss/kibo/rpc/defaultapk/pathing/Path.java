package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import android.util.Log;
import android.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import gov.nasa.arc.astrobee.Result;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;


public class Path {

    ArrayList<Point> PointSet = new ArrayList<>(); // Could use linked list maybe? Please implement if it actually is better.
    public static ArrayList<Double> debug = new ArrayList<>();

    public void moveByPath(){
        Quaternion tempoQuat = new Quaternion(0f,0f,-0.707f,0.707f);
        for(Point p: PointSet){
            String msg = "targ: " + p.getX() + " " + p.getY() + " " + p.getZ();
            Log.i("Path",msg);
            Astro.bee.moveTo(p,tempoQuat,false);
        }
    }

    public void debugDijkstra(){
        for(int i=0;i<PointSet.size();i++){
            String msg ="targ: " + PointSet.get(i).getX() + " " + PointSet.get(i).getY() + " " + PointSet.get(i).getZ() +" at distance: "+debug.get(i);
            Log.i("DijkstraDebug",msg);
        }
    }
    /**
     * Path finding using dijkstra algorithm
     * will change to A* later probably
     * */
    public static Path dijkstra(Node sourceNode, Node targetNode){
        debug.clear();
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
        ArrayList<Double> distTo = new ArrayList<>();
        ArrayList<Integer> fromNode = new ArrayList<>();
        for(int i = 0;i<Graph.currentIndex;i++){
            if(i == sourceNode.index){
                fromNode.add(null);
                distTo.add(0d);
            }else {
                fromNode.add(sourceNode.index);
                distTo.add(99999999d);
            }
        }
        Node CheckingNode; //This node will NOT be add to the global graph

        Path retPath = new Path();
        DistanceIndex.add(new Pair<>(0d, sourceNode));

        while(!DistanceIndex.isEmpty()) {
            CheckingNode = DistanceIndex.peek().second;
            if(visitedNodeIndex.contains(CheckingNode.index)){
                DistanceIndex.poll();
            }else{
                visitedNodeIndex.add(CheckingNode.index);
                double lastDistance = DistanceIndex.peek().first;
                debug.add(lastDistance);
                for(EdgesInfo EI: CheckingNode.Edges){ //Span CheckingNode
                    double accumulateDistance = (double)Math.round((EI.Distance+lastDistance)*100)/100;
                    if(distTo.get(EI.DestNode.index) > accumulateDistance){
                        fromNode.set(EI.DestNode.index,CheckingNode.index);
                        distTo.set(EI.DestNode.index,accumulateDistance);
                        DistanceIndex.add(new Pair<>(accumulateDistance, EI.DestNode));
                        Log.i("Span",fromNode.get(EI.DestNode.index)+" is from: "+(fromNode.get(EI.DestNode.index)));
                    }
                }
            }
        }
        int curr = targetNode.index;

        while(curr != sourceNode.index){
            Log.i("currentLoopIndex"," = "+curr+", Graph max index: "+Graph.currentIndex);
            retPath.PointSet.add(Graph.getNodeReferences(curr).Point);
            curr = fromNode.get(curr);
        }
        retPath.PointSet.add(Graph.getNodeReferences(sourceNode.index).Point);
        Collections.reverse(retPath.PointSet);
        return retPath;
    }
}
