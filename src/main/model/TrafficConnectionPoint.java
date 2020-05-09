package model;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class TrafficConnectionPoint {
    public TrafficComponent tr;
    public Point2D pt;

    public TrafficConnectionPoint(TrafficComponent tr, Point2D pt) {
        this.tr = tr;
        this.pt = pt;
    }
}
