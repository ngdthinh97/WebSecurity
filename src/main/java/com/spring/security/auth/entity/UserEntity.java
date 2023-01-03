package com.spring.security.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String role;

    @ManyToMany(
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "user_groups",
            joinColumns = @JoinColumn(name = "customers_id"),
            inverseJoinColumns = @JoinColumn(name = "groups_id"
            ))
    private Set<PrincipleGroup> userGroups = new HashSet<>();
}
