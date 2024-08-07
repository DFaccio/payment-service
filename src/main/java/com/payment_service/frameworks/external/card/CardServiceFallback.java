package com.payment_service.frameworks.external.card;

import com.payment_service.interfaceadapters.presenters.dto.CardTransactionRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardServiceFallback implements CardServiceInterface {

    @Override
    public ResponseEntity<String> newPayment(CardTransactionRequestDto cardTransactionRequestDto) {
        return new ResponseEntity<>("Erro ao processar pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> validateCard(String cpf, String numero, String data, String cvv) {
        return new ResponseEntity<>("Erro ao processar pagamento", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}