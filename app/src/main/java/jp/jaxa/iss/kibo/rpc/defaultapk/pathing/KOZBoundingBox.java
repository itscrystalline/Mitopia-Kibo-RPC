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
}
