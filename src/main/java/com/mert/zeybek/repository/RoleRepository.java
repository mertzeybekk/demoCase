package com.mert.zeybek.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mert.zeybek.model.Role;
import com.mert.zeybek.model.UserRoles;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByRoleName(UserRoles roleName);
}