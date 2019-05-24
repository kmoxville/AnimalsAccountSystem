package com.kmoxvilleEntertainmentGroup;

//import org.apache.commons.cli.*;

import com.kmoxvilleEntertainmentGroup.DataProviders.DataProvider;
import com.kmoxvilleEntertainmentGroup.DataProviders.DataProviderFactory;
import com.kmoxvilleEntertainmentGroup.DataProviders.DataProviderNotFoundException;
import com.kmoxvilleEntertainmentGroup.DataProviders.DataProviderType;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {

        Options options = new Options();

        Option propertiesSourceOption = Option.builder("p")
                .required(true)
                .longOpt("properties-source")
                .desc("Source of properties(json, xml, cvs file)")
                .hasArg(true)
                .type(String.class)
                .build();

        Option animalsSourceOption = Option.builder("a")
                .required(true)
                .longOpt("animals-source")
                .desc("Source of animals(json, xml, cvs file)")
                .hasArg(true)
                .type(String.class)
                .build();

        options.addOption(propertiesSourceOption)
                .addOption(animalsSourceOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("accontanimals", options);
            System.exit(1);
        }

        String propFilePath = cmd.getOptionValue("properties-source");
        String animalsFilePath = cmd.getOptionValue("animals-source");

        try {
            DataProvider propDataProvider = DataProviderFactory
                    .getInstanceFor(propFilePath, DataProviderType.Property);
            DataProvider animalsDataProvider = DataProviderFactory
                    .getInstanceFor(animalsFilePath, DataProviderType.Animal);
            (new MainEngine(animalsDataProvider, propDataProvider)).run();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(2);
        }
    }

}
