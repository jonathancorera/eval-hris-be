package com.ccms.hris.models.entities;

import com.ccms.hris.enums.BankAccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bankDetailsId;

    private String accountNo;
    private String bankName;
    private String bankBranch;

    @Enumerated(value = EnumType.ORDINAL)
    private BankAccountType bankAccountType;
}
