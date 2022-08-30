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
@Entity(name = "dollar_quotations")
public class DollarQuotation extends PanacheEntity {
    private UUID id;
    private Instant requestTimestamp;
    private LocalDate quotationDate;
    private BigDecimal buyPrice;
    private BigDecimal sellPrice;
    private LocalDateTime quotationDateHour;
}
