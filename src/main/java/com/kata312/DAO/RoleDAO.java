package com.kata312.DAO;

import com.kata312.model.Role;

import java.util.List;

public interface RoleDAO {

   void save (Role role);
   List<Role> getAllRole();
   Role getRoleByName(String name);
}
