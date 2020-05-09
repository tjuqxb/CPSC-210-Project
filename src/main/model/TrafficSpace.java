package model;

import java.awt.*;
import java.util.ArrayList;

public class TrafficSpace extends Space {

    public TrafficSpace(String nm, int x, int y, int w, int h, String f) {
        super(nm, x, y, w, h, f);
        this.attribute = "TRAFFIC_SPACE";
    }


    public TrafficSpace(String nm,int w, int h, String f) {
        super(nm,w,h,f);
        this.attribute = "TRAFFIC_SPACE";
    }

    //REQUIRES: width > 0 and height > 0 and f > 0
    public TrafficSpace(String nm, String f, Point topLeft) {
        super(nm, f, topLeft);
        this.attribute = "TRAFFIC_SPACE";
    }

    //EFFECTS: return the building area of traffic space
    @Override
    public int getBuildingArea() {
        return this.getArea();
    }

    //EFFECTS: print the space description
    @Override
    public int spaceDescription() {
        System.out.println("This is traffic space, area:" + width * height);
        return 0;
    }

    //EFFECTS: search the all the routes from this to dest
    public void searchTrace(TrafficComponent dest, ArrayList<TrafficConnectionPoint> trace,
                            ArrayList<TrafficComponent> trs) {
        searchOneSpace(dest, trace);
        for (TrafficComponent tr: connections) {
            Space sp = (Space) tr;
            if (!sp.getAttribute().equals("TRAFFIC_SPACE")) {
                sp.searchOneSpace(dest, trace);
            } else if (!trs.contains(tr) && sp.getAttribute().equals("TRAFFIC_SPACE")) {
                ArrayList trace2 = new ArrayList();
                ArrayList trs2 = new ArrayList();
                trace2.addAll(trace);
                trace2.add(this.connectionPts.get(tr));
                trs2.addAll(trs);
                trs2.add(tr);
                TrafficSpace ts = (TrafficSpace) sp;
                ts.searchTrace(dest, trace2, trs2);
            }
        }
    }

    // EFFECTS: draws this space, if the shape is selected, color is different
    public void draw(Graphics g) {
        Color save = g.getColor();
        if (selected) {
            g.setColor(selColor);
        } else {
            g.setColor(Color.gray);
        }
        fillGraphics(g);
        g.setColor(save);
        drawGraphics(g);
    }
}
