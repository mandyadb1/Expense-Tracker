package com.svs.etracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svs.etracker.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}
