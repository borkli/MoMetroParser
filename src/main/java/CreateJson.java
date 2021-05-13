import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.Map;

public class CreateJson {

    private static final String JSON_FILE = "src/main/resources/metro.json";
    private static final JSONObject metro = new JSONObject();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @SuppressWarnings("unchecked")
    static void JsonStations() {
        JSONObject line = new JSONObject();
        ParsMetro.getStations().forEach(line::put);
        metro.put("stations", line);
    }

    @SuppressWarnings("unchecked")
    static void JsonLine() {
        JSONArray line = new JSONArray();
        for (Map.Entry<String, String> parLine : ParsMetro.getLines().entrySet()) {
            JSONObject nameLine = new JSONObject();
            nameLine.put("number", parLine.getKey());
            nameLine.put("name", parLine.getValue());
            line.add(nameLine);
        }
        metro.put("Line", line);
    }

    @SuppressWarnings("unchecked")
    static void JsonConnections() {
        JSONArray connections = new JSONArray();
        for (String parsConnection : ParsMetro.getConnections()) {
            String[] arrConnection = parsConnection.split("-");

            JSONArray connection = new JSONArray();
            JSONObject station1 = new JSONObject();
            JSONObject station2 = new JSONObject();

            station1.put("line", arrConnection[0]);
            station1.put("name", arrConnection[1]);
            station2.put("line", arrConnection[2]);
            station2.put("name", arrConnection[3]);

            connection.add(station1);
            connection.add(station2);
            connections.add(connection);
        }
        metro.put("connections", connections);
    }

    static void createFile() {
        JsonStations();
        JsonLine();
        JsonConnections();
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write(GSON.toJson(metro));
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
