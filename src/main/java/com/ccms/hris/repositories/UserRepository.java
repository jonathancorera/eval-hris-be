package com.ccms.hris.repositories;

import com.ccms.hris.models.entities.User;
import com.ccms.hris.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    Page<User> findAll(Pageable pageable);
    List<User> findAll ();

    Optional<User> findByEmail(String email);

    List<User> findByAddress_City(String city);
    Page<User> findByAddress_City(String city, Pageable pageable);

    List<User> findByUserStatus(UserStatus userstatus);
    Page<User> findByUserStatus(UserStatus userstatus, Pageable pageable);

}
