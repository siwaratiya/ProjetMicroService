package tn.esprit.stockservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.stockservice.Entity.Stock;
import tn.esprit.stockservice.Entity.Type_product;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    Long id;
    String name_product;
    String reference;
    Double price;
    Double size_product;
    String description;
    String image;
    Long count_order;
    Type_product type_product;
    Double quantity;
    Set<Stock> stocks;
}
