package com.kmoxvilleEntertainmentGroup.DataProviders.DBDataProvider;

import com.kmoxvilleEntertainmentGroup.Animal;
import com.kmoxvilleEntertainmentGroup.DataProviders.AnimalsDataProvider;

import java.util.Set;

/**
 * Источник данных из базы данных
 */
public class DBAnimalsDataProvider implements AnimalsDataProvider {
    @Override
    public Set<Animal> readAllAnimals() {
        throw new UnsupportedOperationException();
    }
}
