package ml.u_finder.u_finder;

import android.graphics.Point;

import java.io.StringReader;
import java.util.LinkedList;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Coordinates {

    JsonObject json;
    private int size = 0;
    private String jsonString;


    public Coordinates(String jsonString) {

        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        json = jsonReader.readObject();
        jsonReader.close();
        size = json.getJsonArray("coordinates").size();


    }

    public Point getCoordinate(int index) {
        Point r = new Point(0, 0);
        if (!(index > size)) {
            String x = json.getJsonArray("coordinates").getJsonObject(index).get("X").toString();
            String y = json.getJsonArray("coordinates").getJsonObject(index).get("Y").toString();
            r.set(Integer.parseInt(x), Integer.parseInt(y));
            return r;
        }
        return new Point(0, 0);
    }

    public int getX(int index) {

        if (!(index > size)) {
            String x = json.getJsonArray("coordinates").getJsonObject(index).get("X").toString();
            return Integer.parseInt(x);
        }
        return 0;
    }

    public int getY(int index) {
        if (!(index > size)) {
            String y = json.getJsonArray("coordinates").getJsonObject(index).get("Y").toString();
            return Integer.parseInt(y);
        }
        return 0;
    }

    public String getName(int index) {
        if (!(index > size)) {
            String name = json.getJsonArray("coordinates").getJsonObject(index).get("name").toString();
            return name.replaceAll("\"", "");
        }
        return "...";
    }


    public int getSize() {
        return size;
    }

    public String getJsonString() {
        return jsonString;
    }

}
