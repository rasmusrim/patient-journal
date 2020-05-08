package com.rasmusrim.patient.journal.models;

import com.sun.istack.NotNull;
import com.sun.xml.bind.annotation.OverrideAnnotationOf;
import lombok.*;

import javax.persistence.*;

@Table(indexes = @Index(columnList = "lastName, firstName"))
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient extends PersistableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName = "";

    private String lastName = "";
    private String address;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int age;

    public long getId() {
        return id;
    }

    public String toString()
    {
        return lastName + ", " + firstName;
    }

}


