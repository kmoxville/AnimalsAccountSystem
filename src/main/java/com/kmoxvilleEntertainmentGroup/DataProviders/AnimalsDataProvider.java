package com.kmoxvilleEntertainmentGroup.DataProviders;

import com.kmoxvilleEntertainmentGroup.Animal;

import java.util.Set;

/**
 * "Мост" между данными из внешних источников(напрмер файлов) и MainEngine
 */
public interface AnimalsDataProvider {
    Set<Animal> readAllData() throws DataProviderParseException;
}
