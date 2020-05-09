package model;

import java.awt.*;
import java.util.ArrayList;

public class Room extends Space {

    public Room(String nm, int x, int y, int w, int h, String f) {
        super(nm, x, y, w, h, f);
        this.attribute = "ROOM";
    }

    //REQUIRES: width > 0 and height > 0 and f > 0
    public Room(String nm, int w, int h, String f) {
        super(nm, w, h, f);
        this.attribute = "ROOM";
    }



    //REQUIRES: width > 0 and height > 0 and f > 0
    public Room(String nm, String f, Point topLeft) {
        super(nm, f, topLeft);
        this.attribute = "ROOM";
    }

    //EFFECTS: return the building area
    @Override
    public int getBuildingArea() {
        return this.getArea();
    }

    //EFFECTS: print the space description
    @Override
    public int spaceDescription() {
        System.out.println("This is a room, area:" + " " + width * height);
        return 0;
    }

    // EFFECTS: draws the room
    public void draw(Graphics g) {
        Color save = g.getColor();
        if (selected) {
            g.setColor(selColor);
        } else {
            g.setColor(Color.blue);
        }
        fillGraphics(g);
        g.setColor(save);
        drawGraphics(g);
    }
}
