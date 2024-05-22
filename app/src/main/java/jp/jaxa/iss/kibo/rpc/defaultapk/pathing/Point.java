package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

public class Point extends gov.nasa.arc.astrobee.types.Point {

    public Point(double x, double y, double z) {
        super(x, y, z);
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
