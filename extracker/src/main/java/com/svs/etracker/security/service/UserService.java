package com.svs.etracker.security.service;

import com.svs.etracker.model.User;

public interface UserService {
	void save(User user);

	User findByUsername(String username);
}
