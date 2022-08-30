package io.imwhiteflag.backtest.quotation;

import io.imwhiteflag.backtest.quotation.models.DatePeriod;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static boolean validatePaginationParams(Integer first, Integer max) {
        return (first != null && first >= 0) && (max != null && max > 0);
    }

    public static List<LocalDate> getDatesBetweenRange(LocalDate start, LocalDate end) {
        return start.datesUntil(end).collect(Collectors.toList());
    }

    public static List<DatePeriod> getAllPeriodsInDateList(List<LocalDate> dates) {
        List<DatePeriod> periodList = new ArrayList<>();
        DatePeriod period = null;
        LocalDate prevDate = null;

        for (var date : dates) {
            if (prevDate == null) {
                prevDate = date;
                period = new DatePeriod();
                period.setStartDate(prevDate);
            } else {
                if (ChronoUnit.DAYS.between(prevDate, date) > 1) {
                    period.setEndDate(prevDate);
                    periodList.add(period);
                    period = new DatePeriod();
                    period.setStartDate(date);
                }
                prevDate = date;
            }
        }

        if (period != null && period.getEndDate() == null) {
            period.setEndDate(period.getStartDate());
            periodList.add(period);
        }

        return periodList;
    }
}
