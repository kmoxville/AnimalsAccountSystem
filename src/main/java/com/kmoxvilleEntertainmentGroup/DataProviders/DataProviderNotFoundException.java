package com.kmoxvilleEntertainmentGroup.DataProviders;

public class DataProviderNotFoundException extends Exception {

    DataProviderNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
