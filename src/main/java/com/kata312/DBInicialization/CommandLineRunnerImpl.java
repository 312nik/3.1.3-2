package com.kata312.DBInicialization;

import com.kata312.model.Role;
import com.kata312.model.User;

import com.kata312.service.RoleService;
import com.kata312.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final UserService userService;
    private final RoleService roleService;
@Autowired
    public CommandLineRunnerImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public void run(String... args) throws Exception {

        if(userService.getAllUsers().isEmpty()){



            Role userRole = new Role("ROLE_USER");

            Role adminRole = new Role("ROLE_ADMIN");

            roleService.save(userRole);
            roleService.save(adminRole);

            User admin= new User();
            admin.setName("Admin");
            admin.setLastName("Adminov");
            admin.setEmail("admin@mail.ru");
            admin.setAge(40);
            admin.setPassword("admin"); //admin
            List <Role> adminRoleImpl= new ArrayList<>();
            adminRoleImpl.add(userRole);
            adminRoleImpl.add(adminRole);
            admin.setRoles(adminRoleImpl);
            userService.addUser(admin);

            User user = new User();
            user.setName("User");
            user.setLastName("Userov");
            user.setAge(30);
            user.setEmail("user@mail.ru");
            user.setPassword("user"); //user
            List<Role> userRoleImpl= new ArrayList<>();
            userRoleImpl.add(userRole);
            user.setRoles(userRoleImpl);
            userService.addUser(user);
        }
    }
}
