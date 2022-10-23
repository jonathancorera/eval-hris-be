package com.ccms.hris.events;


import com.ccms.hris.enums.UserStatus;
import com.ccms.hris.models.entities.Address;
import com.ccms.hris.models.entities.Privilege;
import com.ccms.hris.models.entities.Role;
import com.ccms.hris.models.entities.User;
import com.ccms.hris.repositories.PrivilegeRepository;
import com.ccms.hris.repositories.RoleRepository;
import com.ccms.hris.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Privilege createUserPrivilege
                = createPrivilegeIfNotFound("CREATE_USER");
        Privilege applyLeavePrivilege
                = createPrivilegeIfNotFound("APPLY_LEAVE");
        Privilege approveLeavePrivilegePrivilege
                = createPrivilegeIfNotFound("APPROVE_LEAVE");


        List<Privilege> adminPrivileges = Arrays.asList(
                createUserPrivilege, applyLeavePrivilege, approveLeavePrivilegePrivilege);
        createRoleIfNotFound("ADMIN", adminPrivileges);
        createRoleIfNotFound("EMPLOYEE", Arrays.asList(applyLeavePrivilege));

        if(userRepository.findByEmail("admin@ccms.com").isEmpty())
        {
            Role adminRole = roleRepository.findByName("ADMIN");
            User user = new User();
            user.setFirstName("CCMS");
            user.setLastName("Admin");
            user.setPassword(passwordEncoder.encode("password"));
            user.setEmail("admin@ccms.com");
            user.setPrimaryContactNo("077123456");
            user.setNicNumber("0000000000");
            user.setDateOfBirth(new Date());
            user.setCreatedDate(new Date());
            user.setJoinDate(new Date());
            user.setRoles(Arrays.asList(adminRole));
            Address address = new Address();
            address.setAddressLine1("123 Main Street");
            address.setAddressLine2("Home Road");
            address.setCity("Capital City");
            address.setProvince("Central");
            user.setAddress(address);
            user.setUserStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        }

    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, List<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
