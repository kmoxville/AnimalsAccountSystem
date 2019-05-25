package com.kmoxvilleEntertainmentGroup.DataProviders;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataProviderFactoryTest {

    @Test(expected = NullPointerException.class)
    public void registerDataFormatHandler() {
        DataProviderFactory.registerDataFormatHandler(null);
    }


    @Test(expected = DataProviderNotFoundException.class)
    public void getInstanceFor() throws DataProviderNotFoundException {
        DataProviderFactory.getInstanceFor(".fgdfg", DataProviderType.Property);
    }
}