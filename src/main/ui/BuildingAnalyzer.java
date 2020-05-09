package ui;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;
import model.Space;
import model.SpaceList;
import ui.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuildingAnalyzer extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    private List<Tool> tools;
    private Tool activeTool;

    public SpaceList currentDrawing;

    public BuildingAnalyzer() {
        super("Building Analyzer");
        initializeFields();
        initializeGraphics();
        initializeInteraction();
    }

    // MODIFIES: this
    // EFFECTS:  initializes a DrawingMouseListener to be used in the JFrame
    private void initializeInteraction() {
        DrawingMouseListener dml = new DrawingMouseListener();
        addMouseListener(dml);
        addMouseMotionListener(dml);
    }


    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this building analyzer will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createTools();
        addNewDrawing();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS:  sets activeTool, currentDrawing to null, and instantiates drawings and tools with ArrayList
    //           this method is called by the BuildingAnalyzer constructor
    private void initializeFields() {
        SpaceStat running = new SpaceStat();
        activeTool = null;
        currentDrawing = running.spaceList;
        tools = new ArrayList<ui.tools.Tool>();
    }

    // MODIFIES: this
    // EFFECTS:  declares and instantiates a Drawing (newDrawing), and adds it to drawings
    private void addNewDrawing() {
        SpaceList newDrawing = new SpaceList("save");
        currentDrawing = newDrawing;
        try {
            newDrawing.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(newDrawing, BorderLayout.CENTER);
        validate();
    }

    // MODIFIES: this
    // EFFECTS:  adds given space to currentDrawing
    public void addToDrawing(Space f) throws CoverageRatioException, PlotRatioException {
        currentDrawing.addSpace(f);
    }

    // MODIFIES: this
    // EFFECTS:  removes given space from currentDrawing
    public void removeFromDrawing(Space f) {
        currentDrawing.removeSpace(f);
    }

    // EFFECTS: if activeTool != null, then mousePressedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMousePressed(MouseEvent e)  {
        if (activeTool != null) {
            activeTool.mousePressedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, then mouseReleasedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseReleased(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseReleasedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, then mouseClickedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseClicked(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseClickedInDrawingArea(e);
        }
        repaint();
    }

    // EFFECTS: if activeTool != null, then mouseDraggedInDrawingArea is invoked on activeTool, depends on the
    //          type of the tool which is currently activeTool
    private void handleMouseDragged(MouseEvent e) {
        if (activeTool != null) {
            activeTool.mouseDraggedInDrawingArea(e);
        }
        repaint();
    }

    // MODIFIES: this
    // EFFECTS:  sets the given tool as the activeTool
    public void setActiveTool(ui.tools.Tool tool) {
        if (activeTool != null) {
            activeTool.deactivate();
        }
        tool.activate();
        activeTool = tool;
    }

    // EFFECTS: return space at given point at the currentDrawing
    public Space getSpaceInDrawing(Point point) {
        return currentDrawing.getSpaceAtPoint(point);
    }


    // MODIFIES: this
    // EFFECTS:  a helper method which declares and instantiates all tools
    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(0,1));
        toolArea.setSize(new Dimension(0, 0));
        add(toolArea, BorderLayout.SOUTH);
        addTools(toolArea);
    }

    // MODIFIES: this
    // EFFECTS: add the tools to the JPanel
    private void addTools(JPanel toolArea) {
        AddBackGroundTool addBackGround = new AddBackGroundTool(this, toolArea);
        tools.add(addBackGround);
        LocationInfoTool changeLocation = new LocationInfoTool(this, toolArea);
        tools.add(changeLocation);
        ReviewLocationInfoTool reviewLocationInfoTool = new ReviewLocationInfoTool(this, toolArea);
        tools.add(reviewLocationInfoTool);
        AddTrafficTool addTrafficTool = new AddTrafficTool(this, toolArea);
        tools.add(addTrafficTool);
        AddRoomTool addRoomTool = new AddRoomTool(this, toolArea);
        tools.add(addRoomTool);
        DeleteTool deleteTool = new DeleteTool(this, toolArea);
        tools.add(deleteTool);
        MoveTool moveTool = new MoveTool(this, toolArea);
        tools.add(moveTool);
        CalculateTwoSpacesTool calculateTwoSpacesTool = new CalculateTwoSpacesTool(this, toolArea);
        tools.add(calculateTwoSpacesTool);
    }


    //EFFECTS：start the BuildingAnalyzer
    public static void main(String[] args) {
        new BuildingAnalyzer();
    }



    private class DrawingMouseListener extends MouseAdapter {

        // EFFECTS: Forward mouse pressed event to the active tool
        public void mousePressed(MouseEvent e) {
            handleMousePressed(translateEvent(e));
        }

        // EFFECTS: Forward mouse released event to the active tool
        public void mouseReleased(MouseEvent e) {
            handleMouseReleased(translateEvent(e));
        }

        // EFFECTS:Forward mouse clicked event to the active tool
        public void mouseClicked(MouseEvent e) {
            handleMouseClicked(translateEvent(e));
        }

        // EFFECTS:Forward mouse dragged event to the active tool
        public void mouseDragged(MouseEvent e) {
            handleMouseDragged(translateEvent(e));
        }

        // EFFECTS: translates the mouse event to current drawing's coordinate system
        private MouseEvent translateEvent(MouseEvent e) {
            return SwingUtilities.convertMouseEvent(e.getComponent(), e, currentDrawing);
        }
    }
}
