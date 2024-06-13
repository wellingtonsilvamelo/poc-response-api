package br.com.tomwell.poc_response_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteResponse implements Serializable {

    private String clientId;
    private BigDecimal planValue;
    private String planCode;
    private LocalDate effectiveDate;

}
