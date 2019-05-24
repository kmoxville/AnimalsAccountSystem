package com.kmoxvilleEntertainmentGroup.Utils;

import org.apache.commons.cli.*;

public class Options {

    public static void processCommandLine(String[] args) throws ParseException {
        org.apache.commons.cli.Options options = new org.apache.commons.cli.Options();

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
            throw e;
        }

        propertiesSource = cmd.getOptionValue("properties-source");
        animalsSource = cmd.getOptionValue("animals-source");
    }

    public static String getPropertiesSource() {
        return propertiesSource;
    }

    public static String getAnimalsSource() {
        return animalsSource;
    }

    private static String propertiesSource = null;
    private static String animalsSource = null;
}
