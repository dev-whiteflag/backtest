package io.imwhiteflag.backtest.quotation;

import io.imwhiteflag.backtest.quotation.models.DollarQuotation;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DollarQuotationTestFactory {

    @Transactional
    public static DollarQuotation createNewDollarQuotation() {
        final DateTimeFormatter bcbFormatter = BacktestQuotationUtils.getVariableISODateTimeFormatter();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        var randomLocalDate = BacktestQuotationUtils.generateRandomLocalDate();
        var localDateTime = BacktestQuotationUtils.addNowTimeToLocalDate(randomLocalDate);
        var date = formatter.format(randomLocalDate);
        var dateHour = bcbFormatter.format(localDateTime);
        var buyPrice = BacktestQuotationUtils.generateRandomBigDecimal();
        var sellPrice = BacktestQuotationUtils.generateRandomBigDecimal();

        var quotation = DollarQuotation.builder().requestTimestamp(Instant.now()).buyPrice(buyPrice)
                .sellPrice(sellPrice).quotationDate(LocalDate.from(formatter.parse(date)))
                .quotationDateHour(LocalDateTime.from(bcbFormatter.parse(dateHour))).build();
        quotation.persist();
        return quotation;
    }
}
