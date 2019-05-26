package com.kmoxvilleEntertainmentGroup.DataProviders;

import java.util.ArrayList;
import java.util.List;

/**
 * Новые источники данных(AnimalsDataProvider или PropertiesDataProvider) должны вызывать registerDataFormatHandler
 */
public class DataProviderFactory {

    //Предопределенные источники инициализируются в статическом инициализаторе
    static {
        animalsFormatHandlers = new ArrayList<>();
        propsFormatHandlers = new ArrayList<>();

        try {
            //FIXME: wontfix(
            Class.forName("com.kmoxvilleEntertainmentGroup.DataProviders.JSONDataProvider.JSONAnimalsDataProvider");
            Class.forName("com.kmoxvilleEntertainmentGroup.DataProviders.JSONDataProvider.JSONPropertiesDataProvider");
            //Class.forName("XMLPropertiesDataProvider.");
            //...
        } catch (ClassNotFoundException ignored) { } //That should just never happen
    }

    public static DataProvider createDataProvider(String animalsSource, String propsSource)
            throws DataProviderNotFoundException {

        AnimalsDataProvider adp = null;
        PropertiesDataProvider pdp = null;

        for (AnimalsDataFormatHandler dfh : animalsFormatHandlers) {
            adp = dfh.canHandle(animalsSource);
        }
        for (PropertiesDataFormatHandler dfh : propsFormatHandlers) {
            pdp = dfh.canHandle(propsSource);
        }

        if (adp != null && pdp != null)
            return new DataProvider(adp, pdp);
        
        throw new DataProviderNotFoundException("Unknown data source");
    }

    //TODO: add unregister(to purge predefined handlers)
    public static void registerDataFormatHandler(Object dfh) {
        if (dfh == null)
            throw new NullPointerException("Null pointer format handler");

        if (dfh instanceof AnimalsDataFormatHandler) {
            animalsFormatHandlers.add((AnimalsDataFormatHandler) dfh);
            return;
        }
        if (dfh instanceof PropertiesDataFormatHandler) {
            propsFormatHandlers.add((PropertiesDataFormatHandler)dfh);
            return;
        }

        throw new IllegalArgumentException("Unknown format handler");
    }

    private static final List<AnimalsDataFormatHandler> animalsFormatHandlers;
    private static final List<PropertiesDataFormatHandler> propsFormatHandlers;
}
