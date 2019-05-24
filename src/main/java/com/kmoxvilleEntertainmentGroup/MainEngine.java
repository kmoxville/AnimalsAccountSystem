package com.kmoxvilleEntertainmentGroup;

import com.kmoxvilleEntertainmentGroup.DataProviders.DataProvider;
import com.kmoxvilleEntertainmentGroup.DataProviders.DataProviderException;

import java.util.List;
import java.sql.*;
import java.util.Map;
import java.util.Set;

class MainEngine {
    private DataProvider propProvider;
    private DataProvider animalsProvider;

    MainEngine(DataProvider animalsProvider, DataProvider propProvider) {
        this.animalsProvider = animalsProvider;
        this.propProvider = propProvider;
    }

    void run() throws DataProviderException, ClassNotFoundException {
        @SuppressWarnings("unchecked")
        List<Animal> animalsList = (List<Animal>)animalsProvider.readAllData();
        @SuppressWarnings("unchecked")
        Map<String, Set<String>> propsSet = (Map<String, Set<String>>)propProvider.readAllData();

        Class.forName("org.postgresql.Driver");
    }
}
