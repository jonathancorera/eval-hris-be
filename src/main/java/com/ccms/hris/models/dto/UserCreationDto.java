package com.ccms.hris.models.dto;

import com.ccms.hris.models.entities.Address;

import com.ccms.hris.models.entities.BankDetails;
import com.ccms.hris.models.entities.Designation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {

    private String email;
    private String firstName;
    private String lastName;
    private String otherNames;
    private String password;
    private String primaryContactNo;
    private String secondaryContactNo;
    private Date dateOfBirth;
    private String gender;
    private String nicNo;

    private Address address;
    private BankDetails bankDetails;
    private Designation designation;

    private Date joinDate;


}
