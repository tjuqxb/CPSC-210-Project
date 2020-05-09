package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SpaceFunctionTest {
    private SpaceFunction s1, s2, s3;

    @BeforeEach
    protected void setUp() {
        s1 = new CourtYard("TEST1", 16, 17, "1");
        s2 = new Room("TEST2", 22,66, "2");
        s3 = new TrafficSpace("TEST3", 32,33, "4");
    }

    @Test
    void spaceDescriptionTest() {
        Assertions.assertEquals(0,s1.spaceDescription());
        Assertions.assertEquals(0,s2.spaceDescription());
        Assertions.assertEquals(0,s3.spaceDescription());
    }

}
