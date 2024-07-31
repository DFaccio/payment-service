package com.payment_service.frameworks.external.card;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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
    private final String cardAddress;

    private static final String CARD_BASE_URL_PRODUCTS = "/api/v1/card";

    private static final String CARD_POST_PAYMENT_URI = "/products/reservation";

    @Autowired
    public CardServiceInterfaceImpl(WebClient.Builder webClientBuilder, ObjectMapper mapper, String productAddress) {
        this.webClientBuilder = webClientBuilder;
        this.mapper = mapper;
        this.cardAddress = productAddress;
    }

    @Override
    public Mono<ResponseEntity> validateCard(RequestPaymentDto requestPaymentDto) throws IOException{

        return webClientBuilder.build()
                .method(HttpMethod.GET)
                .uri(cardAddress + CARD_BASE_URL_PRODUCTS)
                .body(BodyInserters.fromValue(mapper.writeValueAsString(requestPaymentDto)))
                .retrieve()
                .bodyToMono(ResponseEntity.class);

    }

    @Override
    public Mono<ResponseEntity> newPayment(RequestPaymentDto requestPaymentDto) throws IOException {

        return webClientBuilder.build()
                .post()
                .uri(cardAddress + CARD_BASE_URL_PRODUCTS)
                .body(BodyInserters.fromValue(mapper.writeValueAsString(requestPaymentDto)))
                .retrieve()
                .bodyToMono(ResponseEntity.class);

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
