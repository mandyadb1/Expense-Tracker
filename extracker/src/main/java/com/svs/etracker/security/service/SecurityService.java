package com.svs.etracker.security.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
