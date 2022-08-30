package io.imwhiteflag.backtest.quotation.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DollarQuotationBCBRestResponse {
    List<DollarQuotationBCBItem> value;
}
