package com.machine.project.service;

import com.machine.project.domain.User;

import java.util.Collection;


public interface UserService {

    User save(User user);

    Boolean delete(String id);

    User update(User user);

    User findById(String id);

    Collection<User> findAll();

}
