package model;

import java.util.ArrayList;

public class TwoPtPaths {
    public TrafficComponent start;
    public TrafficComponent end;
    public ArrayList<SinglePath> records;

    public TwoPtPaths(TrafficComponent s, TrafficComponent e) {
        this.start = s;
        this.end = e;
        records = new ArrayList<>();
    }
}
