package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.BuildingAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    private Room room;
    private Room room2;
    private Room room3;
    private Room room4;
    private SpaceList spl;

    @BeforeEach
    void setUp() {
        room = new Room("test", 10, 20, "1");
        room2 = new Room("test", 10, 20, "1");
        room3 = new Room("test", 0,0,10, 20, "2");
        Point pt = new Point();
        room4 = new Room("test", "test",pt);
        spl = new SpaceList("test");
        try {
            spl.addSpace(room3);
        } catch (Exception e) {
        }
    }

    @Test
    void setName() {
        room.setName("test0");
        assertEquals("test0", room.getName());
        room.setName("test1");
        assertEquals("test1", room.getName());
    }

    @Test
    void setWidth() {
        room.setWidth(200);
        assertEquals(200, room.getWidth());
        room.setWidth(300);
        assertEquals(300,room.getWidth());
    }

    @Test
    void setHeight() {
        room.setHeight(100);
        assertEquals(100, room.getHeight());
        room.setHeight(800);
        assertEquals(800,room.getHeight());
    }

    @Test
    void getAttribute() {
        assertEquals("ROOM", room.getAttribute());
    }

    @Test
    void getWidth() {
        assertEquals(10, room.getWidth());
        room.setWidth(50);
        assertEquals(50, room.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(20, room.getHeight());
        room.setHeight(55);
        assertEquals(55, room.getHeight());
    }

    @Test
    void getArea() {
        assertEquals(200, room.getArea());
        room.setWidth(30);
        room.setHeight(40);
        assertEquals(1200, room.getArea());
    }

    @Test
    void getBuildingArea() {
        assertEquals(200, room.getBuildingArea());
        room.setWidth(60);
        room.setHeight(70);
        assertEquals(4200, room.getBuildingArea());
    }

    @Test
    void getName() {
        assertEquals("test", room.getName());
        room.setName("test100");
        assertEquals("test100", room.getName());
    }

    @Test
    void equalTest() {
        assertTrue(room.equals(room2));
        assertFalse(room2.equals(room3));
    }

    @Test
    void hashTest() {
        assertTrue(room.hashCode() == room2.hashCode());
        assertFalse(room2.hashCode() == room3.hashCode());
    }

    @Test
    void drawTest() {
        try {
            room3.draw(spl.getGraphics());
            room3.fillGraphics(spl.getGraphics());
            room3.drawGraphics(spl.getGraphics());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void contains() {
        Point pt1 = new Point(1,1);
        Point pt2 = new Point(100,100);
        assertFalse(room3.contains(pt2));
        assertTrue(room3.contains(pt1));
    }

    @Test
    void setBound() {
        room3.setBounds(new Point(100, 100));
        assertTrue(room3.width == 100 - room3.posX);
        assertTrue(room3.height == 100 - room3.posY);
        room3.move(1,2);
        assertEquals(1, room3.posX);
        assertEquals(2, room3.posY);
    }

    @Test
    void selectTest() {
        room3.select();
        assertTrue(room3.selected);
        room3.unselect();
        assertFalse(room3.selected);
    }

    @Test
    void rectangleTest() {
        Rectangle2D rec = room3.getRectangle();
        assertEquals(10, rec.getMaxX()-rec.getMinX());
        assertEquals(20, rec.getMaxY()-rec.getMinY());
    }
}