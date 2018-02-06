package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import crossover.ICrossover;
import mutation.IMutation;
import selection.ISelection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GSONUtils {

    private static GsonBuilder gsonBuilder = new GsonBuilder();

    private static String JsonFile = Configuration.instance.userDirectory +
            Configuration.instance.fileSeparator + "configuration" +
            Configuration.instance.fileSeparator + "genetic_algorithm_tsp.json";
    private static String CsvFile = Configuration.instance.userDirectory +
            Configuration.instance.fileSeparator + "configuration" +
            Configuration.instance.fileSeparator + "genetic_algorithm_tsp.csv";
    ;

    static {
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Double.class, (JsonSerializer<Double>) (originalValue, typeOf, context) -> {
            BigDecimal bigValue = BigDecimal.valueOf(originalValue);
            return new JsonPrimitive(bigValue.toPlainString());
        });
        gsonBuilder.registerTypeAdapter(Scenario.class, (JsonSerializer<Scenario>) (scenario, type, jsonSerializationContext) -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("crossover", jsonSerializationContext.serialize(scenario.getCrossover().getClass().getName(), String.class));
            jsonObject.add("mutation", jsonSerializationContext.serialize(scenario.getMutation().getClass().getName(), String.class));
            jsonObject.add("selection", jsonSerializationContext.serialize(scenario.getSelection().getClass().getName(), String.class));
            jsonObject.add("evaluation", jsonSerializationContext.serialize(scenario.getEvaluation(), String.class));
            jsonObject.add("id", jsonSerializationContext.serialize(scenario.getId(), String.class));
            jsonObject.add("maximumNumberOfEvaluations", jsonSerializationContext.serialize(scenario.getMaximumNumberOfEvaluations(), Long.class));
            jsonObject.add("crossoverRatio", jsonSerializationContext.serialize(scenario.getCrossoverRatio(), Double.class));
            jsonObject.add("mutationRatio", jsonSerializationContext.serialize(scenario.getMutationRatio(), Double.class));
            jsonObject.add("buildStatistics", jsonSerializationContext.serialize(scenario.getBuildStatistics(), Boolean.class));
            jsonObject.add("isEvaluated", jsonSerializationContext.serialize(scenario.getEvaluated(), Boolean.class));
            return jsonObject;
        });
        gsonBuilder.registerTypeAdapter(Scenario.class, (JsonDeserializer<Scenario>) (jsonElement, type, jsonDeserializationContext) -> {
            Scenario createdScenario = new Scenario();
            try {
                createdScenario.setCrossover((ICrossover) Class.forName(jsonElement.getAsJsonObject().get("crossover").getAsString()).newInstance());
                createdScenario.setMutation((IMutation) Class.forName(jsonElement.getAsJsonObject().get("mutation").getAsString()).newInstance());
                createdScenario.setSelection((ISelection) Class.forName(jsonElement.getAsJsonObject().get("selection").getAsString()).newInstance());
            } catch (Exception e) {
                throw new JsonParseException(e);
            }
            createdScenario.setEvaluation(jsonElement.getAsJsonObject().get("evaluation").getAsString());
            createdScenario.setId(jsonElement.getAsJsonObject().get("id").getAsString());
            createdScenario.setMaximumNumberOfEvaluations(jsonElement.getAsJsonObject().get("maximumNumberOfEvaluations").getAsLong());
            createdScenario.setCrossoverRatio(jsonElement.getAsJsonObject().get("crossoverRatio").getAsDouble());
            createdScenario.setMutationRatio(jsonElement.getAsJsonObject().get("mutationRatio").getAsDouble());
            createdScenario.setBuildStatistics(jsonElement.getAsJsonObject().get("buildStatistics").getAsBoolean());
            createdScenario.setEvaluated(jsonElement.getAsJsonObject().get("isEvaluated").getAsBoolean());
            return createdScenario;
        });
    }

    /**
     * Don´t use it!
     */
    private GSONUtils() {
    }

    public static List<Scenario> readScenariosFromJsonFile() throws FileNotFoundException {
        Gson gson = gsonBuilder.create();
        JsonReader jsonReader = new JsonReader(new FileReader(JsonFile));
        return Arrays.asList(gson.fromJson(jsonReader, Scenario[].class));
    }

    public static List<Scenario> readScenariosFromCsvFile() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        FileInputStream fStream = new FileInputStream(CsvFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(fStream));
        List<Scenario> scenarios = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] elements = (line + ";").split(";");
            Scenario scenario = new Scenario();
            scenario.setId(elements[0]);
            scenario.setCrossover((ICrossover) Class.forName(elements[1]).newInstance());
            scenario.setMutation((IMutation) Class.forName(elements[3]).newInstance());
            scenario.setSelection((ISelection) Class.forName(elements[5]).newInstance());
            scenario.setCrossoverRatio(Double.parseDouble(elements[2]));
            scenario.setMutationRatio(Double.parseDouble(elements[4]));
            scenario.setEvaluation("BruteForce"); //TODO was hiermit ?!
            scenario.setBuildStatistics(true);
            scenario.setEvaluated(false);
            scenario.setMaximumNumberOfEvaluations(1000000l);
            scenarios.add(scenario);
        }
        fStream.close();
        br.close();
        return scenarios;
    }

    public static void storeScenariosInJsonFile(List<Scenario> scenariosToStore) throws IOException {
        Writer writer = new FileWriter(JsonFile);
        writer.write(gsonBuilder.create().toJson(scenariosToStore));
        writer.close();
    }

}
