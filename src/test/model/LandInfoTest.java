package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LandInfoTest {
    private LandInfo landInfo;

    @BeforeEach
    void setUp() {
        landInfo = new LandInfo();
    }

    @Test
    void setLandArea() {
        landInfo.setLandArea(20000);
        assertEquals(20000, landInfo.landArea);
    }

    @Test
    void setCoverageRatio() {
        landInfo.setCoverageRatioLimit(0.8);
        assertEquals(0.8, landInfo.coverageRatioLimit);
    }

    @Test
    void setPlotRatio() {
        landInfo.setPlotRatioLimit(4);
        assertEquals(4, landInfo.plotRatioLimit);
    }

    @Test
    void setRatios() {
        ArrayList<String> str = new ArrayList<>();
        str.add("London");
        str.add("Tower");
        str.add("2");
        str.add("0.5");
        str.add("36000");
        landInfo.setRatios(str);
        assertTrue(landInfo.city.equals("London"));
        assertEquals(2, landInfo.plotRatioLimit);
        assertEquals(0.5, landInfo.coverageRatioLimit);
        assertEquals(36000, landInfo.landArea);
    }
}
