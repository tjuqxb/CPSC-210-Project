package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class FloorMapTest {
    private FloorMap floorMap;
    private Space sp1;
    private Space sp2;
    private SpaceList spaceList;

    @BeforeEach
    void setUp() {
        floorMap = new FloorMap();
        spaceList = new SpaceList("test");
        sp1 = new Room("test", 10, 20, "1");
        sp2 = new CourtYard("test2", 20 , 20, "1");
        try {
            spaceList.addSpace(sp1);
            spaceList.addSpace(sp2);
        } catch (Exception e) {
        }
    }

    @Test
    void removeSpaceTest() {
        try {
            floorMap.putFloorSpace(sp1);
            floorMap.putFloorSpace(sp2);
        } catch (Exception e) {
            fail("should not throw exception");
        }
        assertTrue(floorMap.getSpaceList(sp1.getFloor()).getSpace(0).equals(sp1));
        assertTrue(floorMap.getSpaceList(sp1.getFloor()).getSpace(1).equals(sp2));
        floorMap.removeFloorSpace(sp1);
        assertTrue(floorMap.getSpaceList(sp1.getFloor()).getSpace(0).equals(sp2));
        floorMap.removeFloorSpace(sp2);
        assertTrue(floorMap.getSpaceList(sp1.getFloor()) == null);
    }

    @Test
    void addSpaceListTest() {
        try {
            floorMap.addSpaceList(spaceList);
        } catch (Exception e) {
            fail("should not throw exception");
        }
        assertTrue(floorMap.getSpaceList(sp1.getFloor()).getSpace(0).equals(sp1));
        assertTrue(floorMap.getSpaceList(sp1.getFloor()).getSpace(1).equals(sp2));
    }
}
