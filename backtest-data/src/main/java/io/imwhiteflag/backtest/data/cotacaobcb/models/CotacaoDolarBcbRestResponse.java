package io.imwhiteflag.backtest.data.cotacaobcb.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CotacaoDolarBcbRestResponse {
    List<CotacaoDolarValorBcbRestResponse> value;
}
