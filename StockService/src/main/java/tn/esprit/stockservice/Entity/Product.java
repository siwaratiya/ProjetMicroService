package tn.esprit.stockservice.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name_product;
    String reference;
    Double price;
    Double size_product;
    String description;
    String image;
    Long count_order;

    Double quantity;

    @Enumerated(EnumType.STRING)
    Type_product type_product;

    @JsonIgnore
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    Set<Stock> stocks;


}
