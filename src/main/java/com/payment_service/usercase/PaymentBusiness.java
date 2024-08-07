package com.payment_service.usercase;

import com.payment_service.entities.Payment;
import com.payment_service.frameworks.helper.PaymentHelper;
import com.payment_service.interfaceadapters.presenters.converters.PaymentConverter;
import com.payment_service.interfaceadapters.presenters.dto.*;
import com.payment_service.util.enums.CardResponse;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.exception.*;
import com.payment_service.util.time.TimeUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Component
public class PaymentBusiness {

    @Resource
    private PaymentHelper paymentHelper;

    @Resource
    private PaymentConverter paymentConverter;


    public ResponseEntity<?> newPayment(RequestPaymentDto requestPaymentDto) throws ExternalInterfaceException {

        String message = "";

        if(requestPaymentDto.getValue().compareTo(BigDecimal.ZERO) <= 0){
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

        ResponseEntity<?> cardValidationRequest = paymentHelper.validateCard(requestPaymentDto);

        if(cardValidationRequest.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){

            StandardError body = (StandardError) cardValidationRequest.getBody();
            message = body.getMessage();

            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException(message));
        }

        CardTransactionRequestDto cardTransactionRequestDto = newCardTransactionRequestDto(requestPaymentDto);

        ResponseEntity<?> newPaymentRequest = paymentHelper.newPayment(cardTransactionRequestDto);

        if(newPaymentRequest.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){

            StandardError body = (StandardError) newPaymentRequest.getBody();
            message = body.getMessage();

            if(StringUtils.equals(message, CardResponse.UNREPORTED_DATA_ERROR.toString())) {
                return new ExceptionHandlerUtil().cardValidationError(new ValidationsException("0201"));
            }else if(StringUtils.equals(message, CardResponse.CARD_NOT_FOUND.toString())){
                return new ExceptionHandlerUtil().cardValidationError(new ValidationsException("0202"));
            }else if(StringUtils.equals(message, CardResponse.INSUFFICIENT_CARD_LIMIT.toString())){
                return new ExceptionHandlerUtil().cardLimitError(new CardLimitException("0203"));
            }

        }

        CardTransactionResponseDto cardTransactionResponseDto = (CardTransactionResponseDto) Objects.requireNonNull(newPaymentRequest.getBody());

        PaymentDto paymentDto = newPaymentDto(requestPaymentDto, cardTransactionResponseDto);

        return ResponseEntity.ok(paymentDto);

    }

    public ResponseEntity<?> toResponse(Payment payment){

        PaymentDto paymentDto;

        paymentDto = paymentConverter.convert(payment);

        return ResponseEntity.ok(paymentDto);

    }

    public ResponseEntity<?> checkCustomerPayments(Optional<Payment> optionalPayment){

        CheckPaymentsDto checkPaymentsDto = new CheckPaymentsDto();

        if(optionalPayment.isPresent()){

            Payment payment = optionalPayment.get();

            checkPaymentsDto.setPaymentValue(payment.getPaymentValue());
            checkPaymentsDto.setPaymentDescription(payment.getPaymentDescription());
            checkPaymentsDto.setPaymentMethod(payment.getPaymentMethod());
            checkPaymentsDto.setPaymentStatus(payment.getPaymentStatus());

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
        paymentDto.setPaymentStatus(PaymentStatus.CONFIRMED);

        return paymentDto;
    }

}
