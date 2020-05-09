package ui.tools;

import model.Space;
import ui.BuildingAnalyzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DeleteTool extends Tool {

    public DeleteTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a delete button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Delete Space");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new DeleteToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS:  Sets the space at the current mouse position as the space to delete,
    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
        Space s = analyzer.getSpaceInDrawing(e.getPoint());
        int bl = JOptionPane.showConfirmDialog(button,"Delete this?");
        if (bl == 0) {
            super.analyzer.currentDrawing.removeSpace(s);
        }
    }

    private class DeleteToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the delete tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            analyzer.setActiveTool(DeleteTool.this);
        }
    }

}
