package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Point;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;
import android.util.Log;


import java.util.ArrayList;

public class Node {
    public Point Point;
    public int index;
    public POI_ID specialIndex;
    public ArrayList<EdgesInfo> Edges = new ArrayList<EdgesInfo>();

    public Node(double x, double y, double z, int Index){
        this.index = Index;
        Point p = new Point(x,y,z);
        if(isValidNode(p)) {
            Point = p;
        }
    }

    public Node(double x, double y, double z, int Index, POI_ID id){
        this.index = Index;
        this.specialIndex = id;
        Point p = new Point(x,y,z);
        if(isValidNode(p)) {
            Point = p;
        }
    }

    public Node(Point p,int Index, POI_ID id){
        this.index = Index;
        this.specialIndex = id;
        if(isValidNode(p)){
            Point = p;
        }else{
            String msg = "Node is not in valid bounding: "+p.getX()+" "+p.getY()+" "+p.getZ();
            Log.i("NodeCreation",msg);
        }
    }

    public Node(Point p,int Index){
        this.index = Index;
        if(isValidNode(p)){
            Point = p;
        }else{
            String msg = "Node is not in valid bounding: "+p.getX()+" "+p.getY()+" "+p.getZ();
            Log.i("NodeCreation",msg);
        }
    }

    public boolean isValidNode(Point p){ //problematic af how do i check this bro helpp
        for(BoundingBox b: MapPositionManager.KIZs){
            double[] Position = p.toArray();
            if(b.contains(Position[0],Position[1],Position[2])){
                return true;
            }
        }
        return false;
    }

    public void AddEdgeList(Node dest,double Dist){
        Edges.add(new EdgesInfo(dest,Dist));
    }
    public void AddEdgeList(Node dest){
        double distance = Math.sqrt(Math.pow(Point.getX() - dest.Point.getX(), 2) + Math.pow(Point.getY() - dest.Point.getY(), 2) + Math.pow(Point.getZ() - dest.Point.getZ(), 2));
        Edges.add(new EdgesInfo(dest,distance));
    }
}
