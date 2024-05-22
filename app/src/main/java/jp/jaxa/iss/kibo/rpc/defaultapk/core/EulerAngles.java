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
        return Utils.eulerAnglesRad(radX, radY, radZ);
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
