/*
 * A model for the data to be sent back after authentication
 */


package com.ccms.hris.models;

import com.ccms.hris.models.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse implements Serializable {

    private String jwt;
    private UserDto user;





}
