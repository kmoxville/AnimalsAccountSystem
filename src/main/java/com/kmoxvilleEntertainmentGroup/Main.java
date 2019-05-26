package com.kmoxvilleEntertainmentGroup;

import com.kmoxvilleEntertainmentGroup.DataProviders.*;
import com.kmoxvilleEntertainmentGroup.Utils.*;
import static com.kmoxvilleEntertainmentGroup.Utils.HelperUtils.*;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) {

        try {
            Options.processCommandLine(args);
        }
        catch (ParseException e) {
            exit(ExitCodes.CommandLineParsingError);
        }
        catch (IndexOutOfBoundsException e) {
            print("No rule specified");
            exit(ExitCodes.CommandLineParsingError);
        }

        DataProvider dataProvider = null;

        try {
            dataProvider = DataProviderFactory.createDataProvider(Options.getAnimalsSource(),
                    Options.getPropertiesSource());
        }
        catch (DataProviderNotFoundException e) {
            print("No handler for such data source");
            exit(ExitCodes.UnknownDataSource);
        }


        try {
            new MainEngine(dataProvider, Options.getRule()).run();
        }
        catch (DataParseException e) {
            print("Something wrong with sources of data, details:\n" + e.getCause()); //SQLite error
            exit(ExitCodes.ParseError);
        }
        catch (RuleParseException e) {
            print("Wrong rule format\n" + e.getMessage());
            exit(ExitCodes.RuleProcessingError);
        }
        catch (DataProviderParseException e) {
            print("Error while reading data from sources\n" + e.getMessage());
            exit(ExitCodes.ParseError);
        }


    }

}
