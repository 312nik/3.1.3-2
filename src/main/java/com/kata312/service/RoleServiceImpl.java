package com.kata312.service;

import com.kata312.DAO.RoleDAO;
import com.kata312.model.Role;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public List<Role> getAllRole() {
        return roleDAO.getAllRole();
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleDAO.getRoleByName(roleName);
    }

@Transactional
    @Override
    public void save(Role role) {
        roleDAO.save(role);

    }
}
