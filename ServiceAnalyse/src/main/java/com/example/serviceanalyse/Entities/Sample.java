package com.example.serviceanalyse.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sample implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idSample")
    private Integer idSample;
    private Date date;
    private String numSample;
    Long idA;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="sample")
    @JsonIgnore
    Set<Test> tests;




}
