package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

public class EdgesInfo {
    public double Distance; //source to dest dist
    public Node DestNode;

    public EdgesInfo(Node destNode,double weight){
        DestNode = destNode;
        Distance = weight;
    }
}
