package jp.jaxa.iss.kibo.rpc.defaultapk.subsystem;

import android.util.Log;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.EulerAngles;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Point;
import org.opencv.core.Mat;

import java.util.Calendar;
import java.util.Date;

public class CameraManager {
    private Astro bee;
    public CameraManager(Astro bee) {
        this.bee = bee;
    }

    public void takePhoto() {
        Date currentTime = Calendar.getInstance().getTime();
        Mat image = bee.getPhoto();
        bee.getApi().saveMatImage(image, currentTime.toString());
        Log.i("CameraManager", "Photo taken at " + currentTime);
    }
}
