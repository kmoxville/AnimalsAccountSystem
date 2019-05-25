package com.kmoxvilleEntertainmentGroup;

//import org.apache.commons.cli.*;

import com.kmoxvilleEntertainmentGroup.DataProviders.*;
import com.kmoxvilleEntertainmentGroup.Utils.Options;
import static com.kmoxvilleEntertainmentGroup.Utils.HelperUtils.*;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) {

        try {
            Options.processCommandLine(args);
        }
        catch (ParseException e) {
            print(e.getMessage());
            exit(-1);
        }

        PropertiesDataProvider propDataProvider = null;
        AnimalsDataProvider animalsDataProvider = null;

        try {
            propDataProvider = (PropertiesDataProvider)DataProviderFactory
                    .getInstanceFor(Options.getPropertiesSource(), DataProviderType.Property);
            animalsDataProvider = (AnimalsDataProvider)DataProviderFactory
                    .getInstanceFor(Options.getAnimalsSource(), DataProviderType.Animal);
        }
        catch (DataProviderNotFoundException e) {
            print("No handler for such data source");
            exit(-2);
        }

        try {
            (new MainEngine(animalsDataProvider, propDataProvider)).run();
        }
        catch (DataProviderParseException e) {
            print("Source reading error: " + e.getMessage());
            exit(-3);
        }
    }

}
