package com.kmoxvilleEntertainmentGroup;

import java.util.HashMap;
import java.util.Map;

public class Animal {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> properties() {
        return properties;
    }

    private String name = new String();
    private final Map<String, String> properties = new HashMap<>();
}
