package data;

import java.sql.*;
import java.util.ArrayList;

import main.Configuration;

public enum HSQLDBManager {
    instance;

    private Connection connection;
    private String driverName = "jdbc:hsqldb:";
    private String username = "sa";
    private String password = "";

    public void startup() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String databaseURL = driverName + Configuration.instance.databaseFile;
            connection = DriverManager.getConnection(databaseURL,username,password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void init() {
        dropTable();
        createTable();
    }

    public synchronized void update(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlStatement);
            if (result == -1)
                System.out.println("error executing " + sqlStatement);
            statement.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public void dropTable() {
        System.out.println("--- dropTable");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE data");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
    }

    public void createTable() {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE data ").append(" ( ");
        sqlStringBuilder.append("id BIGINT NOT NULL").append(",");
        sqlStringBuilder.append("test VARCHAR(20) NOT NULL").append(",");
        sqlStringBuilder.append("PRIMARY KEY (id)");
        sqlStringBuilder.append(" )");
        update(sqlStringBuilder.toString());
    }

    public String buildSQLStatement(long id, long iteration, double fitness, int scenarioId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO data (id, iteration, fitness, scenario) VALUES (");
        stringBuilder.append(id).append(",");
        stringBuilder.append(iteration).append(",");
        stringBuilder.append(fitness).append(",");
        stringBuilder.append(scenarioId);
        stringBuilder.append(")");
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

 /*   public void insert(String test) {
        update(buildSQLStatement(System.nanoTime(),test));
    }*/

    public void shutdown() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            connection.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public ArrayList<Integer> selectData(String scenarioID)
    {
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT fitnessValue FROM data WHERE scenarioID = '" + scenarioID + "'";
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            while (resultSet.next()) {
                //System.out.println(resultSet.getInt(1));
                result.add(resultSet.getInt(1));
            }
            System.out.println(result);
            return result;
        }catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        return null;
    }


}