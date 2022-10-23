package com.ccms.hris.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Designation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long designationId;

    private String designationName;
    private String designationLevel;
}
