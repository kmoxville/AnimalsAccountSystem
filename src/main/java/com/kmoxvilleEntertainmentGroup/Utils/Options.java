package com.kmoxvilleEntertainmentGroup.Utils;

import org.apache.commons.cli.*;

import static com.kmoxvilleEntertainmentGroup.Utils.HelperUtils.exit;
import static com.kmoxvilleEntertainmentGroup.Utils.HelperUtils.print;

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
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("accontanimals [OPTIONS] <RULE>", options);
            throw e;
        }

        propertiesSource = cmd.getOptionValue("properties-source");
        animalsSource = cmd.getOptionValue("animals-source");
        try {
            rule = cmd.getArgList().get(0);
        }
        catch (IndexOutOfBoundsException e) {
            print("No rule specified");
            exit(-2);
        }
    }

    public static String getPropertiesSource() {
        return propertiesSource;
    }

    public static String getAnimalsSource() {
        return animalsSource;
    }

    public static String getRule() {
        return rule;
    }

    private static String rule = null;
    private static String propertiesSource = null;
    private static String animalsSource = null;
}
