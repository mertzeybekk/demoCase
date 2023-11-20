package com.mert.zeybek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRoles roleName;

    public Role(Integer id, UserRoles roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role() {

    }

    public Role(UserRoles name) {
        this.roleName = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRoles getName() {
        return roleName;
    }

    public void setName(UserRoles name) {
        this.roleName = name;
    }
}