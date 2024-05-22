package jp.jaxa.iss.kibo.rpc.defaultapk;

import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;

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
    }

    @Override
    protected void runPlan2(){
    }

    @Override
    protected void runPlan3(){
    }
}