package model;

public interface TrafficComponent {
    public String getName();

    //EFFECTS: add a traffic connection
    public void addTrafficConnection(TrafficComponent tr);

    //EFFECTS: remove a traffic connection
    public void removeTrafficConnection(TrafficComponent tr);
}
