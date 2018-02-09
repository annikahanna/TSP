package statistics;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import data.HSQLDBManager;


public class Statistics implements IStatistics {

    private ArrayList<String> scenarios;
    private boolean median;
    private boolean mean;
    private boolean sd;
    private boolean range;
    private boolean interquartilsrange;
    private boolean quantile;
    private double quantileStart;
    private double quantileEnd;
    private double iqr;
    private boolean quantileTo;
    private boolean quantileRange;

    //creates csv Files for each scenario
    //gets the data from the database
    public void writeCSVFile() {
        HSQLDBManager.instance.startup();
        int numberScenarios = 0;
        ResultSet rs_scenarios = HSQLDBManager.instance.selectData("select count(DISTINCT scenario) from data;");
        try {
            rs_scenarios.next();
            numberScenarios = rs_scenarios.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (int i = 1; i <= numberScenarios; i++) {
            ResultSet rs = HSQLDBManager.instance.selectData("SELECT * FROM DATA WHERE scenario=" + i);
            try {
                PrintWriter writer = new PrintWriter(new File("data/data_scenario_" + i + ".csv"));
                PrintWriter barplotwriter = new PrintWriter(new File("data/data_scenario_" + i + "_barplot.csv"));
                while (rs.next()) {
                    writer.print(rs.getDouble("fitness") + ",");
                    barplotwriter.print(rs.getDouble("fitness"));

                }
                writer.flush();
                barplotwriter.flush();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //methods for deploying different R scripts
    public void buildMeasureRFile(ArrayList<String> scenarios) {
        List<Integer> scenario_ids = createScenarios(scenarios);
        try {
            String measurefile = Config.instance.buildFileBeginning(scenario_ids, "src/statistics/templates/measures.R.tpl");

            measurefile = median ? measurefile.replaceAll(Config.VAR_MEDIAN, Config.instance.createMedianScenario(scenario_ids)) : measurefile.replaceAll(Config.VAR_MEDIAN, "");
            measurefile = mean ? measurefile.replaceAll(Config.VAR_MEAN, Config.instance.createMeanScenario(scenario_ids)) : measurefile.replaceAll(Config.VAR_MEAN, "");
            measurefile = sd ? measurefile.replaceAll(Config.VAR_SD, Config.instance.createSdScenario(scenario_ids)) : measurefile.replaceAll(Config.VAR_SD, "");
            measurefile = range ? measurefile.replaceAll(Config.VAR_RANGE, Config.instance.createRangeScenario(scenario_ids)) : measurefile.replaceAll(Config.VAR_RANGE, "");
            measurefile = interquartilsrange ? measurefile.replaceAll(Config.VAR_INTERQUARTILERANGE, Config.instance.createInterquartilerangeScenario(scenario_ids)) : measurefile.replaceAll(Config.VAR_INTERQUARTILERANGE, "");

            String quantileText = "";
            if (quantile) quantileText += Config.instance.createQuantile(scenario_ids, quantileStart) + "\n";
            if (quantileTo)
                quantileText += Config.instance.createQuantileTo(scenario_ids, quantileStart, quantileEnd) + "\n";
            if (quantileRange)
                quantileText += Config.instance.createQuantileRange(scenario_ids, quantileStart, quantileEnd) + "\n";
            measurefile = measurefile.replaceAll(Config.VAR_QUANTILE, quantileText);

            Config.instance.writeFile(measurefile, new File(Config.instance.measure_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildBarPlotFile() {
        try {
            String barplot = new String(Files.readAllBytes(Paths.get("src/statistics/templates/barplot.R.tpl")));
            barplot = barplot.replaceAll(Config.VAR_FILENAME, Config.instance.path);
            barplot = barplot.replaceAll(Config.VAR_SCENARIODESCRIPTION, Config.instance.getScenariodescription_barplot());
            Config.instance.writeFile(barplot, new File(Config.instance.barplot_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildBoxPlotRFile(ArrayList<String> scenarios) {
        List<Integer> scenario_ids = createScenarios(scenarios);
        try {
            String boxplot = Config.instance.buildFileBeginning(scenario_ids, "src/statistics/templates/boxplot.R.tpl");
            boxplot = boxplot.replaceAll(Config.VAR_FILENAME, Config.instance.createBoxplotName(scenario_ids));
            boxplot = boxplot.replaceAll(Config.VAR_SCENARIOSHORT, Config.instance.createScenarioShortname(scenario_ids));
            boxplot = boxplot.replaceAll(Config.VAR_NAMES, Config.instance.createScenarioName(scenario_ids));
            Config.instance.writeFile(boxplot, new File(Config.instance.boxplot_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void buildDotPlotRFile(ArrayList<String> scenarios) {
        List<Integer> scenario_ids = createScenarios(scenarios);

        try {
            String dotplot = Config.instance.buildFileBeginning(scenario_ids, "src/statistics/templates/dotplot.R.tpl");
            dotplot = dotplot.replaceAll(Config.VAR_FILENAME, Config.instance.createDotplotName(scenario_ids));
            dotplot = dotplot.replaceAll(Config.VAR_DOTPLOTSCENARIO, Config.instance.createDotplotScenarios(scenario_ids));

            Config.instance.writeFile(dotplot, new File(Config.instance.dotplox_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildStripChartRFile(ArrayList<String> scenarios) {
        List<Integer> scenario_ids = createScenarios(scenarios);
        try {
            String stripchart = Config.instance.buildFileBeginning(scenario_ids, "src/statistics/templates/stripchart.R.tpl");
            stripchart = stripchart.replaceAll(Config.VAR_FILENAME, Config.instance.createStripchartName(scenario_ids));
            stripchart = stripchart.replaceAll(Config.VAR_STRIPCHARTSCENARIOS, Config.instance.createStripchartScenarios(scenario_ids));
            Config.instance.writeFile(stripchart, new File(Config.instance.stripchart_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildTTestRFile(ArrayList<String> scenarios) {
        List<Integer> scenario_ids = createScenarios(scenarios);
        try {
            String ttest = Config.instance.buildFileBeginning(scenario_ids, "src/statistics/templates/t_test.R.tpl");
            ttest = ttest.replaceAll(Config.VAR_TTESTSCENARIOS, Config.instance.createTTestText(scenario_ids));

            Config.instance.writeFile(ttest, new File(Config.instance.ttest_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildHistogramRFile(ArrayList<String> scenarios) {
        List<Integer> scenario_ids = createScenarios(scenarios);
        try {
            String histogram = Config.instance.buildFileBeginning(scenario_ids, "src/statistics/templates/histogram.R.tpl");
            histogram = histogram.replaceAll(Config.VAR_MINANDMAX, Config.instance.createHistogramMinAndMax(scenario_ids));
            histogram = histogram.replaceAll(Config.VAR_HISTOGRAMSCENARIOS, Config.instance.createHistogramScenarios(scenario_ids));
            Config.instance.writeFile(histogram, new File(Config.instance.histogram_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //mff
    public void buildMostFrequentFitnessValuesRFile(ArrayList<String> scenarios) {
        List<Integer> scenario_ids = createScenarios(scenarios);
        try {
            String mff = Config.instance.buildFileBeginning(scenario_ids, "src/statistics/templates/mff.R.tpl");
            mff = mff.replaceAll(Config.VAR_MFFSCENARIOS, Config.instance.createMffs(scenario_ids));
            Config.instance.writeFile(mff, new File(Config.instance.mff_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //transfor String scenario id to integer
    private List<Integer> createScenarios(ArrayList<String> scenarios) {
        List<Integer> scenario_ids = new ArrayList<Integer>();
        for (String scenario : scenarios) {
            scenario_ids.add(Integer.parseInt(scenario.substring(0)));
        }
        return scenario_ids;
    }

    public static void main(String[] args) {
        Statistics stats = new Statistics();
        stats.writeCSVFile();
        stats.generateParams();
        System.out.println("R code generated");
        stats.startupHSQLDB();
    }

    //evaluates the input and chooses right option to generate script
    private void generateParams() {
        System.out.println("Which statistic do you want to have? Your command should be in the form of:");
        System.out.println("-d [<scenarioIDs> | all] -m [median | mean | quantile = <n> | range | iqr = <n> | sd]" +
                "-p [bar | box | dot | hist | strip] -t -a [mff]");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        String[] parts = command.split(" ");
        scenarios = new ArrayList<String>();
        for (int i = 0; i < parts.length; i++) {
            //-d part
            if (parts[i].startsWith("-d")) {
                for (int j = i + 1; j < parts.length; j++) {
                    //all
                    if (parts[j].startsWith("all")) {
                        for (int k = 1; k < 25; k++) {
                            scenarios.add("s" + k);
                        }
                        break;
                    }
                    //<scenarioIDs>
                    if (!parts[j].startsWith("-")) {
                        for (String s : parts[j].split(",")) {
                            scenarios.add(s);
                        }
                    } else {
                        break;
                    }
                }
                //-m part
            } else if (parts[i].startsWith("-m")) {
                for (int j = i + 1; j < parts.length; j++) {
                    if (!parts[j].startsWith("-")) {
                        if (parts[j].startsWith("median")) median = true;
                        if (parts[j].startsWith("mean")) mean = true;
                        if (parts[j].startsWith("range")) range = true;
                        if (parts[j].startsWith("sd")) sd = true;
                        if (parts[j].startsWith("iqr")) {
                            interquartilsrange = true;
                        }
                        if (parts[j].startsWith("quantile")) {
                            if (parts[j].matches("quantile=0\\.[0-9]+")) {
                                quantile = true;
                                quantileStart = Double.parseDouble(parts[j].substring(parts[j].lastIndexOf("=") + 1));
                            } else if (parts[j].matches("quantile=0\\.[0-9]+-0\\.[0-9]+")) {
                                quantileRange = true;
                                quantileStart = Double.parseDouble(parts[j].substring(parts[j].lastIndexOf("=") + 1, parts[j].lastIndexOf("-")));
                                quantileEnd = Double.parseDouble(parts[j].substring(parts[j].lastIndexOf("-") + 1));
                            } else if (parts[j].matches("quantile=0\\.[0-9]+,0\\.[0-9]+")) {
                                quantileTo = true;
                                quantileStart = Double.parseDouble(parts[j].substring(parts[j].lastIndexOf("=") + 1, parts[j].lastIndexOf(",")));
                                quantileEnd = Double.parseDouble(parts[j].substring(parts[j].lastIndexOf(",") + 1));
                            }
                        }
                    } else {
                        break;
                    }
                }
                //build first script for the data
                buildMeasureRFile(scenarios);
                //-p part generates script for plotting pdfs with statistic
            } else if (parts[i].startsWith("-p")) {
                for (int j = i + 1; j < parts.length; j++) {
                    if (!parts[j].startsWith("-")) {
                        if (parts[j].startsWith("bar"))
                            buildBarPlotFile();
                        if (parts[j].startsWith("box"))
                            buildBoxPlotRFile(scenarios);
                        if (parts[j].startsWith("dot"))
                            buildDotPlotRFile(scenarios);
                        if (parts[j].startsWith("hist"))
                            buildHistogramRFile(scenarios);
                        if (parts[j].startsWith("strip"))
                            buildStripChartRFile(scenarios);
                    } else {
                        break;
                    }
                }
                //-t part build tests
            } else if (parts[i].startsWith("-t")) {
                buildTTestRFile(scenarios);
                //-a part builds mff script
            } else if (parts[i].startsWith("-a")) {
                for (int j = i + 1; j < parts.length; j++) {
                    if (!parts[j].startsWith("-")) {
                        if (parts[j].startsWith("mff")) {
                            buildMostFrequentFitnessValuesRFile(scenarios);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }


    private void startupHSQLDB() {
        HSQLDBManager.instance.startup();
    }

}