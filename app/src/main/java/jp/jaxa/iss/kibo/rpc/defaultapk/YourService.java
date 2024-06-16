package jp.jaxa.iss.kibo.rpc.defaultapk;

import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;
import android.util.Log;

import java.util.ArrayList;

import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.POI_ID;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Path;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Point;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Graph;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Node;
import gov.nasa.arc.astrobee.Result;


/**
 * Class meant to handle commands from the Ground Data System and execute them in Astrobee.
 */

public class YourService extends KiboRpcService {
    @Override
    protected void runPlan1(){
        Astro.bee(api);

        Astro.bee.mapPositionManager.addKeepInZones(MapPositionManager.KIZs);
        Astro.bee.mapPositionManager.addKeepOutZones(MapPositionManager.KOZs);

        Astro.bee.mapPositionManager.addWaypoint(MapPositionManager.START);
        Astro.bee.mapPositionManager.addWaypoint(MapPositionManager.ASTRONAUT);

        Graph.Init();
        Node astron = Graph.getNodeReferences(POI_ID.ASTRONAUT);
        int saveindex = Graph.currentIndex;
        Graph.addNode(new Point(10.9d, -9.92284d, 5.195d));
        Node toMid = Graph.getNodeReferences(saveindex);
        Path ToAstron = Path.dijkstra(toMid,astron);
        ToAstron.moveByPath();
        //move to astronaut (pathfind system is still not working)
        Astro.bee.cameraManager.takePhotoBitmap();

        Astro.bee.end();
    }

    @Override
    protected void runPlan2(){
    }

    @Override
    protected void runPlan3(){
    }
}