package com.kmoxvilleEntertainmentGroup;

import com.kmoxvilleEntertainmentGroup.DataProviders.DataProviderFactory;
import com.kmoxvilleEntertainmentGroup.DataProviders.DataProviderParseException;
import org.junit.jupiter.api.*;

import java.io.File;

class MainTest {

    @Test
    @Disabled
    void main() {
        String pathToAnimalsFile = getAbsolutePath("/good-resources/animals.json");
        String pathToPropsFile = getAbsolutePath("/good-resources/properties.json");

        Main.main(new String[] {"-a", pathToAnimalsFile, "-p", pathToPropsFile, "Вес='Тяжелый'"});
    }

    //Test with malformed rule
    @Test
    void malformedRuleMain() {
        String pathToAnimalsFile = getAbsolutePath("/good-resources/animals.json");
        String pathToPropsFile = getAbsolutePath("/good-resources/properties.json");

        Assertions.assertThrows(RuleParseException.class, () -> {
            (new MainEngine(DataProviderFactory
                    .createDataProvider(pathToAnimalsFile, pathToPropsFile), "Вес=<>_+|'Тяжелый'")).run();
        });
    }

    //Test with malformed animals file
    @Test
    void malformedAnimalsFileMain() {
        String pathToAnimalsFile = getAbsolutePath("/bad-resources/animals-with-errors.json");
        String pathToPropsFile = getAbsolutePath("/good-resources/properties.json");

        Assertions.assertThrows(DataProviderParseException.class, () -> {
            (new MainEngine(DataProviderFactory
                    .createDataProvider(pathToAnimalsFile, pathToPropsFile), "Вес='Тяжелый'")).run();
        });
    }

    //Test with malformed properties file
    @Test
    void malformedPropsFileMain() {
        String pathToAnimalsFile = getAbsolutePath("/good-resources/animals.json");
        String pathToPropsFile = getAbsolutePath("/bad-resources/properties-with-errors.json");

        Assertions.assertThrows(DataProviderParseException.class, () -> {
            (new MainEngine(DataProviderFactory
                    .createDataProvider(pathToAnimalsFile, pathToPropsFile), "Вес='Тяжелый'")).run();
        });
    }

    //Test with animals with wrong properties
    @Test
    void malformedAnimalsFileMain_2() {
        String pathToAnimalsFile = getAbsolutePath("/bad-resources/animals-with-not-existing-props.json");
        String pathToPropsFile = getAbsolutePath("/good-resources/properties.json");

        Assertions.assertThrows(DataParseException.class, () -> {
            (new MainEngine(DataProviderFactory
                    .createDataProvider(pathToAnimalsFile, pathToPropsFile), "Вес='Тяжелый'")).run();
        });
    }

    private String getAbsolutePath(String str) {
        return ((new File(getClass()
                .getResource(str)
                .getFile()))
                .getAbsolutePath());
    }
}