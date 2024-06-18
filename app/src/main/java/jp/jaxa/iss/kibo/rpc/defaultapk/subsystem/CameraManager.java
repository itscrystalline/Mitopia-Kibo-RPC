package jp.jaxa.iss.kibo.rpc.defaultapk.subsystem;

import android.graphics.Bitmap;
import android.util.Log;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.EulerAngles;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Point;
import org.opencv.core.Mat;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class CameraManager {
    private final Astro bee;
    public CameraManager(Astro bee) {
        this.bee = bee;
    }

    public void takePhoto() {
        Date currentTime = Calendar.getInstance().getTime();
        Mat image = bee.getPhoto();
        String name = "mat " +
          Arrays.deepToString(bee.getApi().getNavCamIntrinsics()) + " " +
          currentTime.toString();

        bee.getApi().saveMatImage(image, name);
        Log.i("CameraManager", "Photo taken at " + currentTime);
    }

    public void takePhotoBitmap() {
        Date currentTime = Calendar.getInstance().getTime();
        Bitmap image = bee.getPhotoBitmap();
        String name = "bmp " +
          Arrays.deepToString(bee.getApi().getNavCamIntrinsics()) + " " +
          currentTime.toString();

        bee.getApi().saveBitmapImage(image, name);
        Log.i("CameraManager", "Photo taken at " + currentTime);
    }
}
