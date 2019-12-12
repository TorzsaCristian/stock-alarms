package com.torzsa.stockalarms.service;

import com.torzsa.stockalarms.model.User;

public interface UserService {

    void create(User user);

    void save(User user);

    User findByEmail(String email);
}
