package io.imwhiteflag.backtest.quotation;

import io.imwhiteflag.backtest.quotation.models.DollarQuotation;
import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBItem;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DollarQuotationTestFactory {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static final DateTimeFormatter bcbFormatter = BacktestQuotationUtils.getVariableISODateTimeFormatter();


    @Transactional
    public static DollarQuotation createNewDollarQuotation() {
        var randomLocalDate = BacktestQuotationUtils.generateRandomLocalDate();
        var localDateTime = BacktestQuotationUtils.addNowTimeToLocalDate(randomLocalDate);
        var buyPrice = BacktestQuotationUtils.generateRandomBigDecimal();
        var sellPrice = BacktestQuotationUtils.generateRandomBigDecimal();
        var quotation = DollarQuotation.find("quotationDate", randomLocalDate).firstResult();

        if (quotation == null) {
            quotation = DollarQuotation.builder().requestTimestamp(Instant.now()).quotationDate(randomLocalDate)
                    .buyPrice(buyPrice).sellPrice(sellPrice).quotationDateHour(localDateTime).build();
            DollarQuotation.persist(quotation);
        }

        return (DollarQuotation) quotation;
    }

    @Transactional
    public static DollarQuotation createNewDollarQuotation(String stringDate) {
        var randomLocalDate = LocalDate.from(formatter.parse(stringDate));
        var localDateTime = BacktestQuotationUtils.addNowTimeToLocalDate(randomLocalDate);
        var buyPrice = BacktestQuotationUtils.generateRandomBigDecimal();
        var sellPrice = BacktestQuotationUtils.generateRandomBigDecimal();
        var quotation = DollarQuotation.find("quotationDate", randomLocalDate).firstResult();

        if (quotation == null) {
            quotation = DollarQuotation.builder().requestTimestamp(Instant.now()).quotationDate(randomLocalDate)
                    .buyPrice(buyPrice).sellPrice(sellPrice).quotationDateHour(localDateTime).build();
            DollarQuotation.persist(quotation);
        }

        return (DollarQuotation) quotation;
    }

    public static DollarQuotation createNewDollarQuotationWithoutPersist(String stringDate) {
        var randomLocalDate = LocalDate.from(formatter.parse(stringDate));
        var localDateTime = BacktestQuotationUtils.addNowTimeToLocalDate(randomLocalDate);
        var buyPrice = BacktestQuotationUtils.generateRandomBigDecimal();
        var sellPrice = BacktestQuotationUtils.generateRandomBigDecimal();

        return DollarQuotation.builder().requestTimestamp(Instant.now()).buyPrice(buyPrice)
                .sellPrice(sellPrice).quotationDate(randomLocalDate).quotationDateHour(localDateTime).build();
    }

    public static DollarQuotationBCBItem fromBCBToDollarQuotation(DollarQuotation quotation) {
        return new DollarQuotationBCBItem(quotation.getBuyPrice(), quotation.getSellPrice(), quotation.getQuotationDateHour().format(bcbFormatter));
    }
}
