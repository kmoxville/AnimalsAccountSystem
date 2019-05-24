package com.kmoxvilleEntertainmentGroup.DataProviders;

import java.util.ArrayList;
import java.util.List;

/**
 * Новые источники данных(DataProvider) должны вызывать registerDataFormatHandler
 */
public class DataProviderFactory {
    static {
        formatHandlers = new ArrayList<DataFormatHandler>();
        try {
            //FIXME
            Class.forName("com.kmoxvilleEntertainmentGroup.DataProviders.JSONDataProvider.JSONAnimalsDataProvider");
            Class.forName("com.kmoxvilleEntertainmentGroup.DataProviders.JSONDataProvider.JSONPropertiesDataProvider");
            //Class.forName("XMLPropertiesDataProvider.");
            //...
        } catch (ClassNotFoundException e) {

        }

    }

    public static DataProvider getInstanceFor(String source, DataProviderType type)
            throws DataProviderNotFoundException {

        DataProvider dataProvider = null;
        for (DataFormatHandler dfh : formatHandlers) {
            dataProvider = dfh.canHandle(source, type);
            if (dataProvider != null) {
                return dataProvider;
            }
        }
        
        throw new DataProviderNotFoundException("Unknown data source");
    }

    public static void registerDataFormatHandler(DataFormatHandler dfh) {
        formatHandlers.add(dfh);
    }

    private static final List<DataFormatHandler> formatHandlers;
}
