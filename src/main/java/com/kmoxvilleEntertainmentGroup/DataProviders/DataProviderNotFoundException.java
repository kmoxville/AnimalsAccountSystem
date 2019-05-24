package com.kmoxvilleEntertainmentGroup.DataProviders;

public class DataProviderNotFoundException extends Exception {
    public DataProviderNotFoundException() {
        super();
    }

    public DataProviderNotFoundException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public DataProviderNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
