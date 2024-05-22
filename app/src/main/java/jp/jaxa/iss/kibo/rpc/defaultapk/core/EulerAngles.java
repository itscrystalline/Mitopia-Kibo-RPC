package jp.jaxa.iss.kibo.rpc.defaultapk.core;

import gov.nasa.arc.astrobee.types.Quaternion;

public class EulerAngles {
    double degX, degY, degZ;
    double radX, radY, radZ;

    public EulerAngles(double degX, double degY, double degZ) {
        this.degX = degX; //(degX/360-(int)degX/360)*360; prevent angle overshoot 360 for debug purpose
        this.degY = degY; //(degY/360-(int)degY/360)*360;
        this.degZ = degZ; //(degZ/360-(int)degZ/360)*360;
        radX = Math.toRadians(degX);
        radY = Math.toRadians(degY);
        radZ = Math.toRadians(degZ);
    }

    public static EulerAngles add(EulerAngles first, EulerAngles second) {
        return new EulerAngles(first.degX + second.degX, first.degY + second.degY, first.degZ + second.degZ);
    }

    public static EulerAngles subtract(EulerAngles first, EulerAngles second) {
        return new EulerAngles(first.degX - second.degX, first.degY - second.degY, first.degZ - second.degZ);
    }

    public static EulerAngles multiplyFloat(EulerAngles first, float value) {
        return new EulerAngles(first.degX * value, first.degY * value, first.degZ * value);
    }

    /**
     * Create a EulerAngles object from a quaternion
     *
     * @see <a href="https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles#Quaternion_to_Euler_angles_(in_3-2-1_sequence)_conversion">Wikipedia</a>
     * @param orientation the quaternion
     * @return the EulerAngles object
     */
    public static EulerAngles of(Quaternion orientation) {
        //TODO: might be inaccurate, reimplement if necessary

        double sinr_cosp = 2 * (orientation.getW() * orientation.getX() + orientation.getY() * orientation.getZ());
        double cosr_cosp = 1 - 2 * (orientation.getX() * orientation.getX() + orientation.getY() * orientation.getY());
        double roll = Math.atan2(sinr_cosp, cosr_cosp);

        double sinp = Math.sqrt(1 + 2 * (orientation.getW() * orientation.getY() - orientation.getX() * orientation.getZ()));
        double cosp = Math.sqrt(1 - 2 * (orientation.getW() * orientation.getY() - orientation.getX() * orientation.getZ()));
        double pitch = 2 * Math.atan2(sinp, cosp) - Math.PI / 2;

        // yaw (z-axis rotation)
        double siny_cosp = 2 * (orientation.getW() * orientation.getZ() + orientation.getX() * orientation.getY());
        double cosy_cosp = 1 - 2 * (orientation.getY() * orientation.getY() + orientation.getZ() * orientation.getZ());
        double yaw = Math.atan2(siny_cosp, cosy_cosp);

        return new EulerAngles(Math.toDegrees(roll), Math.toDegrees(pitch), Math.toDegrees(yaw));
    }

    /**
     * Create a quaternion from Euler angles
     *
     * @see <a href="https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles#Euler_angles_(in_3-2-1_sequence)_to_quaternion_conversion">Wikipedia</a>
     * @param roll  (X) the roll angle in radians
     * @param pitch (Y) the pitch angle in radians
     * @param yaw   (Z) the yaw angle in radians
     * @return the quaternion
     */
    public static Quaternion fromRad(double roll, double pitch, double yaw) {
        //TODO: might be inaccurate, reimplement if necessary
        //Refer from https://github.com/mrdoob/three.js/blob/master/src/math/Quaternion.js

        double c1 = Math.cos( roll / 2 );
        double c2 = Math.cos( pitch / 2 );
        double c3 = Math.cos( yaw / 2 );

        double s1 = Math.sin( roll / 2 );
        double s2 = Math.sin( pitch / 2 );
        double s3 = Math.sin( yaw / 2 );

        return new Quaternion(
            s1 * c2 * c3 + c1 * s2 * s3,
            c1 * s2 * c3 - s1 * c2 * s3,
            c1 * c2 * s3 + s1 * s2 * c3,
            c1 * c2 * c3 - s1 * s2 * s3
        );

        /*double cr = Math.cos(roll * 0.5);
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
        );*/
    }

    /**
     * Create a quaternion from Euler angles
     *
     * @param roll  (X) the roll angle in degrees
     * @param pitch (Y) the pitch angle in degrees
     * @param yaw   (Z) the yaw angle in degrees
     * @return the quaternion
     */
    public static Quaternion fromDeg(double roll, double pitch, double yaw) {
        return fromRad(Math.toRadians(roll), Math.toRadians(pitch), Math.toRadians(yaw));
    }

    /**
     * Create a quaternion from a EulerAngles object
     *
     * @param eulerAngles the EulerAngles object
     */
    public static Quaternion from(EulerAngles eulerAngles) {
        return fromRad(eulerAngles.radX, eulerAngles.radY, eulerAngles.radZ);
    }

    public EulerAngles add(EulerAngles second) {
        return add(this, second);
    }

    public EulerAngles subtract(EulerAngles second) {
        return subtract(this, second);
    }

    public EulerAngles multiplyFloat(float value) {
        return multiplyFloat(this, value);
    }

    public Quaternion quaternion() {
        return from(this);
    }

    @Override
    public String toString() {
        return "EulerAngles{" +
          "degX=" + degX +
          ", degY=" + degY +
          ", degZ=" + degZ +
          ", radX=" + radX +
          ", radY=" + radY +
          ", radZ=" + radZ +
          '}';
    }
}
