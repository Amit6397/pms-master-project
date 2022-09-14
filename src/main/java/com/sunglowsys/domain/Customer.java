package com.sunglowsys.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    @NotNull
    @Column(name = "email", unique = true , nullable = false)
    private String email;

    @NotNull
    @Column(name = "mobile", nullable = false)
    private String mobile;

    @Column(name = "gender")
    private String gender;

}
