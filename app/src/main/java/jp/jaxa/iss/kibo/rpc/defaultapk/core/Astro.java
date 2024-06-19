package jp.jaxa.iss.kibo.rpc.defaultapk.core;

import android.graphics.Bitmap;
import gov.nasa.arc.astrobee.Kinematics;
import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcApi;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Point;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.CameraManager;
import jp.jaxa.iss.kibo.rpc.defaultapk.subsystem.MapPositionManager;
import org.opencv.core.Mat;

public class Astro {
    public static Astro bee;
    private final KiboRpcApi api;
    public MapPositionManager mapPositionManager;
    public CameraManager cameraManager;

    private Astro(KiboRpcApi api){
        bee = this;

        this.api = api;
        this.mapPositionManager = new MapPositionManager(this);
        this.cameraManager = new CameraManager(this);

        api.startMission();
    }

    public KiboRpcApi getApi(){
        return api;
    }

    public void end(){
        api.reportRoundingCompletion();
        api.notifyRecognitionItem();
    }

    public Kinematics getKinematics(){
        return api.getRobotKinematics();
    }

    public Point getPosition(){
        return Point.of(getKinematics().getPosition());
    }

    public Quaternion getOrientationQuaternion(){
        return getKinematics().getOrientation();
    }
    public EulerAngles getOrientation(){
        return EulerAngles.of(getOrientationQuaternion());
    }

    public static void bee(KiboRpcApi api){ new Astro(api); }

    public void moveTo(Point P, Quaternion Q, boolean debug){
        api.moveTo(P, Q, debug); // temporary
        /*int loopCounter = 0;
        while (!result.hasSucceeded() && loopCounter < 4) {
            result = api.moveTo(P, Q, true);
            ++loopCounter;
        }
        if (!result.hasSucceeded()) {
            throw new IllegalStateException("fail to move to the target point.");
        }*/
    }

    public void rotateTo(Quaternion Q, boolean debug){
        api.moveTo(this.getPosition(), Q, debug);
    }

    public void rotateTo(EulerAngles E, boolean debug){
        api.moveTo(this.getPosition(), E.quaternion(), debug);
    }

    public Mat getPhoto(){
        return api.getMatNavCam();
    }

    public Bitmap getPhotoBitmap(){
        return api.getBitmapNavCam();
    }
}
