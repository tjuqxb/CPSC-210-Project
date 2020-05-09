package ui.tools;

import ui.BuildingAnalyzer;
import ui.DataDisplay;
import ui.MapDisplay;
import ui.SpaceStat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReviewLocationInfoTool extends Tool {
    public ReviewLocationInfoTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a review location button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Review Location Information");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new ReviewLocationToolClickHandler());
    }


    private class ReviewLocationToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the review location tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            new DataDisplay().displayClimates(ReviewLocationInfoTool.super.analyzer.currentDrawing
                    .webClimate.climateDataList);
            new MapDisplay().displaySite(ReviewLocationInfoTool.super.analyzer.currentDrawing.landInfo.city,
                    ReviewLocationInfoTool.super.analyzer.currentDrawing.landInfo.place);
        }
    }
}
