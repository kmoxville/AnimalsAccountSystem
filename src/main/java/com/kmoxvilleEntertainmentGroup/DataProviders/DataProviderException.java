package com.kmoxvilleEntertainmentGroup.DataProviders;

public class DataProviderException extends Exception {
    public DataProviderException() {

    }

    public DataProviderException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }
}
