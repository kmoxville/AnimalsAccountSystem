package com.kmoxvilleEntertainmentGroup.DataProviders;

import com.kmoxvilleEntertainmentGroup.Animal;

import java.util.Map;
import java.util.Set;

/**
 * "Мост" между источниками данных и MainEngine
 * "Фасад" объединяющий AnimalsDataProvider и PropertiesDataProvider
 */
public class DataProvider {
    private final AnimalsDataProvider adp;
    private final PropertiesDataProvider pdp;

    DataProvider(AnimalsDataProvider adp, PropertiesDataProvider pdp) {
        this.adp = adp;
        this.pdp = pdp;
    }

    public Map<String, Set<String>> readAllProperties() throws DataProviderParseException {
        return pdp.readAllProperties();
    }

    public Set<Animal> readAllAnimals() throws DataProviderParseException {
        return adp.readAllAnimals();
    }
}
