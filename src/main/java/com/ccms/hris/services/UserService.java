package com.ccms.hris.services;


import com.ccms.hris.enums.UserStatus;
import com.ccms.hris.models.dto.UserCreationDto;
import com.ccms.hris.models.dto.UserDto;
import com.ccms.hris.models.entities.Role;
import com.ccms.hris.models.entities.User;
import com.ccms.hris.repositories.RoleRepository;
import com.ccms.hris.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	PasswordEncoder passwordEncoder;


	public List<UserDto> getAllUsers (){

		List<User> userList = userRepo.findAll();
		List<UserDto> returnedUserList = new ArrayList<>();

		for (User user: userList)
		{
			returnedUserList.add(convertUserToUserDto(user));
		}

		return  returnedUserList;
	}

	public Page<UserDto> getAllUsers(int pageNo, int pageSize) {
		Page<User> userList = userRepo.findAll(PageRequest.of(pageNo-1, pageSize));
		return userToUserDtoPage(userList);
	}

	public void createUser(UserCreationDto userDto) {
		User user = new User();

		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setOtherNames(userDto.getOtherNames());
		user.setPrimaryContactNo(userDto.getPrimaryContactNo());
		user.setSecondaryContactNo(userDto.getSecondaryContactNo());
		user.setEmail(userDto.getEmail());
		user.setNicNumber(userDto.getNicNo());
		user.setDateOfBirth(userDto.getDateOfBirth());
		user.setJoinDate(userDto.getJoinDate());
		user.setGender(userDto.getGender());

		user.setAddress(userDto.getAddress());
		user.setDesignation(userDto.getDesignation());

		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		Role userRole = roleRepo.findByName("EMPLOYEE");
		user.setRoles(Arrays.asList(userRole));

		user.setUserStatus(UserStatus.ACTIVE);

		user.setCreatedDate(new Date());

		userRepo.save(user);
	
	}


	public User getUserById (Long id) {
		User foundUser = userRepo.findById(id).get();
		return foundUser;
	}



	public void updateUser(UserCreationDto userDto, long userId) throws Exception {

		Optional<User> userExists = userRepo.findById(userId);

		if(userExists.isEmpty()) throw new Exception("User does not exist");

		User user = userExists.get();

		if(userDto.getFirstName() != null)  user.setFirstName(userDto.getFirstName());
		if(userDto.getLastName() != null) user.setLastName(userDto.getLastName());
		if(userDto.getOtherNames() != null) user.setOtherNames(userDto.getOtherNames());
		if(userDto.getEmail() != null) user.setEmail(userDto.getEmail());
		if(userDto.getNicNo() != null) user.setNicNumber(userDto.getNicNo());
		if(userDto.getDateOfBirth() != null) user.setDateOfBirth(userDto.getDateOfBirth());
		if(userDto.getPrimaryContactNo() != null) user.setPrimaryContactNo(userDto.getPrimaryContactNo());
		if(userDto.getSecondaryContactNo() != null) user.setSecondaryContactNo(userDto.getSecondaryContactNo());

		if(userDto.getAddress()!= null) user.setAddress(userDto.getAddress());
		if(userDto.getDesignation() != null) user.setDesignation(userDto.getDesignation());
		if(userDto.getBankDetails() != null) user.setBankDetails(userDto.getBankDetails());

		userRepo.save(user);

	}


	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}

	public User findUserByEmail (String username) {
		return userRepo.findByEmail(username).get();
	}


	public void setUserDeactivated (User user) {
		Long updateUserId = user.getUserId();
		User updateUser = userRepo.findById(updateUserId).get();
		updateUser.setUserStatus(UserStatus.DEACTIVATED);
		userRepo.save(updateUser);
	}

	public void setUserActivated (User user) {
		Long updateUserId = user.getUserId();
		User updateUser = userRepo.findById(updateUserId).get();
		updateUser.setUserStatus(UserStatus.ACTIVE);
		userRepo.save(updateUser);
	}

	public Page<UserDto> findByUserStatus (UserStatus userStatus, int pageNo, int pageSize) {

		Page<User> userList = userRepo.findByUserStatus (userStatus, PageRequest.of(pageNo, pageSize));

		return userToUserDtoPage(userList);

	}


	public Page<UserDto> userToUserDtoPage(Page<User> users) {
		Page<UserDto> dtos  = users.map(this::convertUserToUserDto);
		return dtos;
	}


	private UserDto convertUserToUserDto(User user) {
		UserDto dto = new UserDto();
		dto.setUserId(user.getUserId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setEmail(user.getEmail());
		dto.setUserStatus(user.getUserStatus());
		dto.setRoles(user.getRoles());
		dto.setDesignation(user.getDesignation());

		return dto;
	}
}
