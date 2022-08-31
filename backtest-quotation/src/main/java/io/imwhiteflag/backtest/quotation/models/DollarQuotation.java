package io.imwhiteflag.backtest.quotation.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class DollarQuotation extends PanacheEntityBase {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "request_timestamp", updatable = false, nullable = false)
    private Instant requestTimestamp;

    @Column(name = "quotation_date", updatable = false)
    private LocalDate quotationDate;

    @Column(name = "buy_price")
    private BigDecimal buyPrice;

    @Column(name = "sell_price")
    private BigDecimal sellPrice;

    @Column(name = "quotation_date_hour")
    private LocalDateTime quotationDateHour;
}
