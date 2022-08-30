package io.imwhiteflag.backtest.quotation.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "consulta_cotacao_dolar")
public class DolarQuotation extends PanacheEntity {
    private UUID id;
    private Instant timestampRequisicao;
    private LocalDate dataCotacao;
    private BigDecimal valorCompra;
    private BigDecimal valorVenda;
    private LocalDateTime dataHoraCotacao;
}
