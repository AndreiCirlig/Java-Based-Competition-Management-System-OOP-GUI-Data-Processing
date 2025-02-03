
import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class CompetitionSystem {
    private static final String JSON_FILE_PATH = "compe.json";

    private static final Gson gson = new Gson();

    public static List<Competitor> loadCompetitorsFromJson() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
            return gson.fromJson(json, new TypeToken<List<Competitor>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void loadCompetitors() {
        try {
            String json = Files.readString(Paths.get(JSON_FILE_PATH), StandardCharsets.UTF_8);
            List<Competitor> competitors = gson.fromJson(json, new TypeToken<List<Competitor>>() {
            }.getType());
        } catch (IOException e) {

        }
    }



}
