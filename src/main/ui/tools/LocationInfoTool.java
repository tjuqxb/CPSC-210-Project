package ui.tools;

import ui.BuildingAnalyzer;
import ui.DataDisplay;
import ui.MapDisplay;
import ui.SpaceStat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class LocationInfoTool extends Tool {
    public LocationInfoTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a change location button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Change Location");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new ChangeLocationToolClickHandler());
    }


    private class ChangeLocationToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the change location tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            String city = JOptionPane.showInputDialog("Please enter the city name:");
            String location = JOptionPane.showInputDialog("Please enter the location");
            int area;
            String areaInput = JOptionPane.showInputDialog("Please enter the area in square meters");
            while (!SpaceStat.isParseInt(areaInput)
                    || ((SpaceStat.isParseInt(areaInput)) && Integer.parseInt(areaInput) < 0)) {
                areaInput = JOptionPane.showInputDialog("Please enter a positive integer");
            }
            area = Integer.parseInt(areaInput);
            LocationInfoTool.super.analyzer.currentDrawing.setImageRatio(area);
            LocationInfoTool.super.analyzer.currentDrawing.landInfo.setLandArea(area);
            LocationInfoTool.super.analyzer.currentDrawing.landInfo.setCity(city);
            LocationInfoTool.super.analyzer.currentDrawing.landInfo.setPlace(location);
            LocationInfoTool.super.analyzer.currentDrawing.save();
            new DataDisplay().displayClimates(LocationInfoTool.super.analyzer.currentDrawing
                    .webClimate.climateDataList);
            new MapDisplay().displaySite(city, location);
        }
    }

}
