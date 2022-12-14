package io.imwhiteflag.backtest.quotation;

import io.imwhiteflag.backtest.quotation.models.DatePeriod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class BacktestQuotationUtils {

    public static boolean validateDateFormat(String value) {
        try {
            var formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
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
        return (first != null && first >= 0) && (max != null && max <= 100);
    }

    public static List<LocalDate> getDatesBetweenRange(LocalDate start, LocalDate end) {
        return start.datesUntil(end.plusDays(1)).collect(Collectors.toList());
    }

    public static List<DatePeriod> getAllPeriodsInDateList(List<LocalDate> dates) {
        List<DatePeriod> periodList = new ArrayList<>();
        DatePeriod period = new DatePeriod();
        LocalDate prev = null;

        for (int i = 0; i < dates.size(); i++) {
            var current = dates.get(i);
            if (prev == null) {
                period.setStartDate(current);
            } else {
                if (prev.plusDays(1).isEqual(current)) {
                    period.setEndDate(current);
                } else {
                    if (period.getEndDate() == null) {
                        period.setEndDate(period.getStartDate());
                    }
                    periodList.add(period);
                    period = new DatePeriod(current, null);
                }
            }
            prev = current;

            if (i == dates.size() - 1) {
                periodList.add(period);
            }
        }

        return periodList;
    }

    public static LocalDate generateRandomLocalDate() {
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().minusDays(1).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public static LocalDate generateRandomLocalDate(LocalDate min, LocalDate max) {
        long randomDay = ThreadLocalRandom.current().nextLong(min.toEpochDay(), max.toEpochDay());
        return LocalDate.ofEpochDay(randomDay);
    }

    public static BigDecimal generateRandomBigDecimal() {
        var rand = BigDecimal.valueOf(Math.random());
        rand = rand.setScale(2, RoundingMode.CEILING);
        return rand;
    }

    public static LocalDateTime addNowTimeToLocalDate(LocalDate date) {
        var localTime = LocalTime.now();
        return LocalDateTime.of(date, localTime);
    }

    public static DateTimeFormatter getVariableISODateTimeFormatter() {
        return new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss")
                .appendFraction(ChronoField.MILLI_OF_SECOND, 2, 3, true)
                .toFormatter();
    }
}
