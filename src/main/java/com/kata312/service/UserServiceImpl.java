package com.kata312.service;

import com.kata312.DAO.UserDAO;

import com.kata312.model.Role;
import com.kata312.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service

public class UserServiceImpl  implements UserService {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;

    }




@Transactional
    @Override
    public void updateUser(User user) {
        User userFromBase = userDAO.getUserById(user.getId());
        if (!userFromBase.getPassword().equals(user.getPassword())) {
            user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        }



        userDAO.updateUser(user);

    }
@Transactional
    @Override
    public void removeUserById(Long id) {
        userDAO.removeUserById(id);

    }

    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
@Transactional
    @Override
    public void addUser(User user) {



        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        userDAO.addUser(user);

    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public String getRolesToString(User user) {
        List <Role> roles = user.getRoles();
        StringBuilder getRoles= new StringBuilder(" ");
        for (Role role:roles) {
            getRoles.append(role.toString().substring(5)).append(" ");
        }
        assert getRoles != null;
        return  getRoles.toString().trim();
    }
}