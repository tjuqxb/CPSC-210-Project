package model;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface Saveable {
    // EFFECTS: save file
    public void save() throws FileNotFoundException, UnsupportedEncodingException;
}
