package com.example.serviceanalyse.Entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTest")
    private int idTest;
    private String nameTest;
    private float price;
    @ManyToOne
    Sample sample;

}

