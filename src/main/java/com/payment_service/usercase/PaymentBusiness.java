package com.payment_service.usercase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.payment_service.entities.Payment;
import com.payment_service.frameworks.helper.PaymentHelper;
import com.payment_service.interfaceadapters.presenters.converters.PaymentConverter;
import com.payment_service.interfaceadapters.presenters.dto.*;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.exception.ExceptionHandlerUtil;
import com.payment_service.util.exception.ExternalInterfaceException;
import com.payment_service.util.exception.NotFoundException;
import com.payment_service.util.exception.ValidationsException;
import com.payment_service.util.time.TimeUtils;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class PaymentBusiness {

    @Resource
    private PaymentHelper paymentHelper;

    @Resource
    private PaymentConverter paymentConverter;


    public ResponseEntity<?> newPayment(RequestPaymentDto requestPaymentDto) throws ExternalInterfaceException, IOException {

        String message = "";

        if(requestPaymentDto.getValue() <= 0){
            message = "0101";
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException(message));
        }

        if(requestPaymentDto.getCvv().isEmpty() || Objects.isNull(requestPaymentDto.getCvv())){
            message = "0102";
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException(message));
        }

        if(requestPaymentDto.getExpirationDate().isEmpty() || Objects.isNull(requestPaymentDto.getExpirationDate())){
            message = "0103";
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException(message));
        }

        if(requestPaymentDto.getCpf().isEmpty() || Objects.isNull(requestPaymentDto.getCpf())){
            message = "0104";
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException(message));
        }

        if(requestPaymentDto.getCardNumber().isEmpty() || Objects.isNull(requestPaymentDto.getCardNumber())){
            message = "0105";
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException(message));
        }

        if(requestPaymentDto.getPaymentDescription().isEmpty() || Objects.isNull(requestPaymentDto.getPaymentDescription())){
            message = "0106";
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException(message));
        }

        if(requestPaymentDto.getPaymentMethod().isEmpty() || Objects.isNull(requestPaymentDto.getPaymentMethod())){
            message = "0107";
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException(message));
        }

        paymentHelper.validateCard(requestPaymentDto);

        CardTransactionRequestDto cardTransactionRequestDto = newCardTransactionRequestDto(requestPaymentDto);

        CardTransactionResponseDto cardTransactionResponseDto = (CardTransactionResponseDto) paymentHelper.newPayment(cardTransactionRequestDto).getBody();

        PaymentDto paymentDto = newPaymentDto(requestPaymentDto, cardTransactionResponseDto);

        return ResponseEntity.ok(paymentDto);

    }

    public ResponseEntity<?> toResponse(Payment payment){

        PaymentDto paymentDto;

        paymentDto = paymentConverter.convert(payment);

        return ResponseEntity.ok(paymentDto);

    }

    public ResponseEntity<?> checkCustomerPayments(Optional<List<Payment>> optionalPayment) throws JsonProcessingException {

        List<CheckPaymentsDto> checkPaymentsDto = new ArrayList<>();

        if(optionalPayment.isPresent()){

            List<Payment> payments = optionalPayment.get();

            for(Payment payment : payments){
                CheckPaymentsDto dto = new CheckPaymentsDto();

                dto.setPaymentId(payment.getPaymentId());
                dto.setPaymentValue(payment.getPaymentValue());
                dto.setPaymentDescription(payment.getPaymentDescription());
                dto.setPaymentMethod(payment.getPaymentMethod());
                dto.setPaymentStatus(payment.getPaymentStatus());

                checkPaymentsDto.add(dto);
            }


        }else{
            return new ExceptionHandlerUtil().paymentsNotFound(new NotFoundException("0301"));
        }

        return ResponseEntity.ok(checkPaymentsDto);

    }

    private CardTransactionRequestDto newCardTransactionRequestDto(RequestPaymentDto requestPaymentDto) {
        CardTransactionRequestDto cardTransactionRequestDto = new CardTransactionRequestDto();

        cardTransactionRequestDto.setCpf(requestPaymentDto.getCpf());
        cardTransactionRequestDto.setCardNumber(requestPaymentDto.getCardNumber());
        cardTransactionRequestDto.setExpirationDate(requestPaymentDto.getExpirationDate());
        cardTransactionRequestDto.setCvv(requestPaymentDto.getCvv());
        cardTransactionRequestDto.setValue(requestPaymentDto.getValue());

        return cardTransactionRequestDto;
    }

    private PaymentDto newPaymentDto(RequestPaymentDto requestPaymentDto, CardTransactionResponseDto cardTransactionResponseDto){

        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setCpf(requestPaymentDto.getCpf());
        paymentDto.setCardNumber(requestPaymentDto.getCardNumber());
        paymentDto.setPaymentMethod(requestPaymentDto.getPaymentMethod());
        paymentDto.setPaymentDescription(requestPaymentDto.getPaymentDescription());
        paymentDto.setPaymentValue(requestPaymentDto.getValue());
        paymentDto.setTransactionDate(TimeUtils.now());
        paymentDto.setCardTransactionId(cardTransactionResponseDto.getPaymentId());
        paymentDto.setPaymentStatus(PaymentStatus.APPROVED);

        return paymentDto;
    }

}
