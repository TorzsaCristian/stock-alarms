package com.torzsa.stockalarms.service;

public interface SecurityService {
    String findLoggedInEmail();

    void autoLogin(String email, String password);
}
