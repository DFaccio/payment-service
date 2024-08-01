package com.payment_service.frameworks.external.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@Transactional
@Log4j2
public class CardServiceInterfaceImpl implements CardServiceInterface{

    private final WebClient.Builder webClientBuilder;

    private final ObjectMapper mapper;

    @Value("${card.address}")
    private String cardAddress;

    private static final String CARD_BASE_URL_PRODUCTS = "/api/v1/card";

    @Autowired
    public CardServiceInterfaceImpl(WebClient.Builder webClientBuilder, ObjectMapper mapper) {
        this.webClientBuilder = webClientBuilder;
        this.mapper = mapper;
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public ResponseEntity<?> validateCard(RequestPaymentDto requestPaymentDto) throws IOException{

        return webClientBuilder.build()
                .method(HttpMethod.GET)
                .uri(cardAddress + CARD_BASE_URL_PRODUCTS)
                .body(BodyInserters.fromValue(mapper.writeValueAsString(requestPaymentDto)))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                        .bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody))
                        )
                )
                .bodyToMono(ResponseEntity.class).block();

    }

    @Override
    public ResponseEntity<?> newPayment(RequestPaymentDto requestPaymentDto) throws IOException {

        return webClientBuilder.build()
                .post()
                .uri(cardAddress + CARD_BASE_URL_PRODUCTS)
                .body(BodyInserters.fromValue(mapper.writeValueAsString(requestPaymentDto)))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse
                        .bodyToMono(String.class)
                        .flatMap(errorBody -> Mono.error(new RuntimeException("Error: " + errorBody))
                        )
                )
                .bodyToMono(ResponseEntity.class).block();

    }

//    private Mono<ResponseEntity<?>> convertValidateCardResponse(ResponseEntity<?> response){
//
//        ResponseEntity<?> responseEntity;
//        responseEntity.getStatusCode() response.getStatusCode();
//        response.getBody();
//        CardResponseDto cardResponse = new CardResponseDto();
//
//        cardResponse = response.get("cardResponse");
//        response.
//
//        return null;
//
//    }

}
