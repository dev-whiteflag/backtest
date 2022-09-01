package io.imwhiteflag.backtest.quotation.service;

import io.imwhiteflag.backtest.quotation.BacktestQuotationUtils;
import io.imwhiteflag.backtest.quotation.models.DateComparator;
import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBItem;
import io.imwhiteflag.backtest.quotation.models.DollarQuotation;
import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBRestResponse;
import io.quarkus.panache.common.Page;
import lombok.extern.java.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
public class DollarQuotationService {

    @RestClient
    DollarQuotationBCBRestService quotationBCBService;

    DateTimeFormatter bcbDateHourFormat = BacktestQuotationUtils.getVariableISODateTimeFormatter();
    DateTimeFormatter bcbDateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    public DollarQuotation getDollarQuotationFromBCB(String date) {
        var localDate = LocalDate.from(bcbDateFormat.parse(date));
        var quotation = getExistingDollarQuotationByDate(localDate);

        if (quotation == null) {
            var correctedDate = BacktestQuotationUtils.addQuotesToString(date);
            var response = quotationBCBService.getDayDollarQuotation(correctedDate, "json").getValue().get(0);
            quotation = persistDollarQuotation(response);
        }

        return quotation;
    }

    public List<DollarQuotation> getDollarQuotationFromBCB(String startDate, String finalDate, Integer skip, Integer max) {
        var startLocalDate = LocalDate.from(bcbDateFormat.parse(startDate));
        var finalLocalDate = LocalDate.from(bcbDateFormat.parse(finalDate));

        var dates = BacktestQuotationUtils.getDatesBetweenRange(startLocalDate, finalLocalDate)
                .stream().skip(skip).limit(max).sorted(new DateComparator()).collect(Collectors.toList());

        List<DollarQuotation> quotations = DollarQuotation.stream("quotationDate in ?1", dates).map(obj -> (DollarQuotation) obj)
                 .collect(Collectors.toList());

        if (!dates.isEmpty()) {
            var periods = BacktestQuotationUtils.getAllPeriodsInDateList(dates);

            periods.forEach(period -> {
                var periodStartDate = BacktestQuotationUtils.addQuotesToString(bcbDateFormat.format(period.getStartDate()));
                var periodEndDate = BacktestQuotationUtils.addQuotesToString(bcbDateFormat.format(period.getEndDate()));
                DollarQuotationBCBRestResponse response;

                if (period.getStartDate() == period.getEndDate()) {
                    response = quotationBCBService.getDayDollarQuotation(periodStartDate, "json");
                } else {
                    response = quotationBCBService.getPeriodDollarQuotation(periodStartDate, periodEndDate, "json", max, 0);
                }

                quotations.addAll(persistDollarQuotationList(response.getValue()));
            });
        }

        return quotations;
    }

    public List<DollarQuotation> getAllSavedDollarQuotation(Integer pageIndex, Integer itemsPerPage) {
        return DollarQuotation.findAll().page(Page.of(pageIndex, itemsPerPage)).list();
    }

    private DollarQuotation getExistingDollarQuotationByDate(LocalDate date) {
        return DollarQuotation.find("quotationDate", date).firstResult();
    }

    @Transactional
    public DollarQuotation persistDollarQuotation(DollarQuotationBCBItem quotation) {
        var timestamp = Instant.now();
        var ta = bcbDateHourFormat.parse(quotation.getDataHoraCotacao());
        var entity = DollarQuotation.builder().requestTimestamp(timestamp)
                .quotationDate(LocalDate.from(ta)).buyPrice(quotation.getCotacaoCompra()).sellPrice(quotation.getCotacaoVenda())
                .quotationDateHour(LocalDateTime.from(ta)).build();
        DollarQuotation.persist(entity);
        return entity;
    }

    private List<DollarQuotation> persistDollarQuotationList(List<DollarQuotationBCBItem> list) {
        var quotations = new ArrayList<DollarQuotation>();
        list.forEach(item -> quotations.add(persistDollarQuotation(item)));
        return quotations;
    }
}
