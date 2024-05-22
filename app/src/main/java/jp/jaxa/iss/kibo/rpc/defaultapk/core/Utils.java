package jp.jaxa.iss.kibo.rpc.defaultapk.core;

import gov.nasa.arc.astrobee.types.Quaternion;

public class Utils {
    /**
     * Create a quaternion from Euler angles
     *
     * @param roll  (X) the roll angle in radians
     * @param pitch (Y) the pitch angle in radians
     * @param yaw   (Z) the yaw angle in radians
     * @return the quaternion
     */
    public static Quaternion eulerAnglesRad(double roll, double pitch, double yaw) {
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);

        return new Quaternion(
          (float) (sr * cp * cy - cr * sp * sy),
          (float) (cr * sp * cy + sr * cp * sy),
          (float) (cr * cp * sy - sr * sp * cy),
          (float) (cr * cp * cy + sr * sp * sy)
        );
    }

    /**
     * Create a quaternion from Euler angles
     *
     * @param roll  (X) the roll angle in degrees
     * @param pitch (Y) the pitch angle in degrees
     * @param yaw   (Z) the yaw angle in degrees
     * @return the quaternion
     */
    public static Quaternion eulerAnglesDeg(double roll, double pitch, double yaw) {
        return eulerAnglesRad(Math.toRadians(roll), Math.toRadians(pitch), Math.toRadians(yaw));
    }
}
