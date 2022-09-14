package com.sunglowsys.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "code",unique = true)
    private String code;

    @NotNull
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "email",unique = true)
    private String email;

    @NotNull
    @Column(name = "mobile")
    private String mobile;

}
