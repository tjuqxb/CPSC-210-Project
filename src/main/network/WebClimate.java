package network;

import model.Loadable;
import model.Saveable;
import model.SpaceList;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebClimate implements Loadable, Saveable, Observer {
    String cityName;
    public ArrayList<ClimateData> climateDataList;
    private String keyID = "uWQMjsn3";
    protected String filePos = "./data/Climate.txt";
    protected String singleDataRegex = "(\"[\\w]+\":\\{(?:\"[\\w]+\":\"(?:\\d|\\.)+\"(?:,)?){12}\\})";
    protected String wordRegex = "\"([\\w]+)\"";
    protected String doubleRegex = "\"((?:\\d|\\.)+)\"";
    protected ArrayList<String> months;

    public WebClimate(String cityName) {
        climateDataList = new ArrayList<>();
        months = new ArrayList<>();
        this.cityName = cityName;
    }



    //EFFECTS: get the json file, then return the ID
    public String getStationID(String cityName) throws IOException {
        BufferedReader br = null;
        try {
            String cityName2 = cityName.replace(" ","_");
            String theURL = "https://api.meteostat.net/v1/stations/search?q=" + cityName2 + "&key=" + keyID;
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            return processStationID(sb.toString());
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    // EFFECTS: process the json file to get the station ID
    public String processStationID(String data) throws IOException {
        String re = null;
        String regStr = "\"id\":\"([^\"]+)\",";
        Pattern pat = Pattern.compile(regStr);
        Matcher mat = pat.matcher(data);
        while (mat.find()) {
            re = mat.group(1);
            if (getCityDataFromStation(re).length() > 10) {
                break;
            }
        }
        return re;
    }

    //EFFECTS: according to city name, get the climate string
    public String getCityString(String cityName) throws IOException {
        String code = this.getStationID(cityName);
        return getCityDataFromStation(code);
    }

    //EFFECTS: get the city climate json file, transform it into a String
    private String getCityDataFromStation(String code) throws IOException {
        BufferedReader br = null;
        try {
            String theURL = "https://api.meteostat.net/v1/climate/normals?station=" + code + "&key=" + keyID;
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: process the String to add different Climate data
    public void processCityString(String rawData) {
        Pattern pat = Pattern.compile(this.singleDataRegex);
        Matcher mat = pat.matcher(rawData);
        while (mat.find()) {
            addClimateData(mat.group(1));
        }
    }

    //EFFECTS: indicate whether month data are added
    private boolean isAddMonth() {
        return this.months.size() >= 12;
    }

    //MODIFIES: this
    //EFFECTS: add one certain climate data
    public void addClimateData(String str) {
        Boolean exist = false;
        String dataType = "";
        Pattern patWord = Pattern.compile(wordRegex);
        Matcher matWord = patWord.matcher(str);
        if (matWord.find()) {
            exist = true;
            dataType = matWord.group(1);
        }
        if (!isAddMonth()) {
            for (int i = 0; i < 12; i++) {
                this.months.add(new Integer(i + 1).toString());
            }
        }
        if (exist) {
            addMonthData(str, dataType);
        }
    }

    //MODIFIES: this
    //EFFECTS: add monthly climate datum to one ClimateData
    private void addMonthData(String str, String dataType) {
        ClimateData varData = new ClimateData(dataType);
        Pattern patDouble = Pattern.compile(doubleRegex);
        Matcher matDouble = patDouble.matcher(str);
        while (matDouble.find()) {
            Double toAdd = Double.parseDouble(matDouble.group(1));
            varData.addMonthData(toAdd);
        }
        this.climateDataList.add(varData);
    }

    //MODIFIES: this
    //EFFECTS： update the climate data from the web
    public void getWebData(String cityName) throws IOException {
        this.climateDataList = new ArrayList<>();
        String rawData = this.getCityString(cityName);
        processCityString(rawData);
        this.cityName = cityName;
    }

    //MODIFIES: this
    //EFFECTS： set the city name
    public void setCityName(String city) throws IOException {
        Boolean reset = true;
        if (this.cityName.equals("")) {
            reset = false;
        }
        this.cityName = city;
        if (reset) {
            getWebData(city);
        }
    }

    // EFFECTS: display all the climate data
    public void showData() {
        if (this.climateDataList.size() == 0) {
            return;
        }
        System.out.println(this.cityName + " climate data:");
        System.out.printf("%-20s","");
        for (int i = 0; i < 12; i++) {
            System.out.printf("%-8s", months.get(i));
        }
        System.out.print("\n");
        for (ClimateData wm: this.climateDataList) {
            wm.displayData();
        }
    }

    //EFFECTS: save the climate data to file
    public void save() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(Paths.get(filePos).toFile(),"UTF-8");
        for (ClimateData wm: this.climateDataList) {
            writer.print(wm.name + " ");
            for (Double d: wm.data) {
                writer.print(d + " ");
            }
            writer.print("\n");
        }
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: load the climate data from file
    public int load() throws IOException {
        List<String> allLines = new ArrayList<>();
        try {
            allLines = Files.readAllLines(Paths.get(filePos));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String line: allLines) {
            addFileDataHelper(line);
        }
        return 0;
    }

    //MODIFIES: this
    //EFFECTS: load the climate data from file helper function
    private void addFileDataHelper(String line) {
        ArrayList<String> partsOfLine = SpaceList.splitOnSpace(line);
        if (!this.isAddMonth()) {
            for (int i = 0; i < 12; i++) {
                this.months.add(new Integer(i + 1).toString());
            }
        }
        String name  = partsOfLine.get(0);
        ClimateData d = new ClimateData(name);
        for (int i = 1; i < partsOfLine.size(); i++) {
            d.addMonthData(Double.parseDouble(partsOfLine.get(i)));
        }
        this.climateDataList.add(d);
    }

    //MODIFIES: this
    //EFFECTS： the observer, when location change, climate change
    @Override
    public void update(Observable o, Object arg) {
        try {
            if (!arg.equals(this.cityName) | this.climateDataList.size() == 0) {
                getWebData((String) arg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
