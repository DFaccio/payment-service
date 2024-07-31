package com.payment_service.util.pagination;

import com.payment_service.interfaceadapters.presenters.dto.PaymentDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResponse {

    private Pagination page;

    private List<PaymentDto> data;
}
