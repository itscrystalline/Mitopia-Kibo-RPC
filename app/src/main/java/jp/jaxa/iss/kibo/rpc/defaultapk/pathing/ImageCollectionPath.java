package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import android.util.Log;

import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.EulerAngles;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.CameraManager;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;


public class ImageCollectionPath {

    // I found these points by testing in the place
    public static Point[] ScanNode = {
            new Point(10.95d,-9.78d,5.195d),
            new Point(11.05d,-8.875d,4.76d),
            new Point(11.05d,-7.925d,4.76d),
            new Point(11d,-6.8525d,4.945d)
    };

    public static Quaternion[] ScanAt = {
            new Quaternion(0f,0f,-0.707f,0.707f),
            new Quaternion(0f,0.707f,0f,0.707f),
            new Quaternion(0f,0.707f,0f,0.707f),
            new Quaternion(0f,0f,0f,1f)
    };

    public static EulerAngles[] ScanAtE = {
            new EulerAngles(0d,0d,90d),
    };

    public static void Start(){
        Node prevArea = Graph.addNode(Astro.bee.getPosition());
        for(int i =0;i<4;i++){
            Node Area = Graph.addNode(ScanNode[i]);
            Path path = Path.dijkstra(prevArea,Area);
            path.debugDijkstra();
            path.moveByPath(ScanAt[i]);
            prevArea = Area;
            //Astro.bee.SetFlashlight(0.01f);
            Astro.bee.cameraManager.takePhotoBitmap();
            Log.i("ScanPath","AREA: "+(i+1)+" is finished");
        }
    }
}
