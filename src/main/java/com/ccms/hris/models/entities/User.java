package com.ccms.hris.models.entities;

import com.ccms.hris.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class User {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@NotBlank(message = "email cannot be blank")
	@Column(unique = true)
	private String email;

	@NotBlank(message = "Password  cannot be blank")
	@JsonIgnore
	private String password;
	
	@NotBlank(message = "First name cannot be blank")
	private String firstName;
	
	@NotBlank(message = "Last name cannot be blank")
	private String lastName;

	private String otherNames;

	@NotBlank(message = "Primary contact number cannot be blank")
	private String primaryContactNo;

	private String secondaryContactNo;

	@Enumerated(value = EnumType.ORDINAL)
	private UserStatus userStatus;

	private String gender;

	@NotBlank(message = "NIC Number cannot be blank")
	@Column(unique = true)
	private String nicNumber;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Temporal(TemporalType.DATE)
	private Date joinDate;

	@Temporal(TemporalType.DATE)
	private Date leftDate;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addressId", referencedColumnName = "addressId")
	private Address address;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bankDetailsId", referencedColumnName = "bankDetailsId")
	private BankDetails bankDetails;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "designationId", referencedColumnName = "designationId")
	private Designation designation;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "userId", referencedColumnName = "userId"),
			inverseJoinColumns = @JoinColumn(
					name = "roleId", referencedColumnName = "roleId"))
	private List<Role> roles;

}
