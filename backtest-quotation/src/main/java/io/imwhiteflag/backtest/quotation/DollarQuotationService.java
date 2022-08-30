package io.imwhiteflag.backtest.quotation;

import io.imwhiteflag.backtest.quotation.cotacaobcb.CotacaoDolarBcbRestService;
import io.imwhiteflag.backtest.quotation.cotacaobcb.models.CotacaoDolarBcbRestResponse;
import io.imwhiteflag.backtest.quotation.cotacaobcb.models.CotacaoDolarValorBcbRestResponse;
import io.imwhiteflag.backtest.quotation.models.DolarQuotation;
import lombok.extern.java.Log;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Log
@ApplicationScoped
public class DollarQuotationService {

    @RestClient
    CotacaoDolarBcbRestService cotacaoBcbService;

    public void getDollarQuotationFromBCB(String date) {

    }

    public void getDollarQuotationFromBCB(String startDate, String finalDate) {

    }

    private void persistDollarQuotation(CotacaoDolarValorBcbRestResponse quotation) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        var timestamp = Instant.now();
        var ta = formatter.parse(quotation.getDataHoraCotacao());
        var entity = DolarQuotation.builder().id(UUID.randomUUID()).timestampRequisicao(timestamp)
                .dataCotacao(LocalDate.from(ta)).valorCompra(quotation.getCotacaoCompra()).valorVenda(quotation.getCotacaoVenda())
                .dataHoraCotacao(LocalDateTime.from(ta)).build();
        log.info(entity.getId().toString());
        entity.persist();
    }

    private void persistDollarQuotationList(CotacaoDolarBcbRestResponse list) {

    }
}
