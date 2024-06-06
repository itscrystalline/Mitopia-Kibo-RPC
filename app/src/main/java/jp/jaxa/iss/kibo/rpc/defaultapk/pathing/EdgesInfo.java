package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

public class EdgesInfo {
    public double Distance; //source to dest dist
    public Point DesPosition;

    public EdgesInfo(Point desPosition,double weight){
        DesPosition = desPosition;
        Distance = weight;
    }
}
