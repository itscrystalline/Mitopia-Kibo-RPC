package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

public class BoundingBox {
    public Point start, end;

    public BoundingBox(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        this.start = new Point(startX, startY, startZ);
        this.end = new Point(endX, endY, endZ);
    }

    public BoundingBox(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public boolean contains(float x, float y, float z) {
        return x >= start.getX() && x <= end.getX() && y >= start.getY() && y <= end.getY() && z >= start.getZ() && z <= end.getZ();
    }

    public double[] size() {
        return new double[]{end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ()};
    }
}
