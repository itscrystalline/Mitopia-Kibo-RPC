package jp.jaxa.iss.kibo.rpc.defaultapk.subsystem;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import gov.nasa.arc.astrobee.types.Quaternion;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.Astro;
import jp.jaxa.iss.kibo.rpc.defaultapk.core.EulerAngles;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.BoundingBox;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.KOZBoundingBox;
import jp.jaxa.iss.kibo.rpc.defaultapk.pathing.Point;

public class MapPositionManager {
    public static Point START = new Point(9.815, -9.806, 4.293, 1, 0, 0, 0);
    public static Point ASTRONAUT = new Point(11.143, -6.7607, 4.9654, 0, 0, 0.707, 0.707);

    public static KOZBoundingBox[] KOZs = new KOZBoundingBox[]{
      new KOZBoundingBox(10.87, -9.5, 4.27, 11.6, -9.45, 4.97),
      new KOZBoundingBox(10.25, -9.5, 4.97, 10.87, -9.45, 5.62),
      new KOZBoundingBox(10.87, -8.5, 4.97, 11.6, -8.45, 5.62),
      new KOZBoundingBox(10.25, -8.5, 4.27, 10.7, -8.45, 4.97),
      new KOZBoundingBox(10.87, -7.4, 4.27, 11.6, -7.35, 4.97),
      new KOZBoundingBox(10.25, -7.4, 4.97, 10.87, -7.35, 5.62)
    };
    public static BoundingBox[] KIZs = new BoundingBox[]{
      new BoundingBox(10.3, -10.2, 4.32, 11.55, -6.0, 5.57),
      new BoundingBox(9.5, -10.5, 4.02, 10.5, -9.6, 4.8)
    };
    public static BoundingBox[] AREAS = new BoundingBox[]{
      new BoundingBox(10.42, -10.58, 4.82, 11.48, -10.58, 5.57),
      new BoundingBox(10.3, -9.25, 3.76203, 11.55, -8.5, 3.76203),
      new BoundingBox(10.3, -8.4, 3.76093, 11.55, -7.45, 3.76093),
      new BoundingBox(9.866984, -7.34, 4.32, 9.866984, -6.365, 5.57),
    };


    private final Astro bee;

    private List<BoundingBox> keepInZones;
    private List<KOZBoundingBox> keepOutZones;
    private List<Point> waypoints;
    private Point position;
    private EulerAngles orientation;

    public MapPositionManager(Astro bee) {
        this.bee = bee;
        this.position = bee.getPosition();
        this.orientation = bee.getOrientation();
    }

    public void addKeepInZone(BoundingBox zone) {
        keepInZones.add(zone);
    }

    public void addKeepInZones(BoundingBox[] zones) {
        keepInZones.addAll(Arrays.asList(zones));
    }

    public void addKeepOutZone(KOZBoundingBox zone) {
        keepOutZones.add(zone);
        // add waypoints around the zone
        BoundingBox boundingBox = zone.scale(1.2);
        waypoints.addAll(Arrays.asList(boundingBox.points()));
    }

    public void addKeepOutZones(KOZBoundingBox[] zones) {
        for (KOZBoundingBox zone : zones) {
            addKeepOutZone(zone);
        }
    }

    public void addWaypoint(Point waypoint) {
        waypoints.add(waypoint);
    }

    public void addWaypoints(Point[] waypoints) {
        this.waypoints.addAll(Arrays.asList(waypoints));
    }

    public void addWaypoints(Collection<? extends Point> waypoints) {
        this.waypoints.addAll(waypoints);
    }
}
