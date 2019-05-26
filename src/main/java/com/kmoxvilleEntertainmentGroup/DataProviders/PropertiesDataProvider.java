package com.kmoxvilleEntertainmentGroup.DataProviders;

import java.util.*;

public interface PropertiesDataProvider {
    Map<String, Set<String>> readAllProperties() throws DataProviderParseException;
}
