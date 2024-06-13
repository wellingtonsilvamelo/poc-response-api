package br.com.tomwell.poc_response_api.dto;

public record QuoteRequest(
    String clientId, String cpf, int qtdVidas
) {
}
