package com.payment_service.interfaceadapters.presenters.converters;

import com.payment_service.entities.Card;
import com.payment_service.interfaceadapters.presenters.dto.CardDto;
import org.springframework.stereotype.Component;

@Component
public class CardConverter {

    public CardDto convert(Card entity){

        CardDto dto = new CardDto();

        dto.setCardNumber(entity.getCardNumber());
        dto.setCpf(entity.getCpf());

        return dto;

    }

    public Card convert(CardDto dto){

        Card entity = new Card();

        entity.setCardNumber(dto.getCardNumber());
        entity.setCpf(dto.getCpf());

        return entity;

    }

}
