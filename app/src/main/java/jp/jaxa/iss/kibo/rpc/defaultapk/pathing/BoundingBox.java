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

    public boolean contains(double x, double y, double z) {
        return x >= start.getX() && x <= end.getX() && y >= start.getY() && y <= end.getY() && z >= start.getZ() && z <= end.getZ();
    }

    public double[] size() {
        return new double[]{end.getX() - start.getX(), end.getY() - start.getY(), end.getZ() - start.getZ()};
    }

    /**
     * Get the 8 corner points of the bounding box.
     * @return The 8 corner points of the bounding box.
     */
    public Point[] points() {
        return new Point[]{
            new Point(start.getX(), start.getY(), start.getZ()),   //up left back
            new Point(start.getX(), start.getY(), end.getZ()),  //down left back
            new Point(start.getX(), end.getY(), start.getZ()), //up left front
            new Point(start.getX(), end.getY(), end.getZ()), //down left front
            new Point(end.getX(), start.getY(), start.getZ()), //up right back
            new Point(end.getX(), start.getY(), end.getZ()), //down right back
            new Point(end.getX(), end.getY(), start.getZ()), //up right front
            new Point(end.getX(), end.getY(), end.getZ())   //down right front
        };
    }

    /**
     * Scale the bounding box by the given factors, with the center of the bounding box as the origin.
     * @param x The factor to scale the x-axis by.
     * @param y The factor to scale the y-axis by.
     * @param z The factor to scale the z-axis by.
     * @return The scaled bounding box.
     */
    public BoundingBox scale(double x, double y, double z) {
        double[] size = size();
        double[] newSize = new double[]{size[0] * x, size[1] * y, size[2] * z};
        double[] center = new double[]{start.getX() + size[0] / 2, start.getY() + size[1] / 2, start.getZ() + size[2] / 2};
        return new BoundingBox(center[0] - newSize[0] / 2, center[1] - newSize[1] / 2, center[2] - newSize[2] / 2, center[0] + newSize[0] / 2, center[1] + newSize[1] / 2, center[2] + newSize[2] / 2);
    }

    public double[] getCenter(){
        double[] size = size();
        double[] center = new double[]{start.getX() + size[0] / 2, start.getY() + size[1] / 2, start.getZ() + size[2] / 2};
        return new double[]{center[0],center[1],center[2]};
    }

    /**
     * Scale the bounding box by the given factors, with the center of the bounding box as the origin.
     * @param scale The factor to scale the box by.
     */
    public BoundingBox scale(double scale) {
        return scale(scale, scale, scale);
    }
}
