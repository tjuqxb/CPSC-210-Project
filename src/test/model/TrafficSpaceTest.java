package model;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrafficSpaceTest {
    private SpaceList spl;
    private TrafficSpace tr;
    private TrafficSpace test1;
    private Room rm1;
    private Room rm2;

    @BeforeEach
    void setUp() {
        spl = new SpaceList("TEST");
        tr = new TrafficSpace("Test", 1, 2, "2");
        test1 = new TrafficSpace("Test1", 2, 4, "4");
        rm1 = new Room("Test2", 3, 4,"4");
        rm2 = new Room("Test3", 1, 2,"5");
        try {
            spl.addSpace(tr);
            spl.addSpace(test1);
            spl.addSpace(rm1);
            spl.addSpace(rm2);
        } catch (PlotRatioException e) {
            e.printStackTrace();
        } catch (CoverageRatioException e) {
            e.printStackTrace();
        }

    }

    @Test
    void getBuildingArea() {
        assertEquals(2, tr.getBuildingArea());
        tr.setWidth(10);
        tr.setHeight(2);
        assertEquals(20, tr.getBuildingArea());
    }

    @Test
    void getAttribute() {
        assertEquals("TRAFFIC_SPACE", tr.getAttribute());
    }

    @Test
    void testConnections() {
        assertEquals(4, spl.spList.size());
        assertEquals(1, tr.spLL.size());
        assertEquals(1, test1.spLL.size());
        assertEquals(1, rm1.spLL.size());
        assertEquals(1, rm2.spLL.size());
        test1.addTrafficConnection(rm1);
        assertEquals(1, test1.connections.size());
        assertEquals(1, rm1.connections.size());
        test1.removeTrafficConnection(rm1);
        assertEquals(0, test1.connections.size());
        assertEquals(0, rm1.connections.size());
        test1.addTrafficConnection(rm1);
        tr.addTrafficConnection(test1);
        tr.addTrafficConnection(rm2);
        spl.calculateTwoSpaces(rm2, rm1);
        spl.removeConnections();
        Room rNew = spl.createRoom(new Point(0,0));
        assertTrue(rNew.equals(new Room("0", "1", new Point(0,0))));
        TrafficSpace tNew = spl.createTrafficSpace(new Point(0,0));
        spl.showAll();
        assertTrue(tNew.equals(new TrafficSpace("0", "1", new Point(0,0))));
        assertEquals(0, tr.connections.size());
        assertEquals(0, tr.connectionPts.size());
        assertTrue(spl.getSpaceAtPoint(new Point(0,0)).equals(tr));
    }
}
