package com.rasmusrim.patient.journal.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter @NoArgsConstructor
@Table
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String address;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int age;

}
