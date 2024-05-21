package jp.jaxa.iss.kibo.rpc.defaultapk.core;

import gov.nasa.arc.astrobee.types.Quaternion;

public class Utils {
    /**
     * Create a quaternion from Euler angles
     * @param roll (X) the roll angle in radians
     * @param pitch (Y) the pitch angle in radians
     * @param yaw (Z) the yaw angle in radians
     * @return the quaternion
     */
    public static Quaternion eulerAnglesRad(float roll, float pitch, float yaw) {
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

    /**
     * Create a quaternion from Euler angles
     * @param roll (X) the roll angle in degrees
     * @param pitch (Y) the pitch angle in degrees
     * @param yaw (Z) the yaw angle in degrees
     * @return the quaternion
     */
    public static Quaternion eulerAnglesDeg(float roll, float pitch, float yaw) {
        return eulerAnglesRad((float) Math.toRadians(roll), (float) Math.toRadians(pitch), (float) Math.toRadians(yaw));
    }
}
