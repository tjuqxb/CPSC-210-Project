package ui.tools;

import model.Space;
import model.TrafficComponent;
import ui.BuildingAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class MoveTool extends Tool {
    private Space spaceToMove;
    private Point start;

    public MoveTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
        spaceToMove = null;
        start = null;
    }

    // MODIFIES: this
    // EFFECTS:  constructs a move button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Move Space");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new MoveToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS:  Sets the space at the current mouse position as the space to move,
    //           selects space, and initialize the starting point of
    //           the move with the current location of the MouseEvent
    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
        spaceToMove = analyzer.getSpaceInDrawing(e.getPoint());
        if (spaceToMove != null) {
            spaceToMove.select();
            start = e.getPoint();
        }
    }

    // MODIFIES: this
    // EFFECTS:  unselect the space, and set the space to be moved to null
    @Override
    public void mouseReleasedInDrawingArea(MouseEvent e) {
        if (spaceToMove != null) {
            spaceToMove.unselect();
            spaceToMove = null;
        }
    }

    // MODIFIES: this
    // EFFECTS:  compute the change in the x and y position of the mouse, and move the shape
    @Override
    public void mouseDraggedInDrawingArea(MouseEvent e) {
        if (spaceToMove != null) {
            int dx = (int) (e.getPoint().getX() - start.getX());
            int dy = (int) (e.getPoint().getY() - start.getY());
            start = e.getPoint();
            spaceToMove.move(dx, dy);
            TrafficComponent start = super.analyzer.currentDrawing.twoPointRe.start;
            TrafficComponent end = super.analyzer.currentDrawing.twoPointRe.end;
            if (spaceToMove.equals(start) || (spaceToMove.equals(end))) {
                super.analyzer.currentDrawing.calculateTwoSpaces((Space) start,(Space) end);
            }
        }
    }

    private class MoveToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the move tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            analyzer.setActiveTool(MoveTool.this);
        }

    }
}
