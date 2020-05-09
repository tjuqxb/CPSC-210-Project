package ui.tools;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;
import model.Space;
import ui.BuildingAnalyzer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public abstract class SpaceTool extends Tool {
    protected Space space;

    public SpaceTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
        space = null;
    }

    // MODIFIES: this
    // EFFECTS:  creates new button and adds to parent
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton(getLabel());
        button = customizeButton(button);
    }

    // MODIFIES: this
    // EFFECTS:  associate button with new ClickHandler
    @Override
    protected void addListener() {
        button.addActionListener(new ShapeToolClickHandler());
    }

    // MODIFIES: this
    // EFFECTS:  a space is instantiate MouseEvent occurs and added to the editor's drawing
    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
        makeSpace(e);
        space.setBounds(e.getPoint());
        try {
            analyzer.addToDrawing(space);
        } catch (CoverageRatioException ex) {
            ex.printStackTrace();
        } catch (PlotRatioException ex) {
            ex.printStackTrace();
        }
    }


    // MODIFIES: this
    // EFFECTS:  unselects this space, and sets it to null
    @Override
    public void mouseReleasedInDrawingArea(MouseEvent e) {
        space.unselect();
        space = null;
    }

     //MODIFIES: this
     //EFFECTS:  sets the bounds of the space where the mouse is dragged to
    @Override
    public void mouseDraggedInDrawingArea(MouseEvent e) {
        space.setBounds(e.getPoint());
    }

    //EFFECTS: Returns the string for the label.
    protected abstract String getLabel();
//    {
//
//	}

    //EFFECTS: Constructs and returns the new space
    protected abstract void makeSpace(MouseEvent e);
//	{
//
//	}

    private class ShapeToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the space tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            analyzer.setActiveTool(SpaceTool.this);
        }
    }
}
