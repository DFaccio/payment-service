package com.payment_service.framework.web;

import com.payment_service.frameworks.web.PaymentWeb;
import com.payment_service.interfaceadapters.controllers.PaymentController;
import com.payment_service.interfaceadapters.presenters.dto.RequestPaymentDto;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(PaymentWeb.class)
class PaymentWebTest {

    private MockMvc mockMvc;

    @MockBean
    private PaymentController paymentController;

    @InjectMocks
    private PaymentWeb paymentWeb;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentWeb).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testNewPayment_Success() throws Exception {
        RequestPaymentDto dto = new RequestPaymentDto();
        dto.setCpf("21910056081");
        dto.setCardNumber("5568872479420825");
        dto.setPaymentMethod("Cartão de crédito");
        dto.setPaymentDescription("Compra de um livro");
        dto.setValue(19.99);

        when(paymentController.newPayment(any(RequestPaymentDto.class)))
                .thenReturn((ResponseEntity) ResponseEntity.ok("Payment processed successfully"));

        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment processed successfully"));
    }

    @Test
    public void testNewPayment_InvalidRequest() throws Exception {
        RequestPaymentDto dto = new RequestPaymentDto();

        when(paymentController.newPayment(any(RequestPaymentDto.class)))
                .thenReturn((ResponseEntity) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some error occurred"));

        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testCheckCustomerPayments_Success() throws Exception {
        when(paymentController.checkCustomerPayments(anyString()))
                .thenReturn((ResponseEntity) ResponseEntity.ok("Customer payments retrieved"));

        mockMvc.perform(get("/pagamentos/cliente/21910056081"))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer payments retrieved"));
    }

    @Test
    public void testCheckCustomerPayments_NotFound() throws Exception {
        when(paymentController.checkCustomerPayments(anyString()))
                .thenReturn((ResponseEntity) ResponseEntity.status(HttpStatus.NOT_FOUND).body("No payments found"));

        mockMvc.perform(get("/pagamentos/cliente/21910056082"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No payments found"));
    }
}