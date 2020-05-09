package model;

import exceptions.CoverageRatioException;
import exceptions.PlotRatioException;
import network.WebClimate;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SpaceList extends JPanel implements Loadable, Saveable {
    public  LandInfo landInfo = new LandInfo();
    public WebClimate webClimate = new WebClimate("");
    public ArrayList<Space> spList;
    public String name;
    public String filePos;
    private Image image = null;
    int area = 0;
    double imageRatio = 1;
    Icon icon;
    boolean isLandLoad = false;
    int trCnt = 0;
    int roCnt = 0;
    int floor = 1;
    ArrayList<TwoPtPaths> multiPointsRe;
    public TwoPtPaths twoPointRe;
    public ArrayList<Line2D> lines;


    public SpaceList(String name) {
        super();
        this.name = name;
        this.spList = new ArrayList<Space>();
        filePos = "./data/SpaceList.txt";
        setBackground(Color.white);
        multiPointsRe = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public SpaceList(String pos, String name) {
        this.spList = new ArrayList<Space>();
        filePos = pos;
        this.name = name;
        setBackground(Color.white);
        multiPointsRe = new ArrayList<>();
        lines = new ArrayList<>();
    }

    // EFFECTS: show all the space's index and name
    public void showAll() {
        for (Space sp: spList) {
            System.out.println(spList.indexOf(sp) + " " + sp.getName());
            sp.spaceDescription();
        }
    }

    // MODIFIES: this
    // EFFECTS: add one space to the SpaceList
    public void addSpace(Space sp) throws PlotRatioException, CoverageRatioException {
        if (!spList.contains(sp)) {
            spList.add(sp);
            sp.addSpaceList(this);
        }
    }

    // REQUIRES: i >= 0
    // MODIFIES: this
    // EFFECTS: remove one space from the SpaceList according to the index
    public Space removeSpace(int i) {
        Space sp = spList.get(i);
        spList.remove(i);
        sp.removeSpaceList(this);
        return sp;
    }

    // MODIFIES: this
    //EFFECTS: remove one space from the SpaceList
    public void removeSpace(Space sp) {
        if (spList.contains(sp)) {
            spList.remove(sp);
            sp.removeSpaceList(this);
        }
    }

    // EFFECTS: return the SpaceList length
    public int getSpaceListSize() {
        return spList.size();
    }

    // EFFECTS: return the specific space
    public Space getSpace(int i) {
        return spList.get(i);
    }

    // EFFECTS: return the specific space area
    public int getSpaceArea(int i) {
        return spList.get(i).getArea();
    }

    // EFFECTS: return the whole building area
    public int getTotBuildArea() {
        int area = 0;
        for (Space sp : spList) {
            area += sp.getBuildingArea();
        }
        return area;
    }

    // MODIFIES: this
    // EFFECTS: load the data from file and give values to spList
    public int load() throws IOException, ArrayIndexOutOfBoundsException, NumberFormatException {
        webClimate.load();
        List<String> allLines = Files.readAllLines(Paths.get(filePos));
        for (String line: allLines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            if (!isLandLoad) {
                landInfoLoad(partsOfLine);
                isLandLoad = true;
            } else {
                getSpaceLoad(partsOfLine);
            }
        }
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: load one space
    private void getSpaceLoad(ArrayList<String> partsOfLine) {
        String name = partsOfLine.get(0);
        int x = Integer.parseInt(partsOfLine.get(1));
        int y = Integer.parseInt(partsOfLine.get(2));
        int width = Integer.parseInt(partsOfLine.get(3));
        int height = Integer.parseInt(partsOfLine.get(4));
        String floor = partsOfLine.get(5);
        String isY = partsOfLine.get(6);
        Space one = loadHelper(name, x, y, width, height, floor, isY);
        try {
            addSpace(one);
        } catch (PlotRatioException e) {
            e.printStackTrace();
        } catch (CoverageRatioException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this.landinfo
    // EFFECTS: load land information
    private void landInfoLoad(ArrayList<String> partsOfLine) throws IOException {
        landInfo.setRatios(partsOfLine);
        webClimate.setCityName(landInfo.city);
        landInfo.addObserver(webClimate);
    }


    // EFFECTS: return the right space with right type
    public static Space loadHelper(String name, int x, int y, int width, int height, String f, String isY) {
        Space one;
        if (isY.equals("COURT_YARD")) {
            one = new CourtYard(name, x, y, width, height, f);
        } else if (isY.equals("ROOM")) {
            one = new Room(name, x, y, width, height, f);
        } else {
            one = new TrafficSpace(name, x, y, width, height, f);
        }
        return one;
    }

    // EFFECTS: save all the values from spList to file
    public void save() {
        try {
            webClimate.save();
            PrintWriter writer = new PrintWriter(Paths.get(filePos).toFile(),"UTF-8");
            writer.println(landInfo.toString());
            for (Space sp: spList) {
                sp.save(writer);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: transform the input String to ArrayList<String>
    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

    //EFFECTS: return this.name
    public  String getName() {
        return this.name;
    }

    //EFFECTS: override equals
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SpaceList spaceList = (SpaceList) o;
        return name.equals(spaceList.getName()) && spList.equals(spaceList.spList);
    }

    //EFFECTS: override hashcode
    @Override
    public int hashCode() {
        int re;
        re = this.name.hashCode();
        re = re * 31 + this.spList.hashCode();
        return re;
    }

    // EFFECTS: paints background and spaces in drawing
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image != null) {
            g.drawImage(image, 0, 0, icon.getIconWidth() * this.getHeight()
                    / icon.getIconHeight(), this.getHeight(), this);
        }
        for (Space shape : spList) {
            shape.draw(g);
        }
        Color save = g.getColor();
        g.setColor(Color.red);
        for (Line2D line: lines) {
            g.drawLine((int)line.getX1(), (int)line.getY1(), (int)line.getX2(),(int)line.getY2());
        }
        g.setColor(save);
    }

    // MODIFIES: this
    // EFFECTS: set the background image
    public void setImage(String str) {
        ImageIcon imageIcon = new ImageIcon(str);
        icon = imageIcon;
        this.image = new ImageIcon(str).getImage();
        if (this.area != 0) {
            setImageRatio(area);
        }
    }

    //MODIFIES: this
    //EFFECTS: set the ratio real distance / pixels distance
    public void setImageRatio(int area) {
        this.area = area;
        if (this.image != null) {
            this.imageRatio = Math.sqrt((double)area / (double) (icon.getIconWidth() * icon.getIconHeight()));
        }
    }

    //EFFECTS: create a different traffic space
    public TrafficSpace createTrafficSpace(Point pt) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String nm = df.format(new Date());
        String f = Integer.toString(floor);
        TrafficSpace tr = new TrafficSpace(nm,f,pt);
        trCnt += 1;
        return tr;
    }

    //EFFECTS: create a different room
    public Room createRoom(Point pt) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String nm = df.format(new Date());
        String f = Integer.toString(floor);
        Room ro = new Room(nm,f,pt);
        roCnt += 1;
        return ro;
    }

    //MODIFIES: space
    //EFFECTS； remove connections of each space
    public void removeConnections() {
        for (Space sp: spList) {
            sp.removeConnections();
        }
    }

    //MODIFIES: space
    //EFFECTS: build connections through each space
    public void buildConnections() {
        for (Space sp: spList) {
            if (sp instanceof TrafficSpace) {
                int i = 0;
                for (Space sp2: spList) {
                    if (!sp.equals(sp2) && sp.getRectangle().intersects(sp2.getRectangle())) {
                        sp.addTrafficConnection(sp2);
                        i++;
                    }
                }
                System.out.println(i);
            }
        }
    }

    //MODIFIES: this, records
    //EFFECTS: calculate the results between two spaces and store the value in tempCals and lines
    public void calculateTwoSpaces(Space start, Space end) {
        this.lines = new ArrayList<>();
        removeConnections();
        buildConnections();
        twoPointRe = new TwoPtPaths(start, end);
        start.searchTrace(end);
        twoPointRe.records.sort(SinglePath::compareTo);
        ArrayList<Line2D> trace2 = twoPointRe.records.get(0).sequence;
        for (int i = 0; i < trace2.size(); i++) {
            lines.add(trace2.get(i));
        }
    }

    // EFFECTS: returns the space at a given Point, if any
    public Space getSpaceAtPoint(Point point) {
        for (Space space : spList) {
            if (space.getRectangle().contains(point)) {
                return space;
            }
        }
        return null;
    }
}
