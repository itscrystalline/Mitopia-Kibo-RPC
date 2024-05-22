package jp.jaxa.iss.kibo.rpc.defaultapk.core;

import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.types.Point;
import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcApi;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;

public class Astro {
    public static Astro bee;
    private final KiboRpcApi api;
    public MapPositionManager mapPositionManager;

    private Astro(KiboRpcApi api){
        bee = this;

        this.api = api;
        this.mapPositionManager = new MapPositionManager(this);

        api.startMission();
    }

    public Kinematics getKinematics(){
        return api.getRobotKinematics();
    }

    public Point getPosition(){
        return getKinematics().getPosition();
    }

    public Quaternion getOrientation(){
        return getKinematics().getOrientation();
    }

    public static void bee(KiboRpcApi api){
        new Astro(api);
    }
}
