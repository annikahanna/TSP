package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class GSONUtils {

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    static {
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Double.class, (JsonSerializer<Double>) (originalValue, typeOf, context) -> {
            BigDecimal bigValue = BigDecimal.valueOf(originalValue);
            return new JsonPrimitive(bigValue.toPlainString());
        });
    }

    /**
     * Don´t use it!
     */
    private GSONUtils() {
    }

    public static List<Scenario> readScenariosFromFile() throws FileNotFoundException {
        Gson gson = gsonBuilder.create();
        JsonReader jsonReader = new JsonReader(new FileReader(Configuration.instance.userDirectory +
                Configuration.instance.fileSeparator + "configuration" +
                Configuration.instance.fileSeparator + "genetic_algorithm_tsp.json"));
        return Arrays.asList(gson.fromJson(jsonReader, Scenario[].class));
    }


    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(gsonBuilder.create().toJson(readScenariosFromFile()));
    }
}
