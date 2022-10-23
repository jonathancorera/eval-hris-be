package com.ccms.hris.models.dto;



import com.ccms.hris.enums.UserStatus;
import com.ccms.hris.models.entities.Address;
import com.ccms.hris.models.entities.Designation;
import com.ccms.hris.models.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private long userId;
    private String email;
    private String firstName;
    private String lastName;
    private UserStatus userStatus;

    @JsonIgnoreProperties("privileges")
    private List<Role> roles;

    private Designation designation;


}
