package io.imwhiteflag.backtest.quotation.service;

import io.imwhiteflag.backtest.quotation.models.DollarQuotationBCBItem;
import io.imwhiteflag.backtest.quotation.models.DollarQuotation;
import io.quarkus.panache.common.Page;
import lombok.extern.java.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Log
@ApplicationScoped
public class DollarQuotationService {

    @RestClient
    DollarQuotationBCBRestService quotationBCBService;

    public DollarQuotationBCBItem getDollarQuotationFromBCB(String date) {
        var response = quotationBCBService.getDayDollarQuotation(date, "json").getValue().get(0);
        persistDollarQuotation(response);
        return response;
    }

    public List<DollarQuotationBCBItem> getDollarQuotationFromBCB(String startDate, String finalDate, Integer first, Integer max) {
        var response = quotationBCBService.getPeriodDollarQuotation(startDate, finalDate, "json", max, first);
        persistDollarQuotationList(response.getValue());
        return response.getValue();
    }

    public List<DollarQuotation> getAllSavedDollarQuotation(Integer pageIndex, Integer itemsPerPage) {
        return DollarQuotation.findAll().page(Page.of(pageIndex, itemsPerPage)).list();
    }

    private void persistDollarQuotation(DollarQuotationBCBItem quotation) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        var timestamp = Instant.now();
        var ta = formatter.parse(quotation.getDataHoraCotacao());
        var entity = DollarQuotation.builder().id(UUID.randomUUID()).requestTimestamp(timestamp)
                .quotationDate(LocalDate.from(ta)).buyPrice(quotation.getCotacaoCompra()).sellPrice(quotation.getCotacaoVenda())
                .quotationDateHour(LocalDateTime.from(ta)).build();
        log.info(entity.getId().toString());
        entity.persist();
    }

    private void persistDollarQuotationList(List<DollarQuotationBCBItem> list) {
        list.forEach(this::persistDollarQuotation);
    }

    public boolean validatePaginationParams(Integer first, Integer max) {
        return (first != null && first >= 0) && (max != null && max <= 100);
    }
}
