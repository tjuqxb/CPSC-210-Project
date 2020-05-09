package ui.tools;

import javafx.stage.FileChooser;
import ui.BuildingAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

public class AddBackGroundTool extends Tool {
    public AddBackGroundTool(BuildingAnalyzer analyzer, JComponent parent) {
        super(analyzer, parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs change background button which is then added to the JComponent (parent)
    //           which is passed in as a parameter
    @Override
    protected void createButton(JComponent parent) {
        button = new JButton("Change Background");
        addToParent(parent);
    }

    // MODIFIES: this
    // EFFECTS:  constructs a new listener object which is added to the JButton
    @Override
    protected void addListener() {
        button.addActionListener(new ChangeBackGroundToolClickHandler());
    }


    private class ChangeBackGroundToolClickHandler implements ActionListener {

        // EFFECTS: sets active tool to the add background tool
        //          called by the framework when the tool is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            //Color co = JColorChooser.showDialog(AddBackGroundTool.this.analyzer,"pick a color", null);
            if (chooser.showOpenDialog(button) == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String filePos = file.getAbsolutePath();
                AddBackGroundTool.super.analyzer.currentDrawing.setImage(filePos);
                AddBackGroundTool.super.analyzer.repaint();
            }
        }
    }
}
