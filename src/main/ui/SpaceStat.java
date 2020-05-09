package ui;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;
import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class SpaceStat {
    public SpaceList spaceList;
    private FloorMap floorMap;
    private Scanner sc;

    public SpaceStat() {
        spaceList = new SpaceList("save");
        floorMap = new FloorMap();
        sc = new Scanner(System.in);
    }

    // EFFECTS: run the interface
    public void run() throws IOException, ArrayIndexOutOfBoundsException, NumberFormatException,
            FileNotFoundException, UnsupportedEncodingException {
        spaceList.load();
        floorMap.addSpaceList(spaceList);
        while (true) {
            System.out.println("What would you like to do? Enter a number.");
            System.out.println("1. Add a space.\n2. Delete a space\n3. View all the spaces");
            System.out.println("4. See one space area");
            System.out.println("5. See the building total area");
            System.out.println("6. Modify the ratios and land area");
            System.out.println("7. View all the spaces on a floor");
            System.out.println("8. View location climate \n9. Change location\n10. Exit");
            String operation = sc.nextLine();
            System.out.println("you selected: " + operation);
            if (operation.equals("10")) {
                spaceList.save();
                break;
            }
            innerCall(operation);
        }
    }

    // EFFECTS: pass the operation and values to each function
    private void innerCall(String operation) {
        innerCallSw1(operation);
        innerCallSw2(operation);
        switch (operation) {
            case "9":
                changeLocationProc();
                break;
            default:
                System.out.println("Please re-enter your choice");
        }
    }

    // EFFECTS：switch each function
    private void innerCallSw1(String op) {
        switch (op) {
            case "1":
                addSpaceProc();
                break;
            case "2":
                deleteSpaceProc();
                break;
            case "3":
                spaceList.showAll();
                break;
            case "4":
                spaceAreaProc();
                break;
            default:
        }
    }

    //EFFECTS： switch each function
    private void innerCallSw2(String op) {
        switch (op) {
            case "5":
                System.out.println(spaceList.getTotBuildArea());
                break;
            case "6":
                spaceRatioProc();
                break;
            case "7":
                viewFloorProc();
                break;
            case "8":
                viewLocationClimate();
                break;
            default:
        }
    }

    //EFFECTS: show the climate data
    private void viewLocationClimate() {
        this.spaceList.webClimate.showData();
    }

    //EFFECTS: change the location procedure
    public void changeLocationProc() {
        this.spaceList.landInfo.showLandInfo();
        System.out.println("Which city do you want to locate the project?");
        String str = sc.nextLine();
        this.spaceList.landInfo.setCity(str);
        System.out.println("Change successfully.");
    }

    //EFFECTS: show the floor
    private void viewFloorProc() {
        System.out.println("Which floor do you want to see?(-2, -1, 1, 2...)");
        String str = sc.nextLine();
        if (!isParseDouble(str)) {
            System.out.println("Not an integer, please re-enter");
            viewFloorProc();
        } else {
            SpaceList spL = floorMap.getSpaceList(str);
            if (spL != null) {
                spL.showAll();
            } else {
                System.out.println("The floor does not exit.");
            }
        }
    }

    // EFFECTS: get the information for modify the space ratios
    private void spaceRatioProc() {
        spaceList.landInfo.showLandInfo();
        System.out.println("What ratio do you want to modify? 1 for plot ratio, 2 for coverage ratio, "
                + "3 for land area, other return");
        String str = sc.nextLine();
        if (!(str.equals("1") || str.equals("2") || (str.equals("3")))) {
            return;
        }
        spaceRatioRe(str);
    }

    // EFFECT: modify the space ratios
    private void spaceRatioRe(String str) {
        switch (str) {
            case "1":
                System.out.println("Please enter the new plot ratio.");
                spaceList.landInfo.setPlotRatioLimit(getPositiveDouble());
                break;
            case "2":
                System.out.println("Please enter the new coverage ratio.");
                spaceList.landInfo.setCoverageRatioLimit(getPositiveDouble());
                break;
            case "3":
                System.out.println("Please enter the new land area");
                spaceList.landInfo.setLandArea(getPositiveInteger());
                break;
            default:
        }
    }

    // EFFECTS: collect all the parameters for addSpaceProc function
    public void addSpaceProc() {
        System.out.println("What is the space type? 1 for yard, 2 for room, 3  for traffic, other return");
        String str = sc.nextLine();
        if (!(str.equals("1") || str.equals("2") || (str.equals("3")))) {
            return;
        }
        System.out.println("Please enter space name?");
        String rn = sc.nextLine();
        System.out.println("Please enter the width");
        int w = getPositiveInteger();
        System.out.println("Please enter the height");
        int h = getPositiveInteger();
        System.out.println("Please enter the floor (-1,-2,1,2,3..)");
        String f = sc.nextLine();
        addSpaceRe(str, rn, w, h, f);
    }

    // MODIFIES: this
    // EFFECTS: add one space to the spaceList
    private void addSpaceRe(String type, String name, int w, int h, String f) {
        Space sp = generateSpace(type, name, w, h, f);
        try {
            spaceList.addSpace(sp);
            floorMap.putFloorSpace(sp);
            System.out.println("Add successfully.");
        } catch (PlotRatioException e) {
            System.out.println("Unsuccessful. The plot ratio has exceeded the limit.");
        } catch (CoverageRatioException e) {
            System.out.println("Unsuccessful. The coverage ratio has exceeded the limit.");
        } finally {
            System.out.println("You can change the land ratios and land area in the main menu.");
        }
    }

    //EFFECTS: generate a space to add
    private Space generateSpace(String type, String name, int w, int h, String f) {
        Space sp;
        if (type.equals("1")) {
            sp = new CourtYard(name, w, h, f);
        } else if (type.equals("2")) {
            sp = new Room(name, w, h, f);
        } else {
            sp = new TrafficSpace(name, w, h, f);
        }
        return sp;
    }

    // MODIFIES: this
    // EFFECTS: delete room at a parse index
    private void deleteSpaceProc() {
        spaceList.showAll();
        System.out.println("Which space do you want to delete?(Enter E to exit.)");
        String ss = sc.nextLine();
        if (ss.equals("E")) {
            return;
        }
        if (isParseInt(ss)) {
            int n = Integer.parseInt(ss);
            if (n < 0 || n >= spaceList.getSpaceListSize()) {
                displayIndexError();
                deleteSpaceProc();
            } else {
                deleteSpaceRe(n);
            }
        } else {
            displayIndexError();
            deleteSpaceProc();
        }
    }

    // MODIFIES: this
    // EFFECTS: remove the space from spaceList and floorMap
    private void deleteSpaceRe(int n) {
        Space sp = spaceList.removeSpace(n);
        floorMap.removeFloorSpace(sp);
    }

    //EFFECTS: show the space area
    private void spaceAreaProc() {
        spaceList.showAll();
        System.out.println("Which space area do you want to know?(Enter E to exit.)");
        String ss = sc.nextLine();
        if (ss.equals("E")) {
            return;
        }
        if (isParseInt(ss)) {
            int n = Integer.parseInt(ss);
            if (n < 0 || n >= spaceList.getSpaceListSize()) {
                displayIndexError();
                spaceAreaProc();
            } else {
                System.out.println(spaceList.getSpaceArea(n));
            }
        } else {
            displayIndexError();
            spaceAreaProc();
        }
    }

    //EFFECTS: display index error
    private void displayIndexError() {
        System.out.println("Index error, please re-enter.");
    }

    //EFFECTS: display data error
    private void displayDataError() {
        System.out.println("Data error, please re-enter.");
    }

    // EFFECTS: try to get positive integer from the Input
    private int getPositiveInteger() {
        String str = sc.nextLine();
        if (isParseInt(str)) {
            int n = Integer.parseInt(str);
            if (n > 0) {
                return n;
            }
        }
        displayDataError();
        return getPositiveInteger();
    }

    // EFFECTS: try to get positive double from the Input
    private double getPositiveDouble() {
        String str = sc.nextLine();
        if (isParseDouble(str)) {
            double n = Double.parseDouble(str);
            if (n > 0) {
                return n;
            }
        }
        displayDataError();
        return getPositiveDouble();
    }


    // EFFECTS: return whether a String can be parsed to int
    public static boolean isParseInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // EFFECTS: return whether a String can be parsed to double
    public static boolean isParseDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


