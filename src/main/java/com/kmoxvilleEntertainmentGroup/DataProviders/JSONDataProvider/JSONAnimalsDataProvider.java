package com.kmoxvilleEntertainmentGroup.DataProviders.JSONDataProvider;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.kmoxvilleEntertainmentGroup.Animal;
import com.kmoxvilleEntertainmentGroup.DataProviders.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class JSONAnimalsDataProvider implements DataProvider<List<Animal>> {

    private String dataSource;

    static {
        DataProviderFactory.registerDataFormatHandler(new DataFormatHandler() {
            @Override
            public DataProvider canHandle(String format, DataProviderType type) {
                if (format.endsWith(".json") && (type == DataProviderType.Animal ))
                    return new JSONAnimalsDataProvider(format);

                return null;
            }
        });
    }

    public JSONAnimalsDataProvider(String dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Animal> readAllData() throws DataProviderException {
        List<Animal> result = null;

        try (FileReader fileReader = new FileReader(dataSource)) {
            JsonReader reader = new JsonReader(fileReader);
            result = readAnimalsArray(reader);
        }
        catch (Exception e) {
            throw new DataProviderException("Unable to parse animals file", e);
        }

        return result;
    }

    //JSON parsing begin
    private List<Animal> readAnimalsArray(JsonReader reader) throws IOException {
        List<Animal> animals = new ArrayList<Animal>();

        reader.beginArray();
        while (reader.hasNext()) {
            animals.add(readAnimal(reader));
        }
        reader.endArray();
        return animals;
    }

    private Animal readAnimal(JsonReader reader) throws IOException {
        Animal animal = new Animal();

        reader.beginObject();
        while (reader.hasNext()) {
            animal.setName(reader.nextName());
            animal.properties().putAll(readProperties(reader));
        }
        reader.endObject();
        return animal;
    }

    private Map<String, String> readProperties(JsonReader reader) throws  IOException {
        Map<String, String> properties = new HashMap<>();

        reader.beginArray();
        while (reader.hasNext()) {
            Map.Entry<String, String> propEntry = readProperty(reader);
            properties.put(propEntry.getKey(), propEntry.getValue());
        }
        reader.endArray();

        return properties;
    }

    private Map.Entry<String, String> readProperty(JsonReader reader) throws IOException {
        String key = null;
        String value = null;

        reader.beginObject();
        while (reader.hasNext()) {
            key = reader.nextName();
            value = reader.nextString();
        }
        reader.endObject();

        return new AbstractMap.SimpleEntry<String, String>(key, value);
    }
    //JSON parsing end
}
