package com.kmoxvilleEntertainmentGroup;

import com.kmoxvilleEntertainmentGroup.DataProviders.AnimalsDataProvider;
import com.kmoxvilleEntertainmentGroup.DataProviders.DataProviderParseException;
import com.kmoxvilleEntertainmentGroup.DataProviders.PropertiesDataProvider;
import com.kmoxvilleEntertainmentGroup.Utils.Options;

import java.sql.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import static com.kmoxvilleEntertainmentGroup.Utils.HelperUtils.*;

class MainEngine {
    private PropertiesDataProvider propProvider;
    private AnimalsDataProvider animalsProvider;
    private Connection dbConn;

    MainEngine(AnimalsDataProvider animalsProvider, PropertiesDataProvider propProvider) {
        this.animalsProvider = animalsProvider;
        this.propProvider = propProvider;
    }

    void run() throws DataProviderParseException {
        Set<Animal> animalsSet = animalsProvider.readAllData();
        Map<String, Set<String>> propsSet = propProvider.readAllData();

        connect();
        createTables(animalsSet, propsSet);
        countAnimalsByRule(Options.getRule());
        disconnect();
    }

    private void countAnimalsByRule(String rule) {
        try {
            Statement stmt = dbConn.createStatement();
            String sql = "SELECT COUNT(Название) FROM Животные WHERE "; //FIXME
            ResultSet rs = stmt.executeQuery(sql + rule);
            print(String.valueOf(rs.getInt(1)));
        }
        catch (SQLException e) {
            print(e.getMessage());
            exit(-5);
        }
    }

    private void executeSQLStatement(String sql) {
        try (Statement stmt = dbConn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            print(e.getMessage());
            disconnect();
            exit(-5);
        }
    }

    private void createTables(Set<Animal> animalsSet, Map<String, Set<String>> properties) {
        try {
            createPropertiesTables(properties);
            createAnimalsTables(animalsSet);
        }
        catch (Exception e) {
            print(e.getMessage());
        }
    }

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

    private void connect() {
        try {
            String url = "jdbc:sqlite:animals.db";
            dbConn = DriverManager.getConnection(url);
            Statement stmt = dbConn.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            print(e.getMessage());
            exit(-5);
        }
    }

    private void disconnect() {
        try {
            dbConn.close();
        } catch (SQLException e) {
            print(e.getMessage());
            exit(-5);
        }
    }
}
