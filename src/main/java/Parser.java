import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Parser {
    private static final String URL = "https://www.moscowmap.ru/metro.html#lines";

    static Document setDocMetro() {
        return Jsoup.parse(parseFile("src/main/resources/MosMetro.html"));
    }

    static String parseFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            lines.forEach(line -> sb.append(line).append("\n"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
}
