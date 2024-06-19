package jp.jaxa.iss.kibo.rpc.defaultapk.pathing;

public class KOZBoundingBox extends BoundingBox {
    public KOZBoundingBox(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        super(startX, startY, startZ, endX, endY, endZ);
    }
    @Override

    public Point[] points() {
        return new Point[]{
                new Point(start.getX(), start.getY(), start.getZ()),   //up left back
                new Point(start.getX(), start.getY(), end.getZ()),  //down left back
                new Point(start.getX(), end.getY(), start.getZ()), //up left front
                new Point(start.getX(), end.getY(), end.getZ()), //down left front
                new Point(end.getX(), start.getY(), start.getZ()), //up right back
                new Point(end.getX(), start.getY(), end.getZ()), //down right back
                new Point(end.getX(), end.getY(), start.getZ()), //up right front
                new Point(end.getX(), end.getY(), end.getZ()),   //down right front
                new Point((end.getX()-start.getX())/2+start.getX(), start.getY(),start.getZ()),
                new Point((end.getX()-start.getX())/2+start.getX(), end.getY(),start.getZ()),
                new Point((end.getX()-start.getX())/2+start.getX(), start.getY(),end.getZ()),
                new Point((end.getX()-start.getX())/2+start.getX(), end.getY(),end.getZ()),
                new Point(start.getX(), start.getY(),(end.getZ()-start.getZ())/2+start.getZ()),
                new Point(start.getX(), end.getY(),(end.getZ()-start.getZ())/2+start.getZ()),
                new Point(end.getX(), start.getY(),(end.getZ()-start.getZ())/2+start.getZ()),
                new Point(end.getX(), end.getY(),(end.getZ()-start.getZ())/2+start.getZ()),
        };
    }

    public KOZBoundingBox scale(double x, double y, double z){
        double[] size = size();
        double[] newSize = new double[]{size[0] * x, size[1] * y, size[2] * z};
        double[] center = new double[]{start.getX() + size[0] / 2, start.getY() + size[1] / 2, start.getZ() + size[2] / 2};
        return new KOZBoundingBox(center[0] - newSize[0] / 2, center[1] - newSize[1] / 2, center[2] - newSize[2] / 2, center[0] + newSize[0] / 2, center[1] + newSize[1] / 2, center[2] + newSize[2] / 2);
    }
}
