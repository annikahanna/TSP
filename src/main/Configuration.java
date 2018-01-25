package main;

import random.MersenneTwisterFast;

public enum Configuration {
    instance;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    public String dataFilePath = dataDirectory + "TSP280.txt";

    public String databaseFile = dataDirectory + "datastore.db";

    public MersenneTwisterFast Random = new MersenneTwisterFast();

    public final int FIGHT_COUNT = 52;
    public final int ROULETTE_COUNT = 26;
    public final double ASSERT_DELTA = 1e-15;
}