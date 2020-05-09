package ui;

import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class MapDisplay extends JDialog {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    public MapDisplay() {
        super();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where the map will display
    private void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH / 4, HEIGHT / 4));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS:  get the map data from web and display
    public void displaySite(String city, String place) {
        String city2 = city.replace(" ", "+");
        String place2 = place.replace(" ","+");
        String combination = place2 + "," + city2;
        String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + combination + "&zoom=17&size=800x600&maptype=roadmap"
                + "&key="
                + "AIzaSyA0YlzbJvU3Sm14zHa8e5r-Wr6rH8G0ZMM";
        readMap(imageUrl);
        ImageIcon imageIcon = new ImageIcon((new ImageIcon("image.jpg"))
                .getImage().getScaledInstance(1000, 800,
                        java.awt.Image.SCALE_SMOOTH));
        this.add(new JLabel(imageIcon));
        this.setVisible(true);
        this.pack();
    }

    // MODIFIES: this
    // EFFECTS:  save the data to jpg
    private void readMap(String imageUrl) {
        try {
            String destinationFile = "image.jpg";
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
