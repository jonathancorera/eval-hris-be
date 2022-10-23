package com.ccms.hris.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @NotBlank(message = "Address Line 1 is mandatory")
    private String addressLine1;

    @NotBlank(message = "Address Line 2 is mandatory")
    private String addressLine2;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Province is mandatory")
    private String province;

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private User user;


}
