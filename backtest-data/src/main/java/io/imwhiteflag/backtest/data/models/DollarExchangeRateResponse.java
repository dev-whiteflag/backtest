package io.imwhiteflag.backtest.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DollarExchangeRateResponse {
    List<DollarExchangeRateValueResponse> value;

    static class DollarExchangeRateValueResponse {
        BigDecimal cotacaoCompra;
        BigDecimal cotacaoVenda;
        ZonedDateTime dataHoraCotacao;
    }
}
