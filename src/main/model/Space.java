package model;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Space implements SpaceFunction, TrafficComponent {
    String floor;
    String name;
    int posX;
    int posY;
    protected boolean selected;
    int width;
    int height;
    String attribute;
    protected ArrayList<SpaceList> spLL;
    public ArrayList<TrafficComponent> connections;
    protected HashMap<TrafficComponent, TrafficConnectionPoint> connectionPts;
    protected Color selColor = Color.YELLOW;


    public Space(String nm, String f, Point topLeft) {
        this(nm, topLeft.x, topLeft.y, 0, 0, f);
    }

    public Space(String nm, int x, int y, int w, int h, String f) {
        this.name = nm;
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
        this.floor = f;
        this.spLL = new ArrayList<>();
        connections = new ArrayList<>();
        connectionPts = new HashMap<>();
    }

    public Space(String nm, int w, int h, String f) {
        this.name = nm;
        this.width = w;
        this.height = h;
        this.floor = f;
        this.spLL = new ArrayList<>();
        connections = new ArrayList<>();
        connectionPts = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: set the name
    public void setName(String nm) {
        this.name = nm;
    }

    // REQUIRES: w > 0
    // MODIFIES: this
    // EFFECTS: set the width
    public void setWidth(int w) {
        this.width = w;
    }

    // REQUIRES: h > 0
    // MODIFIES: this
    // EFFECTS: set the height
    public void setHeight(int h) {
        this.height = h;
    }

    // EFFECTS: return the isYard value
    public String getAttribute() {
        return this.attribute;
    }

    // EFFECTS: return the width
    public int getWidth() {
        return  this.width;
    }

    // EFFECTS: return the height
    public int getHeight() {
        return this.height;
    }

    // EFFECTS: return the area
    public int getArea() {
        return width * height;
    }

    // EFFECTS: return the building area
    public abstract int getBuildingArea();

    // EFFECTS: return the name
    public String getName() {
        return this.name;
    }

    // EFFECTS: get the floor of the space
    public String getFloor() {
        return floor;
    }

    //EFFECTS: add one spaceList to the spLL
    public void addSpaceList(SpaceList spL) throws CoverageRatioException, PlotRatioException {
        if (!spLL.contains(spL)) {
            spLL.add(spL);
            spL.addSpace(this);
        }
    }

    //EFFECTS: remove one spaceList from the spLL
    public void removeSpaceList(SpaceList spL) {
        if (spLL.contains(spL)) {
            spLL.remove(spL);
            spL.removeSpace(this);
        }
    }

    // EFFECTS: return true iff the given x value is within the bounds of the Shape
    public boolean containsX(int x) {
        return (this.posX <= x) && (x <= this.posX + width);
    }

    // EFFECTS: return true iff the given y value is within the bounds of the Shape
    public boolean containsY(int y) {
        return (this.posY <= y) && (y <= this.posY + height);
    }

    // EFFECTS: return true if the given Point (x,y) is contained within the bounds of this Shape
    public boolean contains(Point point) {
        int pointX = point.x;
        int pointY = point.y;
        return containsX(pointX) && containsY(pointY);
    }

    // REQUIRES: the x,y coordinates of the Point are larger than the x,y coordinates of the space
    // MODIFIES: this
    // EFFECTS:  sets the bottom right corner of this space to the given Point
    public void setBounds(Point bottomRight) {
        width  = bottomRight.x - posX;
        height = bottomRight.y - posY;
    }

    // EFFECTS: draws this space
    public abstract void draw(Graphics g);

    // EFFECTS: fill the rectangle
    protected void fillGraphics(Graphics g) {
        System.out.println();
        g.fillRect(posX, posY, width, height);
    }

    //EFFECTS: draws the shape
    protected void drawGraphics(Graphics g) {
        g.drawRect(posX, posY, width, height);
    }

    // MODIFIES: this
    // EFFECTS:  adds dx to the x coordinate, and dy to the y coordinate.
    public void move(int dx, int dy) {
        posX += dx;
        posY += dy;
    }

    // MODIFIES: this
    // EFFECTS:  unselects this space, stops playing associated sound
    public void unselect() {
        if (selected) {
            selected = false;
        }
    }

    // MODIFIES: this
    // EFFECTS：select this space
    public void select() {
        if (!selected) {
            selected = true;
        }
    }

    //EFFECTS: return the value to indicate two spaces equal or not
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Space space = (Space) o;
        return floor.equals(space.getFloor())
                && name.equals(space.getName())
                && this.getAttribute().equals(space.getAttribute());
    }

    //EFFECTS: return the hashcode
    @Override
    public int hashCode() {
        int re;
        re = this.floor.hashCode();
        re = re * 31 + this.name.hashCode();
        re = re * 31 + this.getAttribute().hashCode();
        return re;
    }

    //MODIFIES: this
    //EFFECTS: add one more traffic connection
    public void addTrafficConnection(TrafficComponent tr) {
        if (!connections.contains(tr)) {
            connections.add(tr);
            Rectangle2D rec = ((Space)tr).getRectangle().createIntersection(this.getRectangle());
            double x = rec.getCenterX();
            double y = rec.getCenterY();
            TrafficConnectionPoint spt = new TrafficConnectionPoint(tr, new Point2D.Double(x,y));
            connectionPts.put(tr, spt);
            tr.addTrafficConnection(this);
            System.out.println("add");
        }
    }

    //MODIFIES: this
    //EFFECTS: remove one traffic connection
    public void removeTrafficConnection(TrafficComponent tr) {
        if (connections.contains(tr)) {
            connections.remove(tr);
            connectionPts.remove(tr);
            tr.removeTrafficConnection(this);
        }
    }

    //MODIFIES: spaceList
    //EFFECTS: search place in one space
    public void searchOneSpace(TrafficComponent dest, ArrayList<TrafficConnectionPoint> trace) {
        if (this.equals(dest)) {
            Space sp = (Space) trace.get(trace.size() - 1).tr;
            TrafficConnectionPoint tp = sp.connectionPts.get((TrafficComponent) this);
            ArrayList<TrafficConnectionPoint> trace2 = new ArrayList<>();
            trace2.addAll(trace);
            trace2.add(tp);
            double length = 0;
            System.out.println(trace2.size());
            ArrayList<Line2D>  trace3 = new ArrayList<>();
            for (int i = 0; i < trace2.size() - 1; i++) {
                Line2D line = new Line2D.Double(trace2.get(i).pt, trace2.get(i + 1).pt);
                length += getLength(line);
                trace3.add(line);
            }
            System.out.println(length);
            this.spLL.get(0).twoPointRe.records.add(new SinglePath(length, trace3));
        }
    }

    //EFFECTS：return the length of one line
    public Double getLength(Line2D line) {
        double x1 = line.getX1();
        double y1 = line.getY1();
        double x2 = line.getX2();
        double y2 = line.getY2();
        return Math.sqrt(Math.pow(x2 - x1,2) + Math.pow(y2 - y1,2));
    }

    //EFFECTS: search the trace from this space to dest
    public void searchTrace(TrafficComponent dest) {
        ArrayList trace = new ArrayList();
        searchOneSpace(dest, trace);
        for (TrafficComponent tr: connections) {
            Space sp = (Space) tr;
            ArrayList trace2 = new ArrayList();
            ArrayList trs2 = new ArrayList();
            if (sp.getAttribute().equals("TRAFFIC_SPACE")) {
                TrafficSpace ts = (TrafficSpace)sp;
                trace2.add(this.connectionPts.get(ts));
                trs2.add(tr);
                ts.searchTrace(dest, trace2, trs2);
            }
        }
    }

    //EFFECTS: save this space to file
    public void save(PrintWriter pw) {
        String name = this.getName();
        String x = Integer.toString(this.posX);
        String y = Integer.toString(this.posY);
        String w = Integer.toString(this.getWidth());
        String h = Integer.toString(this.getHeight());
        String type = this.getAttribute();
        String floor = this.getFloor();
        pw.println(name + " " + x + " " + y + " " + w + " " + h + " " + floor + " " + type);
    }

    //EFFECTS: get the rectangle of this space
    public Rectangle2D getRectangle() {
        return new Rectangle2D.Double(posX, posY, width, height);
    }

    //MODIFIES: this
    //EFFECTS: remove all the traffic connections
    public void removeConnections() {
        this.connections = new ArrayList<>();
        this.connectionPts = new HashMap<>();
    }

}
