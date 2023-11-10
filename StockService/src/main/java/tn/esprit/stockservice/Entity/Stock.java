package tn.esprit.stockservice.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long nbProduct;
    String unit;
    Double total_quantity;
    Double storage;
    Double free_storage;
    Double used_storage;
    LocalDate date;
    String location;

    @Enumerated(EnumType.STRING)
    Type_product type_product;
    @Enumerated(EnumType.STRING)
    State state;


    @ManyToMany(cascade = CascadeType.ALL)
    Set<Product> products;
}
