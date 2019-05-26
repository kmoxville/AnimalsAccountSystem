package com.kmoxvilleEntertainmentGroup;

import com.kmoxvilleEntertainmentGroup.DataProviders.DataProvider;
import com.kmoxvilleEntertainmentGroup.DataProviders.DataProviderParseException;
import com.kmoxvilleEntertainmentGroup.Utils.ExitCodes;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import static com.kmoxvilleEntertainmentGroup.Utils.HelperUtils.*;

class MainEngine {

    private final DataProvider dataProvider;
    private final String rule;
    private Connection dbConn;

    MainEngine(DataProvider dataProvider, String rule) {
        this.dataProvider = dataProvider;
        this.rule = rule;
    }

    void run() throws DataParseException, RuleParseException, DataProviderParseException {
        Set<Animal> animalsSet = dataProvider.readAllAnimals();
        Map<String, Set<String>> propsSet = dataProvider.readAllProperties();

        initDBEngine();
        createTables(animalsSet, propsSet);
        countAnimalsByRule(rule);
        shutdownDBEngine();
    }

    private void countAnimalsByRule(String rule) throws RuleParseException {
        try {
            Statement stmt = dbConn.createStatement();
            String sql = "SELECT COUNT(Название) FROM Животные WHERE "; //FIXME
            ResultSet rs = stmt.executeQuery(sql + rule);
            print(String.valueOf(rs.getInt(1)));
        }
        catch (SQLException e) {
            throw new RuleParseException();
        }
    }

    private void executeSQLStatement(String sql) {
        try (Statement stmt = dbConn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e); //Unchecked exception to bypass lambda restriction
        }
    }

    private void createTables(Set<Animal> animalsSet, Map<String, Set<String>> properties)
            throws DataParseException {

        try {
            createPropertiesTables(properties);
            createAnimalsTables(animalsSet);
        }
        catch (Exception e) {
            throw new DataParseException();
        }
    }

    //Exception in anonymous inner class = cancer
    private void createPropertiesTables(Map<String, Set<String>> properties) {
        properties.entrySet().iterator().forEachRemaining((entry) -> {
            String fmtStr = MessageFormat.format("CREATE TEMP TABLE {0} " +
                                                    "(Значения varchar(20) PRIMARY KEY);", entry.getKey());
            executeSQLStatement(fmtStr);

            StringBuilder sql = new StringBuilder();
            sql.append(MessageFormat.format((   "INSERT INTO {0} " +
                                                "VALUES "), entry.getKey()));

            entry.getValue().iterator().forEachRemaining((value) ->
                sql.append(MessageFormat.format("(''{0}''),", value))
            );
            sql.replace(sql.length() - 2, sql.length(), ");");

            executeSQLStatement(sql.toString());
        });
    }

    private void createAnimalsTables(Set<Animal> animals) {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TEMP TABLE Животные ( Название varchar(20), ");

        animals.iterator().next().properties().entrySet().iterator().forEachRemaining((entry) ->
            sql.append(MessageFormat.format("{0} varchar(20), ", entry.getKey()))
        );

        animals.iterator().next().properties().entrySet().iterator().forEachRemaining((entry) ->
            sql.append(MessageFormat.format("FOREIGN KEY({0}) REFERENCES {0}(Значения), ", entry.getKey()))
        );

        sql.replace(sql.length() - 2, sql.length(), " );  ");

        executeSQLStatement(sql.toString());
        sql.setLength(0);
        sql.append("INSERT INTO Животные VALUES ");
        animals.iterator().forEachRemaining((animalEntry) -> {
            sql.append(MessageFormat.format("( ''{0}'', ", animalEntry.getName()));
            animalEntry.properties().entrySet().iterator().forEachRemaining((propEntry) ->
                sql.append(MessageFormat.format("''{0}'', ", propEntry.getValue()))
            );
            sql.replace(sql.length() - 2, sql.length(), "), ");
        });
        sql.replace(sql.length() - 3, sql.length(), "); ");

        executeSQLStatement(sql.toString());

    }

    private void initDBEngine() {
        try {
            String url = "jdbc:sqlite:animals.db";
            dbConn = DriverManager.getConnection(url);
            Statement stmt = dbConn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            //That should never happen
            print("SQLite init error" + e.getMessage());
            exit(ExitCodes.CriticalError);
        }
    }

    private void shutdownDBEngine() {
        try {
            dbConn.close();
        } catch (SQLException e) {
            //That should never happen
            exit(ExitCodes.CriticalError);
        }
    }
}
