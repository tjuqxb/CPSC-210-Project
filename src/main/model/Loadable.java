package model;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;

import java.io.IOException;

public interface Loadable {
    // EFFECTS: load file and get values
    public int load() throws IOException, ArrayIndexOutOfBoundsException, NumberFormatException;
}
