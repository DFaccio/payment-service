package com.payment_service.frameworks.external.card;

import com.payment_service.interfaceadapters.presenters.dto.CardTransactionRequestDto;
import com.payment_service.interfaceadapters.presenters.dto.CardTransactionResponseDto;
import com.payment_service.util.MessageUtil;
import com.payment_service.util.exception.ExternalInterfaceException;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class CardServiceInterfaceImpl implements CardServiceInterface {

    @Autowired
    private final CardServiceInterface cardServiceInterface;

    @Autowired
    public CardServiceInterfaceImpl(CardServiceInterface cardServiceInterface) {
        this.cardServiceInterface = cardServiceInterface;
    }

    @Override
    public ResponseEntity<?> validateCard(String cpf, String cardNumber, String expirationDate, String cvv) throws ExternalInterfaceException {
        try {
            return cardServiceInterface.validateCard(cpf, cardNumber, expirationDate, cvv);
        } catch (FeignException exception) {
            throw new ExternalInterfaceException(MessageUtil.getMessage("0401"));
        }
    }

    @Override
    public ResponseEntity<CardTransactionResponseDto> newPayment(CardTransactionRequestDto cardTransactionRequestDto) throws ExternalInterfaceException {
        try {
            return cardServiceInterface.newPayment(cardTransactionRequestDto);
        } catch (FeignException exception) {
            throw new ExternalInterfaceException(MessageUtil.getMessage("0401"));
        }
    }

}
