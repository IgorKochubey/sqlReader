package com;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SqlParser {

    private static final String INTEGER = "(Integer)";
    private static final String STRING = "(String)";
    private static final String EMPTY_STRING = "";
    private static final String SINGLE_QUOTE = "'";

    public static void main(String[] args) {
        String input =
                "WITH productTable AS ( SELECT product_id, vendor_id IN ( ? , ?);";

        String parametersRaw =
                "228(Integer), 2020.10.01(String), 2020.10.31(String)";

        List<Object> parameters = Arrays.stream(parametersRaw.split(","))
                .map(String::trim)
                .map(SqlParser::getObject)
                .collect(toList());

        int counter = 0;
        while (input.contains("?")) {
            input = input.replaceFirst("\\?", parameters.get(counter).toString());
            counter++;
        }
        System.out.println(input);
    }

    private static Serializable getObject(String s) {
        if (s.contains(INTEGER)) {
            return Integer.valueOf(s.replace(INTEGER, EMPTY_STRING));
        } else {
            return SINGLE_QUOTE + s.replace(STRING, EMPTY_STRING) + SINGLE_QUOTE;
        }
    }
}
