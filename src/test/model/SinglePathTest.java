package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class SinglePathTest {
    private SinglePath s1;
    private SinglePath s2;
    private SinglePath s3;


    @BeforeEach
    void setUp() {
        s1 = new SinglePath(10, new ArrayList<>());
        s2 = new SinglePath(10, new ArrayList<>());
        s3 = new SinglePath(11, new ArrayList<>());
    }

    @Test
    void testCom() {
        Assertions.assertEquals(0, s1.compareTo(s2));
        Assertions.assertEquals(1, s3.compareTo(s1));
        Assertions.assertEquals(-1, s1.compareTo(s3));
    }
}
