package br.com.tomwell.poc_response_api.service;

import br.com.tomwell.poc_response_api.dto.QuoteRequest;
import br.com.tomwell.poc_response_api.dto.QuoteResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class QuoteKafkaService {

    public static final String MY_GROUP = "my-group";
    public static final String POC_QUOTE_REQUEST = "poc-quote-request";

    private final SseService sseService;

    public QuoteKafkaService(SseService sseService) {
        this.sseService = sseService;
    }

    @KafkaListener(topics = POC_QUOTE_REQUEST, groupId = MY_GROUP)
    public void listen(QuoteRequest quoteRequest) {
        System.out.println("Received Message: " + quoteRequest);

        var response = QuoteResponse.builder()
            .clientId(quoteRequest.clientId())
            .effectiveDate(LocalDate.now())
            .planCode("PRT18")
            .planValue(new BigDecimal("1245.56"))
            .build();

        try {
            Thread.sleep(2000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sseService.emitEvent(quoteRequest.clientId(), response);
    }
}
