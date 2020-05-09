package ui.tools;

import model.Space;
import model.TrafficSpace;
import ui.BuildingAnalyzer;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class AddTrafficTool extends SpaceTool {
    public AddTrafficTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
        space = null;
    }

    //EFFECTS: Returns the string for the label.
    protected String getLabel() {
        return "Add Traffic Space";
    }

    //EFFECTS: Constructs and returns the new traffic space
    protected void makeSpace(MouseEvent e) {
        space = AddTrafficTool.super.analyzer.currentDrawing.createTrafficSpace(e.getPoint());
    }
}
