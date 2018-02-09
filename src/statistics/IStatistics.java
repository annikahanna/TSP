package statistics;

import java.util.ArrayList;

public interface IStatistics {
    void writeCSVFile();
    void buildMeasureRFile(ArrayList<String> scenarios);
    void buildBarPlotFile();
    void buildBoxPlotRFile(ArrayList<String> scenarios);
    void buildDotPlotRFile(ArrayList<String> scenarios);
    void buildHistogramRFile(ArrayList<String> scenarios);
    void buildStripChartRFile(ArrayList<String> scenarios);
    void buildTTestRFile(ArrayList<String> scenarios);
    void buildMostFrequentFitnessValuesRFile(ArrayList<String> scenarios);
}