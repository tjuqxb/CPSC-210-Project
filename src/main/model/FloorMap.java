package model;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;

import java.util.HashMap;

public class FloorMap {
    protected HashMap<String, SpaceList> floorMap;

    public FloorMap() {
        floorMap = new HashMap<>();
    }

    //MODIFIES: this
    //EFFECTS: add space to the floor space hash-map
    public void putFloorSpace(Space sp) throws CoverageRatioException, PlotRatioException {
        String floor = sp.getFloor();
        SpaceList spl = floorMap.get(floor);
        if (spl == null) {
            SpaceList newSpl = new SpaceList(floor);
            newSpl.addSpace(sp);
            floorMap.put(floor, newSpl);
        } else {
            spl.addSpace(sp);
        }
    }

    //MODIFIES: this
    //EFFECTS: remove space from the floor space hash-map
    public void removeFloorSpace(Space sp) {
        String floor = sp.getFloor();
        SpaceList spl = floorMap.get(floor);
        if (spl != null) {
            spl.removeSpace(sp);
            if (spl.getSpaceListSize() == 0) {
                floorMap.remove(floor);
            }
        }
    }

    public SpaceList getSpaceList(String key) {
        return floorMap.get(key);
    }

    //MODIFIES: this
    //EFFECTS: get all the spaces from a space list
    public void addSpaceList(SpaceList spL) {
        for (Space sp: spL.spList) {
            try {
                putFloorSpace(sp);
            } catch (CoverageRatioException e) {
                System.out.println("Coverage Ratio Exception");
            } catch (PlotRatioException e) {
                System.out.println("Plot Ration Exception");
            }
        }
    }
}
