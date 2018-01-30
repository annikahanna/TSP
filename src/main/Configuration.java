package main;

import base.City;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public enum Configuration {
    instance;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String dataDirectory = userDirectory + fileSeparator + "TSP" + fileSeparator + "data" + fileSeparator;
    public String dataFilePath = dataDirectory + "TSP280.txt";

    public String databaseFile = dataDirectory + "datastore.db";
    public ArrayList<City> availableCities;
    public MersenneTwisterFast Random = new MersenneTwisterFast();
    public double optimum = 2579;

    public final int CONTENDER_COUNT = 52;
    public final int FIGHT_COUNT = CONTENDER_COUNT / 2;
    public final int ROULETTE_COUNT = 26;
    public final double ASSERT_DELTA = 1e-15;
}