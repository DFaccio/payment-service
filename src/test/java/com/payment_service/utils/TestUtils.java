package com.payment_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_service.PaymentServiceApplication;
import jakarta.annotation.Resource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = PaymentServiceApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class TestUtils {

    private static final String SOURCE_ROOT = "src/test/resources/mocks/";

    @Resource
    protected ObjectMapper objectMapper;

    protected File getFile(String path) {
        return new File(SOURCE_ROOT + path);
    }

    protected String getMock(String filename, Class<?> type) {
        File file = getFile(filename);

        try {
            Object object = objectMapper.readValue(file, type);

            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Não foi possível converter o objeto!", e);
        }
    }

    protected Object getResponseBody(MockHttpServletResponse response, Class<?> type) throws JsonProcessingException, UnsupportedEncodingException {
        return objectMapper.readValue(response.getContentAsString(StandardCharsets.UTF_8), type);
    }
}