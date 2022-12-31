package com.kata312.service;

import com.kata312.model.Role;
import com.kata312.model.User;
import com.kata312.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service

public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;



        
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElse(null);

    }

    public List<User> findAll() {

        return userRepository.findAll();

    }
    @Transactional
    public void saveUser(User user) {

        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }


    @Transactional
    public void update(Long id, User updateUser) {

        updateUser.setId(id);
        userRepository.save(updateUser);
    }
    @Transactional
    public void deleteUser(User user) {

        userRepository.delete(user);

    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public String getRolesToString(User user) {
        Set<Role> roles=user.getRoles();
        String getRoles=" ";
        for (Role role:roles) {
            getRoles= getRoles+(role.toString().substring(5)+" ");
        }
        assert getRoles != null;
        return  getRoles.trim();
    }


}