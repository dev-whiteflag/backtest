package io.imwhiteflag.backtest.quotation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DollarQuotationBCBItem {
    BigDecimal cotacaoCompra;
    BigDecimal cotacaoVenda;
    String dataHoraCotacao;
}
