package com.svs.etracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.svs.etracker.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
