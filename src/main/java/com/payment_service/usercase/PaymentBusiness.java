package com.payment_service.usercase;

import com.payment_service.util.enums.CardResponse;
import com.payment_service.util.exception.ValidationsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PaymentBusiness {

    public void validateCardResponse(ResponseEntity<?> cardResponse) throws ValidationsException {

        if(cardResponse.getStatusCode().equals(HttpStatus.PAYMENT_REQUIRED)){

            throw new ValidationsException("0001");

        }else if(cardResponse.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)){

            if(Objects.equals(cardResponse.getBody(), CardResponse.INVALID_CVV)){

                throw new ValidationsException("0002");

            }else if(Objects.equals(cardResponse.getBody(), CardResponse.INVALID_EXPIRATION_DATE)){

                throw new ValidationsException("003");

            }else if(Objects.equals(cardResponse.getBody(), CardResponse.CARD_EXPIRED)){

                throw new ValidationsException("004");

            }else if(Objects.equals(cardResponse.getBody(), CardResponse.INVALID_CARD_NUMBER)){

                throw new ValidationsException("005");

            }else if(Objects.equals(cardResponse.getBody(), CardResponse.INVALID_CPF)){

                throw new ValidationsException("006");

            }

        }

    }

}
