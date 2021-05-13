import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.List;
import java.util.Map;

public class Main {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(Parser.parseFile("src/main/resources/metro.json"));

        Map<String, List<String>> stations = (Map<String, List<String>>) jsonObject.get("stations");
        stations.forEach((key, value) -> System.out.println("Линия " + key +
                " Количество пересадок: " + value.size()));

        List<String> connections = (List<String>) jsonObject.get("connections");
        System.out.println("Количество пересадок: " + connections.size());
    }
}
