package ui.tools;

import model.Space;
import ui.BuildingAnalyzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CalculateTwoSpacesTool extends Tool {
    private ArrayList<Space> spaces;

    public CalculateTwoSpacesTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
        spaces = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS:  constructs a analyze space button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Analyze two spaces routes");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new TwoSpaceToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS:  Sets the spaces at the current mouse position to analyze and save the current spaces
    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
        Space s = analyzer.getSpaceInDrawing(e.getPoint());
        if (s != null) {
            if (spaces.contains(s)) {
                this.spaces.remove(s);
                s.unselect();
            } else {
                this.spaces.add(s);
                s.select();
            }
            if (spaces.size() == 2) {
                int bl = JOptionPane.showConfirmDialog(button, "Calculate the routes between two spaces?");
                if (bl == 0) {
                    super.analyzer.currentDrawing.calculateTwoSpaces(spaces.get(0), spaces.get(1));
                    super.analyzer.currentDrawing.save();
                }
            }
        }
    }


    private class TwoSpaceToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the calculate tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            analyzer.setActiveTool(CalculateTwoSpacesTool.this);
        }
    }
}
