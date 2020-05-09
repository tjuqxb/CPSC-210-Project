package network;

import java.util.ArrayList;

public class ClimateData {
    public String name;
    public ArrayList<Double> data;

    public ClimateData(String name) {
        this.name = name;
        this.data = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add a number to the data
    public void addMonthData(double number) {
        data.add(number);
    }

    //EFFECTS: display the data
    public void displayData() {
        System.out.printf("%-20s",this.name);
        for (int i = 0; i < data.size(); i++) {
            System.out.printf("%-8s", data.get(i));
        }
        System.out.print("\n");
    }
}
