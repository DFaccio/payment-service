package com.payment_service.usercase;

import com.payment_service.entities.Payment;
import com.payment_service.frameworks.helper.PaymentHelper;
import com.payment_service.interfaceadapters.presenters.converters.PaymentConverter;
import com.payment_service.interfaceadapters.presenters.dto.CheckPaymentsDto;
import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import com.payment_service.util.enums.CardResponse;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.exception.ExceptionHandlerUtil;
import com.payment_service.util.exception.ValidationsException;
import com.payment_service.util.time.TimeUtils;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Component
public class PaymentBusiness {

    @Resource
    private PaymentHelper paymentHelper;

    @Resource
    private PaymentConverter paymentConverter;


    public ResponseEntity<?> newPayment(RequestPaymentDto requestPaymentDto) throws IOException {

        String error = "";

        if(requestPaymentDto.getValue().compareTo(BigDecimal.ZERO) <= 0){

            error = "0001";

            return new ExceptionHandlerUtil().validationsError(new ValidationsException(error));

        }

        ResponseEntity<?> validateCard = paymentHelper.validateCard(requestPaymentDto);

        if(validateCard.getStatusCode().equals(HttpStatus.PAYMENT_REQUIRED)){

            error = "0101";

            return new ExceptionHandlerUtil().cardLimitError(new ValidationsException(error));

        }else if(validateCard.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){

            if(Objects.equals(validateCard.getBody(), CardResponse.INVALID_CVV.toString())){
                error = "0102";
            }else if(Objects.equals(validateCard.getBody(), CardResponse.INVALID_EXPIRATION_DATE.toString())){
                error = "0103";
            }else if(Objects.equals(validateCard.getBody(), CardResponse.CARD_EXPIRED.toString())){
                error = "0104";
            }else if(Objects.equals(validateCard.getBody(), CardResponse.INVALID_CARD_NUMBER.toString())){
                error = "0105";
            }else if(Objects.equals(validateCard.getBody(), CardResponse.INVALID_CPF.toString())){
                error = "0106";
            }

            return new ExceptionHandlerUtil().validationsError(new ValidationsException(error));

        }

        ResponseEntity<?> newPayment = paymentHelper.newPayment(requestPaymentDto);

        PaymentDto paymentDto = new PaymentDto();

        paymentDto.setCpf(requestPaymentDto.getCpf());
        paymentDto.setCardNumber(requestPaymentDto.getCardNumber());
        paymentDto.setPaymentMethod(requestPaymentDto.getPaymentMethod());
        paymentDto.setPaymentDescription(requestPaymentDto.getPaymentDescription());
        paymentDto.setPaymentValue(requestPaymentDto.getValue());
        paymentDto.setTransactionDate(TimeUtils.now());
        paymentDto.setCardTransactionId(newPayment.getBody().toString());
        paymentDto.setPaymentStatus(PaymentStatus.CONFIRMED);

        return ResponseEntity.ok(paymentDto);

    }

    public ResponseEntity<?> toResponse(Payment payment){

        PaymentDto paymentDto;

        paymentDto = paymentConverter.convert(payment);

        return ResponseEntity.ok(paymentDto);

    }

    public ResponseEntity<?> checkPayments(Optional<Payment> optionalPayment){

        CheckPaymentsDto checkPaymentsDto = new CheckPaymentsDto();

        if(optionalPayment.isPresent()){

            Payment payment = optionalPayment.get();

            checkPaymentsDto.setPaymentValue(payment.getPaymentValue());
            checkPaymentsDto.setPaymentDescription(payment.getPaymentDescription());
            checkPaymentsDto.setPaymentMethod(payment.getPaymentMethod());
            checkPaymentsDto.setPaymentStatus(payment.getPaymentStatus());

        }else{

            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.ok(checkPaymentsDto);

    }

}
