package com.kmoxvilleEntertainmentGroup.DataProviders;

import java.util.ArrayList;
import java.util.List;

/**
 * Новые источники данных(DataProvider) должны вызывать registerDataFormatHandler
 */
public class DataProviderFactory {
    static {
        formatHandlers = new ArrayList<>();
        try {
            //FIXME
            Class.forName("com.kmoxvilleEntertainmentGroup.DataProviders.JSONDataProvider.JSONAnimalsDataProvider");
            Class.forName("com.kmoxvilleEntertainmentGroup.DataProviders.JSONDataProvider.JSONPropertiesDataProvider");
            //Class.forName("XMLPropertiesDataProvider.");
            //...
        } catch (ClassNotFoundException e) {

        }

    }

    public static Object getInstanceFor(String source, DataProviderType type)
            throws DataProviderNotFoundException {

        Object dataProvider;
        for (DataFormatHandler dfh : formatHandlers) {
            dataProvider = dfh.canHandle(source, type);
            if (dataProvider != null) {
                return dataProvider;
            }
        }
        
        throw new DataProviderNotFoundException("Unknown data source");
    }

    public static void registerDataFormatHandler(DataFormatHandler dfh) {
        if (dfh == null)
            throw new NullPointerException("Null pointer in format handler");

        formatHandlers.add(dfh);
    }

    private static final List<DataFormatHandler> formatHandlers;
}
