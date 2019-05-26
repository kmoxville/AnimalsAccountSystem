package com.kmoxvilleEntertainmentGroup.DataProviders;

import com.kmoxvilleEntertainmentGroup.Animal;

import java.util.Set;

public interface AnimalsDataProvider {
    Set<Animal> readAllAnimals() throws DataProviderParseException;
}
