package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

import gov.nasa.arc.astrobee.types.Quaternion;
import gov.nasa.arc.astrobee.types.Vec3d;

public class Point extends gov.nasa.arc.astrobee.types.Point {
    private Quaternion orientation;

    public Point(double x, double y, double z) {
        super(x, y, z);
        this.orientation = new Quaternion(0, 0, 0, 1);
    }

    public Point(double x, double y, double z, Quaternion orientation) {
        super(x, y, z);
        this.orientation = orientation;
    }

    public Point(double xP, double yP, double zP, double xO, double yO, double zO, double wO) {
        super(xP, yP, zP);
        this.orientation = new Quaternion((float) xO, (float) yO, (float) zO, (float) wO);
    }

    public static Point of(gov.nasa.arc.astrobee.types.Point point) {
        return new Point(point.getX(), point.getY(), point.getZ());
    }

    public static Point of(Vec3d point) {
        return (Point) point;
    }

    public double distance(gov.nasa.arc.astrobee.types.Point other) {
        return Math.sqrt(Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2) + Math.pow(getZ() - other.getZ(), 2));
    }

    public double[] toArray() {
        return new double[]{getX(), getY(), getZ()};
    }

    @Override
    public String toString() {
        return "Point{" +
          "x=" + getX() +
          ", y=" + getY() +
          ", z=" + getZ() +
          '}';
    }
}
