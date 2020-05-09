package model;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoadableTest {

    // EFFECTS: load the values
    @Test
   static void load(Loadable m) throws IOException, ArrayIndexOutOfBoundsException, NumberFormatException {
        Assertions.assertEquals(0, m.load());
    }
}
