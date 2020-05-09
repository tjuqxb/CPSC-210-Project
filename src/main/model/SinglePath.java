package model;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class SinglePath implements Comparable<SinglePath> {
    public ArrayList<Line2D> sequence;
    public double length;

    public SinglePath(double length, ArrayList<Line2D> sequence) {
        this.sequence = sequence;
        this.length = length;
    }


    //EFFECTS: return the compare value to implement sort
    @Override
    public int compareTo(SinglePath o) {
        if (this.length == o.length) {
            return 0;
        } else if (this.length > o.length) {
            return 1;
        } else {
            return -1;
        }
    }
}
