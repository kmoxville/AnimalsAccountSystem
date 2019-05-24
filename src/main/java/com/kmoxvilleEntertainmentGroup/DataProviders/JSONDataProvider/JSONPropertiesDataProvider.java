package com.kmoxvilleEntertainmentGroup.DataProviders.JSONDataProvider;

import com.google.gson.stream.JsonReader;
import com.kmoxvilleEntertainmentGroup.DataProviders.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JSONPropertiesDataProvider implements DataProvider<Map<String, Set<String>>> {
    private String dataSource;

    static {
        DataProviderFactory.registerDataFormatHandler((format, type) -> {
                if (format.endsWith(".json") && (type == DataProviderType.Property ))
                    return new JSONPropertiesDataProvider(format);

                return null;
        });
    }

    public JSONPropertiesDataProvider(String dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Map<String, Set<String>> readAllData() throws DataProviderException {
        Map<String, Set<String>> result;
        try (FileReader fileReader = new FileReader(dataSource)) {
            JsonReader reader = new JsonReader(fileReader);
            result = readProperties(reader);
        }
        catch (Exception e) {
            throw new DataProviderException("Unable to parse properties file", e);
        }

        return result;
    }

    //JSON parsing section begin
    private Map<String, Set<String>> readProperties(JsonReader reader) throws IOException {
        Map<String, Set<String>> props = new HashMap<>();

        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            {
                String propName = reader.nextName();
                Set<String> propValues = readPropertyValues(reader);
                props.put(propName, propValues);
            }
            reader.endObject();
        }
        reader.endArray();

        return props;
    }

    private Set<String> readPropertyValues(JsonReader reader) throws IOException {
        Set<String> propValues = new HashSet<>();

        reader.beginArray();
        while (reader.hasNext()) {
            propValues.add(reader.nextString());
        }
        reader.endArray();

        return propValues;
    }
    //JSON parsing section end
}
