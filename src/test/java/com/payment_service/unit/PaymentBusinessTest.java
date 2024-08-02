package com.payment_service.unit;


import com.payment_service.interfaceadapters.presenters.converters.PaymentConverter;
import com.payment_service.usercase.PaymentBusiness;
import com.payment_service.utils.TestUtils;
import jakarta.annotation.Resource;

class PaymentBusinessTest extends TestUtils {

    @Resource
    private PaymentBusiness paymentBusiness;

    @Resource
    private PaymentConverter paymentConverter;

}
