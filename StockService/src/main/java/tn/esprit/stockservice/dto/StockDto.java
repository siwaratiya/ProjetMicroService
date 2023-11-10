package tn.esprit.stockservice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.stockservice.Entity.Product;
import tn.esprit.stockservice.Entity.State;
import tn.esprit.stockservice.Entity.Type_product;

import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockDto {
    Long id;
    Long nbProduct;
    String unit;
    Double total_quantity;
    Double storage;
    Double free_storage;
    Double used_storage;
    LocalDate date;
    String location;
    Type_product type_product;
    State state;
    Set<Product> products;
}
