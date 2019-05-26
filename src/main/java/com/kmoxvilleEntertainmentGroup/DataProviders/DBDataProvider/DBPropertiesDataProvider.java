package com.kmoxvilleEntertainmentGroup.DataProviders.DBDataProvider;

import com.kmoxvilleEntertainmentGroup.DataProviders.PropertiesDataProvider;

import java.util.Map;
import java.util.Set;

/**
 * Источник данных из базы данных
 */
public class DBPropertiesDataProvider implements PropertiesDataProvider {
    @Override
    public Map<String, Set<String>> readAllProperties() {
        throw new UnsupportedOperationException();
    }
}
