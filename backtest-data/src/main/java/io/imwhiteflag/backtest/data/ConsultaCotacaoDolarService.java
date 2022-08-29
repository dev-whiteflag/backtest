package io.imwhiteflag.backtest.data;

import io.imwhiteflag.backtest.data.cotacaobcb.models.CotacaoDolarBcbRestResponse;
import io.imwhiteflag.backtest.data.models.ConsultaCotacaoDolarEntity;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.UUID;

@Log
@ApplicationScoped
public class ConsultaCotacaoDolarService {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public void persistCotacaoResponseAsEntity(CotacaoDolarBcbRestResponse response, Instant timestampRequisicao) {
        response.getValue().parallelStream().forEach(cotacao -> {
            TemporalAccessor ta = formatter.parse(cotacao.getDataHoraCotacao());
            var entity = ConsultaCotacaoDolarEntity.builder()
                    .id(UUID.randomUUID()).timestampRequisicao(timestampRequisicao).dataCotacao(LocalDate.from(ta))
                    .valorCompra(cotacao.getCotacaoCompra()).valorVenda(cotacao.getCotacaoVenda())
                    .dataHoraCotacao(LocalDateTime.from(ta)).build();

            log.info(entity.getId().toString());
            entity.persist();
        });
    }
}
