package io.imwhiteflag.backtest.quotation.models;

import java.time.LocalDate;
import java.util.Comparator;

public class DateComparator implements Comparator<LocalDate> {
    @Override
    public int compare(LocalDate o1, LocalDate o2) {
        return o1.compareTo(o2);
    }
}
