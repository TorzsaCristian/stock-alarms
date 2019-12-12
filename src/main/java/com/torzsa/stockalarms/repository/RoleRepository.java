package com.torzsa.stockalarms.repository;

import com.torzsa.stockalarms.model.Role;
import com.torzsa.stockalarms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
