package com.progetto.BookHavenBackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderForm {

    private String recipientName;
    private String shippingAddress;

    private Long cardId;
}
