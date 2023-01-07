package com.kata312.service;

import com.kata312.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    void save(User user);



    void deleteUser(User user);
    User findUserByEmail(String email);

    String getRolesToString(User user);


}




