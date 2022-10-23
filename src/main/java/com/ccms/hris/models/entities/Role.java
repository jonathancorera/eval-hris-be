package com.ccms.hris.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    @ManyToMany
    @JsonIgnoreProperties("roles")
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "roleId", referencedColumnName = "roleId"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilegeId", referencedColumnName = "privilegeId"))
    private List<Privilege> privileges;

    public Role(int roleId, String name, List<User> users, List<Privilege> privileges) {
        this.roleId = roleId;
        this.name = name;
        this.users = users;
        this.privileges = privileges;
    }

    public Role(String name) {
        this.name = name;
    }


}