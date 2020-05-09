package ui.tools;

import ui.BuildingAnalyzer;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class AddRoomTool extends SpaceTool {
    public AddRoomTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
        space = null;
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "Add Room";
    }

    //EFFECTS: Constructs and returns the new room
    protected void makeSpace(MouseEvent e) {
        space = AddRoomTool.super.analyzer.currentDrawing.createRoom(e.getPoint());
    }
}
