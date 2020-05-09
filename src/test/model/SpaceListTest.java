package model;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpaceListTest {
    private Room r1, r2, r3;
    private CourtYard c1, c2;
    private SpaceList s;
    public static final String DATA_TEST_1_TXT = "./data/test1.txt";
    public static final String DATA_TEST_2_TXT = "./data/test2.txt";
    public static final String DATA_TEST_3_TXT = "./data/test3.txt";
    private SpaceList test1 = new SpaceList(DATA_TEST_1_TXT, "test1");
    private SpaceList test2 = new SpaceList(DATA_TEST_2_TXT, "test2");
    private SpaceList test3 = new SpaceList(DATA_TEST_3_TXT, "test3");

    @BeforeEach
    void setUp() {
        s = new SpaceList("test");
        r1 = new Room("r1", 10,10, "1");
        r2 = new Room("r2", 20,20, "1");
        r3 = new Room("r3", 30,40, "2");
        c1 = new CourtYard("c1", 10,20, "1");
        c2 = new CourtYard("c2", 30,10, "2");
    }

    @Test
    void getSize() throws CoverageRatioException, PlotRatioException {
        assertEquals(0, s.getSpaceListSize());
        s.addSpace(r1);
        assertEquals(1, s.getSpaceListSize());
        s.addSpace(r2);
        assertEquals(2, s.getSpaceListSize());
        s.removeSpace(0);
        assertEquals(1, s.getSpaceListSize());
        s.removeSpace(0);
        assertEquals(0,s.getSpaceListSize());
    }

    @Test
    void addSpace() throws CoverageRatioException, PlotRatioException {
        assertEquals(0, s.getSpaceListSize());
        s.addSpace(r1);
        assertEquals(r1, s.getSpace(0));
        s.addSpace(r2);
        assertEquals(r2, s.getSpace(1));
    }


    @Test
    void removeSpace() throws CoverageRatioException, PlotRatioException {
        assertEquals(0, s.getSpaceListSize());
        s.addSpace(r1);
        assertEquals(r1, s.getSpace(0));
        s.addSpace(r2);
        assertEquals(r2, s.getSpace(1));
        s.removeSpace(0);
        assertEquals(r2, s.getSpace(0));
    }

    @Test
    void  getSpace() throws CoverageRatioException, PlotRatioException {
        assertEquals(0, s.getSpaceListSize());
        s.addSpace(r1);
        assertEquals(r1, s.getSpace(0));
        s.addSpace(r2);
        assertEquals(r2, s.getSpace(1));
        s.removeSpace(0);
        assertEquals(r2, s.getSpace(0));
    }

    @Test
    void getSpaceArea() throws CoverageRatioException, PlotRatioException {
        s.addSpace(r1);
        s.addSpace(r2);
        s.addSpace(c1);
        assertEquals(10 * 10, s.getSpaceArea(0));
        assertEquals(20 * 20, s.getSpaceArea(1));
        assertEquals(10 * 20, s.getSpaceArea(2));
        s.removeSpace(0);
        assertEquals(20 * 20, s.getSpaceArea(0));
    }

    @Test
    void getTotBuildArea() throws CoverageRatioException, PlotRatioException {
        assertEquals(0, s.getTotBuildArea());
        s.addSpace(c2);
        assertEquals(0, s.getTotBuildArea());
        s.addSpace(r2);
        s.addSpace(r3);
        assertEquals(20 * 20 + 30 * 40, s.getTotBuildArea());
    }

    @Test
    void splitOnSpace() {
        ArrayList<String> a1 = new ArrayList<String>();
        ArrayList<String> a2 = new ArrayList<String>();
        a1.add("x");
        a2.add("x1");
        a2.add("x2");
        a2.add("x3");
        assertEquals(a1, s.splitOnSpace("x"));
        assertEquals(a2, s.splitOnSpace("x1 x2 x3"));
    }

    @Test
    void load() throws IOException {
        LoadableTest.load(test1);
        assertEquals("a", test1.getSpace(0).getName());
        assertEquals(20,test1.getSpace(0).getWidth());
        assertEquals(100,test1.getSpace(0).getHeight());
        assertEquals("COURT_YARD", test1.getSpace(0).getAttribute());
        LoadableTest.load(test2);
        assertEquals("a", test2.getSpace(0).getName());
        assertEquals(10,test2.getSpace(0).getWidth());
        assertEquals(10,test2.getSpace(0).getHeight());
        assertEquals("COURT_YARD", test2.getSpace(0).getAttribute());
        assertEquals("b", test2.getSpace(1).getName());
        assertEquals(20,test2.getSpace(1).getWidth());
        assertEquals(10,test2.getSpace(1).getHeight());
        assertEquals("COURT_YARD", test2.getSpace(0).getAttribute());
        assertEquals("ctest", test2.getSpace(2).getName());
        assertEquals(35,test2.getSpace(2).getWidth());
        assertEquals(101,test2.getSpace(2).getHeight());
        assertEquals("COURT_YARD", test2.getSpace(0).getAttribute());
    }

    @Test
    void save() throws IOException, CoverageRatioException, PlotRatioException {
        test3.addSpace(new Room("t1", 0, 0,24, 60, "2"));
        test3.addSpace(new CourtYard("t2", 0, 0, 10, 20, "3"));
        test3.addSpace(new CourtYard("332", 0, 0, 24, 66, "1"));
        test3.save();
        List<String> lines = Files.readAllLines(Paths.get(DATA_TEST_3_TXT));;
        assertTrue(lines.get(1).equals("t1 0 0 24 60 2 ROOM"));
        assertTrue(lines.get(2).equals("t2 0 0 10 20 3 COURT_YARD"));
        assertTrue(lines.get(3).equals("332 0 0 24 66 1 COURT_YARD"));
        for (int i = 0; i < test3.getSpaceListSize(); i++) {
            test3.removeSpace(i);
        }
        PrintWriter writer = new PrintWriter(Paths.get(DATA_TEST_3_TXT).toFile(),"UTF-8");
        writer.println();
        writer.close();
    }


    @Test
    void testHashEqual() {
        assertTrue(test1.equals(test1));
        assertFalse(test1.equals(test2));
        assertFalse(test2.equals(test3));
        assertTrue(test1.hashCode() == test1.hashCode());
        assertFalse(test1.hashCode() == test2.hashCode());
    }
}