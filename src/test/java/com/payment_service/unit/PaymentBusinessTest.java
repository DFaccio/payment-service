package com.payment_service.unit;


import com.payment_service.entities.Payment;
import com.payment_service.frameworks.db.PaymentRepository;
import com.payment_service.interfaceadapters.gateways.PaymentGateway;
import com.payment_service.interfaceadapters.presenters.converters.PaymentConverter;
import com.payment_service.interfaceadapters.presenters.dto.CheckPaymentsDto;
import com.payment_service.usercase.PaymentBusiness;
import com.payment_service.util.enums.PaymentStatus;
import com.payment_service.util.time.TimeUtils;
import com.payment_service.utils.TestUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

class PaymentBusinessTest extends TestUtils {

    @Resource
    @InjectMocks
    private PaymentBusiness paymentBusiness;

    @Mock
    private PaymentGateway paymentGateway;

    @Resource
    private PaymentConverter paymentConverter;

    @Resource
    private PaymentRepository paymentRepository;

    private Payment payment;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        payment = paymentRepository.save(newPayment());
    }

    @AfterEach
    void cleanUp() {
        paymentRepository.deleteAll();
    }

    @Test
    void newPaymentTest(){



    }

    @Test
    void checkCustomerPaymentsTest(){

        CheckPaymentsDto checkPaymentsDto = checkPaymentsDto();

        Optional<Payment> optional = paymentGateway.checkCustomerPayments("43556112566");
        ResponseEntity<?> response = paymentBusiness.checkCustomerPayments(optional);

        assertEquals(checkPaymentsDto, response.getBody());

    }

    private Payment newPayment(){

        Payment entity = new Payment();

        entity.setCpf("43556112566");
        entity.setCardNumber("5568872479420825");
        entity.setPaymentMethod("Cartão de crédito");
        entity.setPaymentDescription("Compra de um livro");
        entity.setPaymentValue(BigDecimal.valueOf(150.00));
        entity.setTransactionDate(TimeUtils.now());
        entity.setCardTransactionId("123456");
        entity.setPaymentStatus(PaymentStatus.CONFIRMED);

        return entity;

    }

    private CheckPaymentsDto checkPaymentsDto(){

        CheckPaymentsDto checkPaymentsDto = new CheckPaymentsDto();

        checkPaymentsDto.setPaymentValue(BigDecimal.valueOf(150.00));
        checkPaymentsDto.setPaymentMethod("Cartão de crédito");
        checkPaymentsDto.setPaymentDescription("Compra de um livro");
        checkPaymentsDto.setPaymentStatus(PaymentStatus.CONFIRMED);

        return checkPaymentsDto;

    }

}
