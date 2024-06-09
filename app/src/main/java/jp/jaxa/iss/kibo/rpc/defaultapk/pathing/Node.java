package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Point;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;

import java.util.ArrayList;

public class Node {
    public Point Point;
    public ArrayList<EdgesInfo>  Edges;

    public Node(double x, double y, double z){
        Point p = new Point(x,y,z);
        if(isValidNode(p)) {
            Point = p;
        }
    }

    public Node(Point p){
        if(isValidNode(p)){
            Point = p;
        }
    }

    public boolean isValidNode(Point p){
        int cnt = 0;
        for(BoundingBox b: MapPositionManager.KIZs){
            double[] Position = p.toArray();
            if(!(b.contains(Position[0],Position[1],Position[2]))){
                cnt++;
            }
        }
        if(cnt==2){
            return false;
        }else{
            return true;
        }
    }

    public void AddEdgeList(Node dest,double Dist){
        Edges.add(new EdgesInfo(dest,Dist));
    }
    public void AddEdgeList(Node dest){
        double distance = Math.sqrt(Math.pow(Point.getX() - dest.Point.getX(), 2) + Math.pow(Point.getY() - dest.Point.getY(), 2) + Math.pow(Point.getZ() - dest.Point.getZ(), 2));
        Edges.add(new EdgesInfo(dest,distance));
    }
}
