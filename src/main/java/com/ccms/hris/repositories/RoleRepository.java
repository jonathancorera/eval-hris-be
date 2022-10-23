package com.ccms.hris.repositories;

import com.ccms.hris.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByName(String role);

}
