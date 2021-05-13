import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class ParsMetro {

    static Map<String, String> getLines() {
        Elements lines = Parser.setDocMetro().select("span.js-metro-line");
        Map<String, String> linesMap = new TreeMap<>(sortMap());

        if (lines.size() > 0) {
            for (Element line : lines) {
                String[] nameLine = line.toString().split(">|<");
                linesMap.put(line.attr("data-line"), nameLine[2]);
            }
        }
        return linesMap;
    }

    static Map<String, List<String>> getStations() {
        Map<String, List<String>> stationsMap = new TreeMap<>(sortMap());
        Elements stationsAndLine = Parser.setDocMetro().select("div.js-metro-stations");

        if (stationsAndLine.size() > 0) {
            for (Element line : stationsAndLine) {
                String numLine = line.attr("data-line");
                Elements stations = line.select("span.name");
                List<String> stationsLine = new ArrayList<>();
                for (Element station : stations) {
                    String[] parStation = station.toString().split(">|<");
                    stationsLine.add(parStation[2]);
                    stationsMap.put(numLine, stationsLine);
                }
            }
        }
        return stationsMap;
    }

    static List<String> getConnections() {
        List<String> stationsList = new ArrayList<>();
        Elements metro = Parser.setDocMetro().select("div.js-metro-stations");
        Elements stations = metro.select("a");

        if (stations.size() > 0) {
            for (Element station : stations) {
                Elements stationEl = station.select("span.name");
                String[] nameArr = stationEl.toString().split(">|<");
                String nameStation = nameArr[2];

                Elements connectionsEl = station.select("span.t-icon-metroln");
                for (Element connection : connectionsEl) {
                    String numConnectionLine = connection.toString().replaceAll("[^A-Z0-9]", "");
                    String nameConnection = connection.toString().replaceAll(".*\\«|\\».*", "");
                    String numLine = "";
                    for (Map.Entry<String, List<String>> stationsMap : ParsMetro.getStations()
                            .entrySet()) {
                        if (stationsMap.getValue().contains(nameStation)) {
                            numLine = stationsMap.getKey();
                            break;
                        }
                    }
                    if (!numLine.equals(numConnectionLine)) {
                        String connectionStr = numLine + "-" + nameStation +
                                "-" + numConnectionLine + "-" + nameConnection;
                        stationsList.add(connectionStr);
                    }
                }
            }
        }
        return stationsList;
    }

    static Comparator<String> sortMap() {
        return ((o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            return 1;
        });
    }
}
