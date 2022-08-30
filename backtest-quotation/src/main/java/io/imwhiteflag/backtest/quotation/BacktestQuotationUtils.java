package io.imwhiteflag.backtest.quotation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BacktestQuotationUtils {

    public static boolean validateDateFormat(String value) {
        try {
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter.parse(value);
            return true;
        } catch (DateTimeParseException exception) {
            return false;
        }
    }
    public static String addQuotesToString(String value) {
        return "'" + value + "'";
    }
    public static boolean validateString(String value) {
        return value != null && !value.isEmpty();
    }
}
