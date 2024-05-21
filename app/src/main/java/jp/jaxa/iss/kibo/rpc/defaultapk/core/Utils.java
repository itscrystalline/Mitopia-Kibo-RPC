package jp.jaxa.iss.kibo.rpc.defaultapk.core;

import gov.nasa.arc.astrobee.types.Quaternion;

public class Utils {
    public static Quaternion eulerAngles(float roll, float pitch, float yaw) {
        float cr = (float) Math.cos(roll * 0.5);
        float sr = (float) Math.sin(roll * 0.5);
        float cp = (float) Math.cos(pitch * 0.5);
        float sp = (float) Math.sin(pitch * 0.5);
        float cy = (float) Math.cos(yaw * 0.5);
        float sy = (float) Math.sin(yaw * 0.5);

        return new Quaternion(
          sr * cp * cy - cr * sp * sy,
          cr * sp * cy + sr * cp * sy,
          cr * cp * sy - sr * sp * cy,
          cr * cp * cy + sr * sp * sy
        );
    }
}
