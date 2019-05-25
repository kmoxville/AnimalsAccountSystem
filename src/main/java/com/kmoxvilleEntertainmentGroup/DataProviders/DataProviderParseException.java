package com.kmoxvilleEntertainmentGroup.DataProviders;

public class DataProviderParseException extends Exception {
    public DataProviderParseException() {

    }

    public DataProviderParseException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
