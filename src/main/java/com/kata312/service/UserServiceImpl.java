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
import java.util.stream.Collectors;

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
    public void save(User user) {

        if(user.getId()!=null) {
            User userFromBase = findById(user.getId());
            if (!userFromBase.getPassword().equals(user.getPassword())) {
                user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
            }
        } else {

        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));}
        userRepository.save(user);

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