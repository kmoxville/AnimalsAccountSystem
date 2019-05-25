package com.kmoxvilleEntertainmentGroup.DataProviders;

import java.util.*;

/**
 * "Мост" между данными из внешних источников(напрмер файлов) и MainEngine
 */
public interface PropertiesDataProvider {
    Map<String, Set<String>> readAllData() throws DataProviderParseException;
}
