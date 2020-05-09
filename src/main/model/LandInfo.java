package model;

import java.util.ArrayList;
import java.util.Observable;

public class LandInfo extends Observable {
    public double plotRatioLimit = 3;
    public double coverageRatioLimit = 1;
    public double landArea = 10000;
    public String city = " ";
    public String place = " ";

    public LandInfo() {
    }

    //MODIFIES: this
    //EFFECTS: set the city
    public void setCity(String city) {
        this.city = city;
        this.setChanged();
        notifyObservers(city);
    }

    //MODIFIES: this
    //EFFECTS: set the place
    public void setPlace(String place) {
        this.place = place;
    }


    //MODIFIES: this
    //EFFECTS: set the plot ratio
    public void setPlotRatioLimit(double m) {
        this.plotRatioLimit = m;
    }

    //MODIFIES: this
    //EFFECTS: set the coverage ratio
    public void setCoverageRatioLimit(double m) {
        this.coverageRatioLimit = m;
    }

    //MODIFIES: this
    //EFFECTS: set the land area
    public void setLandArea(double m) {
        this.landArea = m;
    }

    //MODIFIES: this
    //EFFECTS: according to the load file, set the ratios
    public void setRatios(ArrayList<String> pl) {
        try {
            String city = pl.get(0);
            String place = pl.get(1);
            double m1 = Double.parseDouble(pl.get(2));
            double m2 = Double.parseDouble(pl.get(3));
            double m3 = Double.parseDouble(pl.get(4));
            this.setCity(city);
            this.setPlace(place);
            this.setPlotRatioLimit(m1);
            this.setCoverageRatioLimit(m2);
            this.setLandArea(m3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: display the land area and ratio limits
    public void showLandInfo() {
        System.out.println(city);
        System.out.println("Land Area: " + this.landArea + "\n" + "Plot Ratio Limit: " + this.plotRatioLimit
                + "\n" + "Coverage Ratio Limit: " + this.coverageRatioLimit);
    }

    // EFFECTS: return whether violate plotRatio limit
    public boolean violatePlot(int area) {
        return (double)area / landArea > plotRatioLimit;
    }

    //EFFECTS: return whether violate CoverageRatio limit
    public boolean violateCoverage(int area) {
        return (double)area / landArea > coverageRatioLimit;
    }

    //EFFECTS: transform the land information to a string
    @Override
    public String toString() {
        return city + " " + place + " " + plotRatioLimit + " " + coverageRatioLimit + " " + landArea;
    }
}