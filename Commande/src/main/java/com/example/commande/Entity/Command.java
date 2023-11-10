package com.example.commande.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Command implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    //@NonNull
    LocalDate date;

    String notice;

    Long quantity_product;

    Long NbPoduct;


    Double Total_price;

    /*@ManyToMany
    List<Product> products;*/

    @JsonIgnore
    @OneToMany(mappedBy = "command")
    Set<CommandLigne> commandLignes;

    Long idA;
}
