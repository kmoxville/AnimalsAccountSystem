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

        DataProvider propDataProvider = null;
        DataProvider animalsDataProvider = null;

        try {
            propDataProvider = DataProviderFactory
                    .getInstanceFor(Options.getPropertiesSource(), DataProviderType.Property);
            animalsDataProvider = DataProviderFactory
                    .getInstanceFor(Options.getAnimalsSource(), DataProviderType.Animal);
        }
        catch (DataProviderNotFoundException e) {
            print("No handler for such data source");
            exit(-2);
        }

        try {
            (new MainEngine(animalsDataProvider, propDataProvider)).run();
        }
        catch (ClassNotFoundException e) { //Just suppress this

        }
        catch (DataProviderException e) {
            print("Source reading error: " + e.getMessage());
        }
    }

}
