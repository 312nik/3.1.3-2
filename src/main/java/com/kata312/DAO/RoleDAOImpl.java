package com.kata312.DAO;

import com.kata312.model.Role;
import com.kata312.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(Role role) {

        entityManager.persist(role);

    }

    @Override
    public List<Role> getAllRole() {

        List<Role> roles = entityManager.createQuery("from Role", Role.class).getResultList();

        return roles;
    }

    @Override
    public Role getRoleByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select u from Role u where u.name=:name",
                Role.class).setParameter("name", name);
        return query.getSingleResult();
    }


}
