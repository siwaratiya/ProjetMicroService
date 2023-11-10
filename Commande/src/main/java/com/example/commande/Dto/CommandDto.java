package com.example.commande.Dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter @ToString
@Builder @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandDto {
    Long id;
    Date date;
    String notice;
    Long quantity;
    double total_price;

    Long idProduct;
    String name_product;
    double price_product;
    Long account_id;
    AccountDto accountDto;
}

