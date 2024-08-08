package com.payment_service.util.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_service.util.enums.CardResponse;
import feign.FeignException;
import feign.FeignException.FeignClientException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerUtil extends Throwable {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerUtil.class);

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<StandardError> cardValidationError(ValidationsException e){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError(status.value(),
                "Data Error",
                e.getMessage(),
                "/pagamentos");

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(CardLimitException.class)
    public ResponseEntity<StandardError> cardLimitError(CardLimitException e){
        HttpStatus status = HttpStatus.PAYMENT_REQUIRED;

        StandardError error = new StandardError(status.value(),
                "Limit error",
                e.getMessage(),
                "/pagamentos");

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> paymentsNotFound(NotFoundException e) throws JsonProcessingException {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError(status.value(),
                "Not found",
                e.getMessage(),
                "/pagamentos");

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler({ FeignException.class, FeignClientException.class })
    public ResponseEntity<?> handleFeignException(final FeignException ex) throws JsonProcessingException {
        logger.error(ex.getMessage(), ex);

        ObjectMapper objectMapper = new ObjectMapper();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        StandardError error = new StandardError();
        error = objectMapper.readValue(ex.contentUTF8(), StandardError.class);

        if(StringUtils.equals(error.getError(), CardResponse.UNREPORTED_DATA_ERROR.toString())) {
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException("0201"));
        }else if(StringUtils.equals(error.getError(), CardResponse.CARD_NOT_FOUND.toString())){
            return new ExceptionHandlerUtil().cardValidationError(new ValidationsException("0202"));
        }else if(StringUtils.equals(error.getError(), CardResponse.INSUFFICIENT_CARD_LIMIT.toString())){
            return new ExceptionHandlerUtil().cardLimitError(new CardLimitException("0203"));
        }

        return ResponseEntity.status(status).body(error);
    }

}