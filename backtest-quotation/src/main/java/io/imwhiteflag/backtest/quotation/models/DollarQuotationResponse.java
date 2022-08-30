package io.imwhiteflag.backtest.quotation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DollarQuotationResponse {
    String quotationDateHour;
    BigDecimal buyPrice;
    BigDecimal sellPrice;

    public static DollarQuotationResponse from(DollarQuotationBCBItem item) {
        return new DollarQuotationResponse(item.getDataHoraCotacao(), item.getCotacaoCompra(), item.getCotacaoVenda());
    }

    public static List<DollarQuotationResponse> from(List<DollarQuotationBCBItem> items) {
        List<DollarQuotationResponse> list = new ArrayList<>();
        items.parallelStream().forEach(item -> {
            list.add(DollarQuotationResponse.from(item));
        });
        return list;
    }
}
