package model;

import java.awt.*;

public class CourtYard extends Space {

    public CourtYard(String nm, int x, int y, int w, int h, String f) {
        super(nm, x, y, w, h, f);
        this.attribute = "COURT_YARD";
    }

    //REQUIRES: width > 0 and height > 0 and f > 0
    public CourtYard(String nm, int w, int h, String f) {
        super(nm, w, h, f);
        this.attribute = "COURT_YARD";
    }

    //EFFECTS: get the building area
    @Override
    public int getBuildingArea() {
        return 0;
    }

    //EFFECTS: draw courtyard
    @Override
    public void draw(Graphics g) {
    }

    //EFFECTS: print the space description
    @Override
    public int spaceDescription() {
        System.out.println("This is a yard, area:" + " " + width * height);
        return 0;
    }


}
