package io.imwhiteflag.backtest.quotation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static DollarQuotationResponse fromEntity(DollarQuotation item) {
        return  new DollarQuotationResponse(item.getQuotationDateHour().toString(), item.getBuyPrice(), item.getSellPrice());
    }

    public static List<DollarQuotationResponse> from(List<DollarQuotationBCBItem> items) {
        List<DollarQuotationResponse> list = new ArrayList<>();
        items.parallelStream().forEach(item -> list.add(DollarQuotationResponse.from(item)));
        return list;
    }

    public static Map<String, List<DollarQuotationResponse>> fromEntity(List<DollarQuotation> items) {
        Map<String, List<DollarQuotationResponse>> response = new HashMap<>();
        List<DollarQuotationResponse> list = new ArrayList<>();
        items.parallelStream().forEach(item -> list.add(DollarQuotationResponse.fromEntity(item)));
        response.put("quotations", list);
        return response;
    }
}
