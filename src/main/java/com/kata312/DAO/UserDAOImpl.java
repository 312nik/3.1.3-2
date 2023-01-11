package com.kata312.DAO;


import com.kata312.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO{

    @PersistenceContext
    private EntityManager entityManager;




    @Override
    public void updateUser(User user) {

        entityManager.merge(user);


    }

    @Override
    public void removeUserById(Long id) {

        entityManager.remove(entityManager.find(User.class,id));

    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = entityManager.createQuery("from User", User.class).getResultList();
        return users;

    }

    @Override
    public void addUser(User user) {

        entityManager.persist(user);

    }

    @Override

    public User getUserByEmail(@Param("email")String email) {
        TypedQuery<User> query = entityManager.createQuery("SELECT p FROM User p LEFT JOIN FETCH p.roles WHERE p.email = (:email)",
                User.class).setParameter("email", email);
        return query.getSingleResult();
    }


}
