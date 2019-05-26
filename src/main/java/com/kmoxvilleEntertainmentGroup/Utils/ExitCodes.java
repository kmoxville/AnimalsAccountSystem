package com.kmoxvilleEntertainmentGroup.Utils;

public enum ExitCodes {
    ParseError(-1),
    RuleProcessingError(-2),
    CommandLineParsingError(-3),
    UnknownDataSource(-4),
    CriticalError(-100);

    public int getCode() { return code; }

    ExitCodes(int code) {
        this.code = code;
    }

    private int code;
}