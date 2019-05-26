package com.kmoxvilleEntertainmentGroup.DataProviders;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataProviderFactoryTest {

    @Test
    void registerDataFormatHandler() {
        Assertions.assertThrows(NullPointerException.class, () ->
            DataProviderFactory.registerDataFormatHandler(null)
        );
    }

    //Test with not existing data source
    @Test
    void getInstanceFor() {
        Assertions.assertThrows(DataProviderNotFoundException.class, () ->
            DataProviderFactory.createDataProvider(".lol", ".kek")
        );

    }
}